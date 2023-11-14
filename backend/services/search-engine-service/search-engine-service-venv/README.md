# Search Engine Service

## Prerequisites

Ensure that your system has Python 3 installed. You can verify the Python installation by executing the following command in your terminal:

```py --version```

If no errors are displayed, proceed to create a virtual environment by executing the following command:

```py -m venv /path/to/new/virtual/environment```

You might decide to copy the files to a new folder where you have created your virtual environment. Refer to this [site](https://docs.python.org/3/library/venv.html) to learn more about virtual environments.

Now install the requirements by running the following command:
```pip install -r requirements.txt```

Ensure that you run above commands from the project's root folder, where app folder is located.

Now to run the application, simply go to the app directory and run the following command:

```py app.py```

## Notes

This service is responsible for serving the client's search requests. This searching is a vector based search in which TF-IDF Vectorizer along with Cosine Similarity is used to retrieve a list of result similar with the search query.

When the application starts, it might take some time for initialization tasks. In this initialization, dataframes are obtained from the database and data pre-processing is done like removing the stopwords etc.
