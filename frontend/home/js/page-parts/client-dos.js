/**
 * The sole purpose of this script is to provide an easy way for the developer to access some 
 * functions which will be used to be called on specific html node.
 */


import { langMap__ISO639_1 } from '../data/language-mappings.js';
import { fetchSearchResults } from '../wrappers/search/search.js';
import {
    MovieFilterRequest, fetchWithFilter, getMovieListForDisplay, getMovieOnPage,
    getMovieListByPage, getCastsByMovieId, getLanguages, getGenres, getPopularityScoreLowerBound, 
    getPopularityScoreUpperBound, getRandomCasts, getRandomKeywords, getReleaseYearLowerBound, getReleaseYearUpperBound
} from '../wrappers/view-filter/view-and-filter.js';


// Fill the filter form data
/*
 * Adult choice is represented with switch
 * Languages are represented with chips where abbreviation will be stored and the complete name will be displayed
 * Popularity scores are represented as range sliders (lower on left extreme, upper on right extreme)
 * Release year are represented as textfields (from -> to)
 * Genre list is represented as chips
 * Keyword list is represented as chips
 * Casts list is represented as chips
 */
const filterForm = document.getElementById('filter-form');
// An anonymouse self-invoking function to set/create the filter form fields beforehand
(async function () {
    // Fill the languages row with chips and data from the server
    const langs = await getLanguages();
    var chipSet = document.createElement('md-chip-set');
    langs.forEach(item => {
        if(langMap__ISO639_1.get(item) !== undefined) {
        var filterChip = document.createElement('md-filter-chip');
            filterChip.setAttribute('value', item);
            filterChip.setAttribute('label', langMap__ISO639_1.get(item));

            chipSet.appendChild(filterChip);
        }
    });
    filterForm.getElementsByClassName('languages-row')[0].appendChild(chipSet);

    // Genre Arrangements
    chipSet = document.createElement('md-chip-set');
    const genres = await getGenres();
    genres.forEach(item => {
        var filterChip = document.createElement('md-filter-chip');
        if(item !== '') {
            filterChip.setAttribute('value', item);
            filterChip.setAttribute('label', item);
        }
        else {
            filterChip.setAttribute('value', '');
            filterChip.setAttribute('label', 'no genre');
        }

            chipSet.appendChild(filterChip);
    });
    filterForm.getElementsByClassName('genre-row')[0].appendChild(chipSet);

    // Keyword Arrangements
    chipSet = document.createElement('md-chip-set');
    const keywords = await getRandomKeywords();
    keywords.forEach(item => {
        var filterChip = document.createElement('md-filter-chip');
            filterChip.setAttribute('value', item);
            filterChip.setAttribute('label', item);

            chipSet.appendChild(filterChip);
    });
    filterForm.getElementsByClassName('keyword-row')[0].appendChild(chipSet);

    // Cast Arrangements
    chipSet = document.createElement('md-chip-set');
    const casts = await getRandomCasts();
    casts.forEach(item => {
        var filterChip = document.createElement('md-filter-chip');
            filterChip.setAttribute('value', item);
            filterChip.setAttribute('label', item);

            chipSet.appendChild(filterChip);
    });
    filterForm.getElementsByClassName('cast-row')[0].appendChild(chipSet);

    // Release Year arrangements
    var lowerBound = await getReleaseYearLowerBound();
    var upperBound = await getReleaseYearUpperBound();

    const fromTextField = document.createElement('md-filled-text-field');
    fromTextField.setAttribute('label', 'From Year');
    fromTextField.setAttribute('type', 'number');
    fromTextField.setAttribute('placeholder', lowerBound);
    fromTextField.setAttribute('aria-label', 'Release Year Lower Bound');
    fromTextField.setAttribute('min', lowerBound);
    fromTextField.setAttribute('max', upperBound);
    fromTextField.setAttribute('id', 'from-year');
    const toTextField = document.createElement('md-filled-text-field');
    toTextField.setAttribute('label', 'To Year');
    toTextField.setAttribute('type', 'number');
    toTextField.setAttribute('placeholder', upperBound);
    toTextField.setAttribute('aria-label', 'Release Year Upper Bound');
    toTextField.setAttribute('id', 'to-year');
    toTextField.setAttribute('min', lowerBound);
    toTextField.setAttribute('max', upperBound);

    document.getElementsByClassName('release-year-row')[0].appendChild(document.createElement('br'));
    document.getElementsByClassName('release-year-row')[0].appendChild(fromTextField);
    document.getElementsByClassName('release-year-row')[0].appendChild(document.createElement('br'));
    document.getElementsByClassName('release-year-row')[0].appendChild(toTextField);

    // Popularity Score Arrangements
    lowerBound = await getPopularityScoreLowerBound();
    upperBound = await getPopularityScoreUpperBound();

    rangeSlider = document.createElement('md-slider');
    rangeSlider.setAttribute('range', true);
    rangeSlider.setAttribute('value-start', lowerBound);
    rangeSlider.setAttribute('value-end', upperBound);
    rangeSlider.setAttribute('ticks', true);
    rangeSlider.setAttribute('labeled', true);
    rangeSlider.setAttribute('aria-label', 'Popularity Score lower and upper bound');

    document.getElementsByClassName('popularity-score-row')[0].appendChild(rangeSlider);
})();


