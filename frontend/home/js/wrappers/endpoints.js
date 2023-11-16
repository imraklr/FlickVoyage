export const baseUrl = 'http://192.168.56.1:8764';

export const apiEndpoints = {

    // Movie View AND Filter Requests
    movieWithFilter: {
        url: `${baseUrl}/filter?pageNumber={pageNumber}&pageSize={pageSize}`,
        method: "POST"
    },
    getMovieForDisplay: {
      url: `${baseUrl}/movie`,
      method: "GET"
    },
    getMovieByPage: {
      url: `${baseUrl}/movie?pageNumber={pageNumber}&pageSize={pageSize}`,
      method: "GET"
    },
    getMovieOnPage: {
        url: `${baseUrl}/movie/page/{pN}`,
        method: "GET"
    },
    getMovieForPageSize: {
        url: `${baseUrl}/movie/pageSize/{pS}`,
        method: "GET"
    },
    resetMoviePageSize: {
      url: `${baseUrl}/movie/pageSize/{to}`,
      method: "PUT"
    },
    getCastByMovieId: {
        url: `${baseUrl}/cast/{mId}`,
        method: "GET"
    },
    languages: {
      url: `${baseUrl}/movie/languages`,
      method: "GET"
    },
    genres: {
      url: `${baseUrl}/movie/genres`,
      method: "GET"
    },
    releaseYearLowerBound: {
      url: `${baseUrl}/movie/releaseYearLowerBound`,
      method: "GET"
    },
    releaseYearUpperBound: {
      url: `${baseUrl}/movie/releaseYearUpperBound`,
      method: "GET"
    },
    popularityScoreLowerBound: {
      url: `${baseUrl}/movie/popularityScoreLowerBound`,
      method: "GET"
    },
    popularityScoreUpperBound: {
      url: `${baseUrl}/movie/popularityScoreUpperBound`,
      method: "GET"
    },
    randomKeywords: {
      url: `${baseUrl}/movie/randomKeywords`,
      method: "GET"
    },
    randomCasts: {
      url: `${baseUrl}/movie/randomCasts`,
      method: "GET"
    },

    // Search Requests
    suggestions: {
        url: `${baseUrl}/search?pN={pageNumber}&pS={pageSize}`,
        method: "POST"
    }
};

module.exports = {
    baseUrl, apiEndpoints
}
