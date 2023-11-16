#!/usr/bin/env python

from flask import Flask, request, jsonify
from sqlalchemy import create_engine


from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize


import py_eureka_client.eureka_client as eureka_client


import pandas as pd


from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize



rest_port = 8769
eureka_client.init(eureka_server='http://localhost:8761/eureka', app_name='SEARCH-ENGINE-SERVICE', instance_port=rest_port)

app = Flask(__name__)

# @app.before_request
def startup():
    # Make SQLAlchemy Engine to connect to the database
    sqlalchemy_engine(host='localhost', user = 'movie_database', name = 'movie_database', pwd = 'movie_database')
    # Generate the vectorized form based on the dataframes obtained using the engine
    generate_all_dataframes()
    # Store the vectorized form globally
    vectorize()


# global variable, eng - a sqlalchemy engine, set by the app startup task
eng = None
def sqlalchemy_engine(host, user, name, pwd):
    # Database connection details
    db_url = f'mysql+pymysql://{user}:{pwd}@{host}/{name}'

    # Create a SQLAlchemy engine
    global eng
    eng = create_engine(db_url)


# Function to remove stopwords
def remove_stopwords(text):
    words = word_tokenize(text)
    filtered_words = [word for word in words if word.lower() not in stopwords.words('english')]
    filtered_text = ' '.join(filtered_words)
    return filtered_text


# global variables
movie_df = None
genre_df = None
keyword_df = None
combined_df = None
# Function returns a dict of pandas dataframes
def generate_all_dataframes():
    global eng
    global movie_df
    query = '''
    SELECT * FROM movie
    '''
    movie_df = pd.read_sql_query(query, con=eng)

    global genre_df
    query = '''
    SELECT movie_genre.movie_id, genre.genre 
    FROM movie_genre 
    INNER JOIN genre 
    WHERE movie_genre.genre_id = genre.genre_id
    '''
    genre_df = pd.read_sql_query(query, con=eng)
    # Group by Column movie_id and join the corresponding values in Column genre with a single space
    genre_df = genre_df.groupby('movie_id')['genre'].agg(lambda x: ', '.join(x)).reset_index()

    # merge genre_df in the movie_df
    movie_df = pd.merge(genre_df, movie_df, on='movie_id')
    
    movie_df['adult'] = movie_df['adult'].replace({0: False, 1: True})

    movie_df['genre'] = movie_df['genre'].str.split(', ')

    global keyword_df
    query = '''
    SELECT movie_keyword.movie_id, keyword.keyword
    FROM movie_keyword
    INNER JOIN keyword ON movie_keyword.keyword_id = keyword.keyword_id
    '''
    keyword_df = pd.read_sql_query(query, con=eng)

    # Apply remove_stopwords to the "keyword" column
    keyword_df['keyword'] = keyword_df['keyword'].apply(remove_stopwords)

    # Multiple keywords are there for each movie so combine them
    keyword_df = keyword_df.groupby('movie_id')['keyword'].agg(lambda x: ' '.join(x)).reset_index()

    global combined_df
    combined_df = pd.merge(genre_df, keyword_df, on='movie_id')
    combined_df['genre'].apply(lambda x: x.replace(',', ''))
    combined_df = pd.merge(combined_df, movie_df[['movie_id', 'release_year']], on='movie_id')
    combined_df['combined'] = combined_df['genre'] + ' ' + combined_df['keyword']
    combined_df['combined'] = combined_df['combined'] + ' ' + combined_df['release_year'].apply(lambda x: str(x))


# NOTE: Do not vectorize a None type. It cannot be done as lower() method is called which
# requires a non-None string type. One strategy can be to use fillna('') to handle such 
# cases and it is done here.
vectorizer = None
tfidf = None
def vectorize():
    global vectorizer
    global tfidf
    combined_df['combined'] = combined_df['combined'].fillna('')
    vectorizer = TfidfVectorizer(ngram_range=(1, 3))
    tfidf = vectorizer.fit_transform(combined_df['combined'])




def vector_search(text, page_number, page_size):
    text = remove_stopwords(text)

    query_vec = vectorizer.transform([text])

    similarity = cosine_similarity(query_vec, tfidf).flatten()

    intermediate_results = combined_df.drop_duplicates(subset='movie_id')[::-1]

    final_results = movie_df[movie_df['movie_id'].isin(intermediate_results['movie_id'])].copy()

    # Add a new column for similarity scores
    final_results['similarity'] = similarity

    # Filter rows where similarity is greater than 0
    final_results = final_results[final_results['similarity'] > 0]

    # Sort the results based on similarity in descending order
    final_results = final_results.sort_values(by='similarity', ascending=False)

    # Pagination
    start_idx = (page_number - 1) * page_size
    end_idx = start_idx + page_size

    paginated_results = final_results.iloc[start_idx:end_idx].drop(columns='similarity')

    return paginated_results


def prettifyJSON(res):
    # res is paginated result obtained from vector_search function.
    # res is a list of individual columns listed at one place in single list.
    # Convert the DataFrame to a list of dictionaries with 'records' orient
    json_result = res.to_dict(orient='records')

    # Customize the column names in the JSON output
    custom_column_names = {
        'movie_id': 'movieId',
        'poster_path': 'posterLink',
        'popularity': 'popularityScore',
        'vote_average': 'averageVote',
        'vote_count': 'totalVoteCount',
        'release_year': 'releaseYear',
        'genre': 'genreList'
    }
    
    # Rename the columns in each dictionary
    for item in json_result:
        for key, value in custom_column_names.items():
            item[value] = item.pop(key, None)
    
    # Return the prettified JSON
    return jsonify(json_result)


@app.route('/search', methods=['POST'])
def search():
    text = request.data.decode('utf-8')  # Decode the incoming bytes as UTF-8

    # Retrieve query parameters
    page_number = int(request.args.get('pN', 1))
    if page_number <= 0: # (validation)
        page_number = 1
    
    page_size = int(request.args.get('pS', 10))
    if page_size <= 0: # (validation)
        page_size = 10
    elif page_size > 20:
        page_size = 20 # (validation) restrict to the max allowed page size
    
    response = vector_search(text, page_number, page_size)

    return prettifyJSON(response)


with app.app_context():
    startup()


if __name__ == '__main__':
    app.run(host='0.0.0.0', port = rest_port) # Set the host to 0.0.0.0 to open it to external services, lest Flask refuse them to connect.