var requestedPageNumber = 1; // default
var requestedPageSize = 10; // default

// The container in which we will put the cloned cards
const scrollWrapperContainer = document.getElementById('container-scroll-wrapper');
// Function to generate the replica of movie card
// It takes optional data for displaying the filter items or for data which obey the next, prev or GO button
async function genReplicas(data) {
    // Before cloning and putting, clean the existing cards(remove them from the DOM)
    var child = scrollWrapperContainer.lastElementChild;
    while(child) {
        scrollWrapperContainer.removeChild(child);
        child = scrollWrapperContainer.lastElementChild;
    }

    // Shows initial 10 movies in the movie container (the gallery) if the data sent in the parameter is null
    if(data === null) {
        // get the initial display of movies
        // note that if the filter does not give any specific movie, it returns an empty JSON array
        data = await getMovieListForDisplay();
    }
    // Clone the Cards and manipulate them
    if(data && Array.isArray(data)) {
        data.forEach(async item => {
            // Create the outer div element with the class and id attributes
            var movieCard = document.createElement("div");
            movieCard.className = "movie_card";
            movieCard.id = `card_${item.movieId}`;
            movieCard.setAttribute("data-movieid", `${item.movieId}`);

            // Create the card-container div
            var cardContainer = document.createElement("div");
            cardContainer.className = "card-container";

            // Create the poster-container div
            var posterContainer = document.createElement("div");
            posterContainer.className = "poster-container";

            // Create the img element for the poster
            var posterImg = document.createElement("img");
            posterImg.className = "poster-card";
            posterImg.src = `${item.posterLink}`;

            // Append the posterImg to posterContainer
            posterContainer.appendChild(posterImg);

            // Create the list-encapsulator div
            var listEncapsulator = document.createElement("div");
            listEncapsulator.className = "list-encapsulator";

            // Create the top-level list
            var topList = document.createElement("md-list");
            topList.className = "card_list";

            // Create md-list-items for title, adult, overview, popularity-score, etc.
            var titleItem = document.createElement("md-list-item");
            titleItem.id = "title-language";
            titleItem.textContent = `${item.title}(${item.language})`;

            var adultItem = document.createElement("md-list-item");
            adultItem.id = "adult";
            adultItem.textContent = `${item.adult === true ? "Suitable for all ages." : "Suitable for adults only."}`;

            var overviewItem = document.createElement("md-list-item");
            overviewItem.id = "overview";
            overviewItem.textContent = `Overview: ${item.overview}`;

            var popularityItem = document.createElement("md-list-item");
            popularityItem.id = "popularity-score";
            popularityItem.textContent = `Popularity Score Out of 100 : ${Math.round(item.popularityScore*10)/10}`;

            var averageVote = document.createElement("md-list-item");
            averageVote.id = "average-vote";
            averageVote.textContent = `Average Vote (Scale of 10): ${Math.round(item.averageVote*10)/10}`;

            var totalVoteCount = document.createElement("md-list-item");
            totalVoteCount.id = "total-vote-count";
            totalVoteCount.textContent = `Total Vote Count: ${item.totalVoteCount}`;

            var releaseYearItem = document.createElement("md-list-item");
            releaseYearItem.id = "release-year";
            releaseYearItem.textContent = `Release Year : ${item.releaseYear}`;

            // Append the md-list-items to the top-level list
            topList.appendChild(titleItem);
            topList.appendChild(adultItem);
            topList.appendChild(overviewItem);
            topList.appendChild(popularityItem);
            topList.appendChild(averageVote);
            topList.appendChild(totalVoteCount);
            topList.appendChild(releaseYearItem);


            // Create cast list
            var castList = document.createElement('md-list');
            castList.className = "card_list"; // typical card list for styling

            // The title of the list
            var listTitle = document.createElement('md-list-item');
            listTitle.textContent = '::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::Casts List::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::';
            castList.appendChild(listTitle);

            // append respective texts for each data item present in the item
            (await getCastsByMovieId(item.movieId)).forEach(cast => {
                const castItem = document.createElement("md-list-item");
                castItem.className = "cast-info";
                castItem.textContent = `Name: ${cast.name}, Character: ${cast.character}, Departments: ${cast.departmentList}`;
                // append this child node in the castList
                castList.appendChild(castItem);
            });


            // Append the top-level list to the list-encapsulator
            listEncapsulator.appendChild(topList);
            // Append the second-level list (cast list) to the list-encapsulator
            listEncapsulator.appendChild(castList);

            // Append the posterContainer and listEncapsulator to the cardContainer
            cardContainer.appendChild(posterContainer);
            cardContainer.appendChild(listEncapsulator);

            // Append the cardContainer to the movieCard
            movieCard.appendChild(cardContainer);

            // Finally, append the movieCard to the document body or another parent element
            scrollWrapperContainer.appendChild(movieCard);
        });
    }
}
genReplicas(null);


const pageNumberField = document.getElementById("page-number-text-field");
const pageSizeField = document.getElementById("page-size-text-field");

/*
 * The 'currentFunctionToExecute' variable plays a pivotal role in executing functions triggered by user interactions with navigation buttons.
 * Its value dynamically adapts to the accessed feature; for example, when the client utilizes the filter option,
 * it invokes the corresponding filter functions. Conversely, in the standard home page gallery display feature,
 * this variable activates the function responsible for displaying the next set of pages, ensuring smooth pagination.
 * Additionally, 'currentFunctionToExecute' is instrumental in facilitating the search function calls.
*/
var currentFunctionToExecute = getMovieOnPage;
const nextBtn = document.getElementById('nav-next');
nextBtn.addEventListener('click', async () => {
    // Show the content loading bar
    showPageProgressIndicator();
    if(currentFunctionToExecute === getMovieOnPage || currentFunctionToExecute === formSubmission) {
        ++requestedPageNumber;
        const data = await currentFunctionToExecute(requestedPageNumber);
        // put the data in the replicator
        genReplicas(data);
    }
    else if(currentFunctionToExecute === submitSearchRequestQuery) {
        ++requestedPageNumber;
        const requestedSearchQueryString = searchQueryTextarea.value;
        if(requestedSearchQueryString.length != 0) {
            const data = await currentFunctionToExecute(requestedSearchQueryString, requestedPageNumber, requestedPageSize);
            // put the data in the replicator
            genReplicas(data);
        }
    }
    // Hide the content loading bar
    hidePageProgressIndicator();
});


const prevBtn = document.getElementById('nav-back');
prevBtn.addEventListener('click', async () => {
    showPageProgressIndicator();
    if(requestedPageNumber != 1) {
        if(currentFunctionToExecute === getMovieOnPage || currentFunctionToExecute === formSubmission) {
            --requestedPageNumber;
            const data = await currentFunctionToExecute(requestedPageNumber);
            // put the data in the replicator
            genReplicas(data);
        }
        else if(currentFunctionToExecute === submitSearchRequestQuery) {
            --requestedPageNumber;
            const requestedSearchQueryString = searchQueryTextarea.value;
            if(requestedSearchQueryString.length != 0) {
                const data = await currentFunctionToExecute(requestedSearchQueryString, requestedPageNumber, requestedPageSize);
                // put the data in the replicator
                genReplicas(data);
            }
        }
    }
    // Hide the content loading bar
    hidePageProgressIndicator();
});

async function goButtonAction() {
    showPageProgressIndicator();
    // fetch the respective data and send it to the genReplicas method
    requestedPageNumber = pageNumberField.valueAsNumber;
    requestedPageSize = pageSizeField.valueAsNumber;

    if(currentFunctionToExecute === getMovieOnPage) {
        // We need movie list by page so call the getMovieListByPage function
        const data = await getMovieListByPage(requestedPageNumber, requestedPageSize);
        // put the data in the replicator
        genReplicas(data);
    }
    else if(currentFunctionToExecute === formSubmission) {
        const data = await currentFunctionToExecute(requestedPageNumber, requestedPageSize);
        // put the data in the replicator
        genReplicas(data);
    }
    else if(currentFunctionToExecute === submitSearchRequestQuery) {
        const requestedSearchQueryString = searchQueryTextarea.value;
        if(requestedSearchQueryString.length != 0) {
            const data = await currentFunctionToExecute(requestedSearchQueryString, requestedPageNumber, requestedPageSize);
            // put the data in the replicator
            genReplicas(data);
        }
    }
        
    // Hide the content loading bar
    hidePageProgressIndicator();
}

const filterDialog = document.getElementById("filter-dialog");
function openFilterDialog() {
    filterDialog.show();
}

async function formSubmission() {
    // display the content loading bar
    showPageProgressIndicator();
    // get the form first
    const form = document.getElementById('filter-form');
    event.preventDefault();

    const formData = {};


    // Handle the language chips (limiting to 5)
    const selectedLanguages = [];
    const languageChips = document.querySelectorAll('.languages-row md-filter-chip');
    languageChips.forEach(chip => {
    if (chip.hasAttribute('selected')) {
        selectedLanguages.push(chip.getAttribute('value'));
    }
    });
    formData.languages = selectedLanguages.slice(0, 5); // Limit to 5 choices

    // Handle the adult choice
    const adultChoice = document.getElementById('adult-choice-button');
    formData.adultChoice = !adultChoice.selected;

    // Handle the selected casts
    const selectedCasts = [];
    const castChips = document.querySelectorAll('.cast-row md-filter-chip');
    castChips.forEach(chip => {
    if (chip.hasAttribute('selected')) {
        selectedCasts.push(chip.getAttribute('value'));
    }
    });
    formData.casts = selectedCasts.slice(0, 5);

    // Handle the release year range
    const fromYearField = document.getElementById('from-year');
    const toYearField = document.getElementById('to-year');
    formData.releaseYearFrom = fromYearField.valueAsNumber;
    formData.releaseYearTo = toYearField.valueAsNumber;

    // Handle the popularity score range
    const popularitySlider = document.querySelector('.popularity-score-row md-slider');
    formData.popularityScoreFrom = popularitySlider.getAttribute('value-start');
    formData.popularityScoreTo = popularitySlider.getAttribute('value-end');

    // Handle the selected genres
    const selectedGenres = [];
    const genreChips = document.querySelectorAll('.genre-row md-filter-chip');
    genreChips.forEach(chip => {
    if (chip.hasAttribute('selected')) {
        selectedGenres.push(chip.getAttribute('value'));
    }
    });
    formData.genres = selectedGenres.slice(0, 5);

    
    // Handle the selected keywords
    const selectedKeywords = [];
    const keywordChips = document.querySelectorAll('.keyword-row md-filter-chip');
    keywordChips.forEach(chip => {
    if (chip.hasAttribute('selected')) {
        selectedKeywords.push(chip.getAttribute('value'));
    }
    });
    formData.keywords = selectedKeywords.slice(0, 5);

    // Structure the request JSON
    const requestDTO = new MovieFilterRequest(
        formData.languages, formData.adultChoice, formData.popularityScoreFrom, formData.popularityScoreTo,
        formData.releaseYearFrom, formData.releaseYearTo, formData.genres, formData.keywords, formData.casts
    );

    // Get the response
    const response = await fetchWithFilter(requestedPageNumber, requestedPageSize, requestDTO);
    // Call the replicator
    genReplicas(response);
    hidePageProgressIndicator();
}

filterDialog.addEventListener('submit', async () => {
    currentFunctionToExecute = formSubmission;
    currentFunctionToExecute();
});


// Search related functions
const searchDialog = document.getElementById('search-dialog');
const searchQueryTextarea = document.getElementById('search-query-textarea');
// Function to open the search dialog
function openSearchDialog() {
    searchDialog.showModal();
    searchQueryTextarea.focus();
}
function closeSearchDialog() {
    searchDialog.close();
}

// Event listener for the Cancel (Esc) action
document.addEventListener('keydown', function (event) {
    if (event.key === 'Escape' && searchDialog.open) {
        closeSearchDialog();
    }
});

document.addEventListener('keydown', function(event) {
    var isAltKey = event.altKey || event.metaKey;
  
    // Check for specific key names
    if (isAltKey && event.key === 'Enter') {
      search();
    }
});
  

const searchBtn = document.getElementById('search-btn');
// onclick action of the search button handled here in the following function
searchBtn.addEventListener('click', async () => {
    // open the dialog
    openSearchDialog();
});

const cancelSearchBtn = document.getElementById('search-cancel-btn');
cancelSearchBtn.addEventListener('click', async () => {
    // force close the dialog
    closeSearchDialog();
});

async function submitSearchRequestQuery(searchQueryString) {
    
    // display the content loading bar
    showPageProgressIndicator();

    // Get the response
    const response = await fetchSearchResults(searchQueryString, requestedPageNumber, requestedPageSize);

    console.log(response);

    // Call the replicator
    genReplicas(response);

    hidePageProgressIndicator();
}

function search() {
    // read the query string and supply it to the server, fetch the result, generate the replicas
    // and then save this function
    const requestedSearchQueryString = searchQueryTextarea.value;
    if(requestedSearchQueryString.length != 0) {
        currentFunctionToExecute = submitSearchRequestQuery;
        // execute the currentFunctionToExecute function
        currentFunctionToExecute(requestedSearchQueryString, requestedPageNumber, requestedPageSize);
        // close the dialog as there was a valid query being made
        closeSearchDialog();
    }
}

const searchGoBtn = document.getElementById('search-go-btn');
searchGoBtn.addEventListener('click', async () => {
    search();
});


exports.module = {
    genReplicas, genReplicaCaller, goButtonAction, openFilterDialog
};
