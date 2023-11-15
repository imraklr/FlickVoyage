/**
 * @author Rakesh Kumar
 *
 * For more information, visit:
 * GitHub: [imraklr]{@link https://github.com/imraklr}
 */

import { apiEndpoints } from "../endpoints.js";

// Normal Page Requests
export class MovieFilterRequest {
  constructor(
    languages, adult, 
    popularityScoreLowerBound, popularityScoreUpperBound, 
    releaseYearLowerBound, releaseYearUpperBound, 
    genreList, keywordList, 
    castNameList
  ) {
    this.languages = languages;
    this.adult = adult;
    this.popularityScoreLowerBound = popularityScoreLowerBound;
    this.popularityScoreUpperBound = popularityScoreUpperBound;
    this.releaseYearLowerBound = releaseYearLowerBound;
    this.releaseYearUpperBound = releaseYearUpperBound;
    this.genreList = genreList;
    this.keywordList = keywordList;
    this.castNameList = castNameList;
  }

  isValidRecord() {
    return (
      this.languages !== null ||
      this.adult !== null ||

      this.popularityScoreLowerBound !== null || this.popularityScoreUpperBound !== null ||

      this.releaseYearLowerBound !== null || this.releaseYearUpperBound !== null ||

      this.genreList !== null ||
      this.keywordList !== null ||
      this.castNameList !== null
    );
  }

  addToGenreList(genre) {
    if (Array.isArray(this.genreList)) {
      this.genreList.push(genre);
    } else {
      console.error("Genre list must be an array.");
    }
  }
  addToKeywordList(keyword) {
    if (Array.isArray(this.keywordList)) {
      this.keywordList.push(keyword);
    } else {
      console.error("Keyword list must be an array.");
    }
  }
  addToCastNameList(castName) {
    if (Array.isArray(this.castNameList)) {
      this.castNameList.push(castName);
    } else {
      console.error("castName list must be an array.");
    }
  }
}

export async function fetchWithFilter(pageNumber, pageSize, movieFilterRequestDTO) {
  // perform request data validation
  try {
    if(movieFilterRequestDTO.isValidRecord()) {
      const url = apiEndpoints.movieWithFilter.url
      .replace("{pageNumber}", pageNumber === null ? '1' : pageNumber)
      .replace("{pageSize}", pageSize === null ? ''+defaultPageSize : pageSize);
      const response = await fetch(url, {
        method: apiEndpoints.movieWithFilter.method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(movieFilterRequestDTO)
      });
    
      if (response.ok) {
        return await response.json();
      } else {
        throw new Error(`Failed to fetch data: ${response.status}`);
      }
    }
    else {
      throw "Fetch failed.\nFilterRequestDTO object contains invalid data OR itself in valid.";
    }
  }
  catch(err) {
    console.error(err);
  }
}

export async function getMovieListForDisplay() {
  const url = apiEndpoints.getMovieForDisplay.url;
  const response = await fetch(url, {
    method: apiEndpoints.getMovieForDisplay.method
  });

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error(`Failed to fetch movie data: ${response.status}`);
  }
}

export async function getMovieListByPage(pageNumber, pageSize) {
  const url = apiEndpoints.getMovieByPage.url
    .replace("{pageNumber}", pageNumber === null ? null : pageNumber)
    .replace("{pageSize}", pageSize === null ? null : pageSize);
  const response = await fetch(url, {
    method: apiEndpoints.getMovieByPage.method
  });

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error(`Failed to fetch movie data: ${response.status}`);
  }
}

export async function getMovieOnPage(pageNumber) {
  const url = apiEndpoints.getMovieOnPage.url.replace("{pN}", pageNumber === null ? null : pageNumber);
  const response = await fetch(url, {
    method: apiEndpoints.getMovieOnPage.method,
  });

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error(`Failed to fetch movie data: ${response.status}`);
  }
}

export async function getMovieForPageSizeOnTheSamePage(pageSize) {
  const url = apiEndpoints.getMovieForPageSize.url.replace("{pS}", pageSize === null ? null : pageSize);
  const response = await fetch(url, {
    method: apiEndpoints.getMovieForPageSize.method,
  });

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error(`Failed to fetch movie data: ${response.status}`);
  }
}

// Filter Requests Criterias
export async function getLanguages() {
  const url = apiEndpoints.languages.url;
  const response = await fetch(url,{
    method: apiEndpoints.languages.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch language list');
  }
}

export async function getGenres() {
  const url = apiEndpoints.genres.url;
  const response = await fetch(url,{
    method: apiEndpoints.genres.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch genre list');
  }
}

export async function getReleaseYearLowerBound() {
  const url = apiEndpoints.releaseYearLowerBound.url;
  const response = await fetch(url,{
    method: apiEndpoints.releaseYearLowerBound.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch Release Year Lower Bound value');
  }
}

export async function getReleaseYearUpperBound() {
  const url = apiEndpoints.releaseYearUpperBound.url;
  const response = await fetch(url,{
    method: apiEndpoints.releaseYearUpperBound.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch Release Year Lower Bound value');
  }
}

export async function getPopularityScoreLowerBound() {
  const url = apiEndpoints.popularityScoreLowerBound.url;
  const response = await fetch(url,{
    method: apiEndpoints.popularityScoreLowerBound.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch Release Year Lower Bound value');
  }
}

export async function getPopularityScoreUpperBound() {
  const url = apiEndpoints.popularityScoreUpperBound.url;
  const response = await fetch(url,{
    method: apiEndpoints.popularityScoreUpperBound.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch Release Year Lower Bound value');
  }
}

export async function getRandomKeywords() {
  const url = apiEndpoints.randomKeywords.url;
  const response = await fetch(url,{
    method: apiEndpoints.randomKeywords.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch language list');
  }
}

export async function getRandomCasts() {
  const url = apiEndpoints.randomCasts.url;
  const response = await fetch(url,{
    method: apiEndpoints.randomCasts.method
  });

  if(response.ok) {
    return await response.json();
  } else {
    throw new Error('Failed to fetch language list');
  }
}


const defaultPageSize = 10;
export async function resetPageSizeForMovieInfoDisplay(pageSize) {
  const url = apiEndpoints.resetMoviePageSize.url.replace("{to}", pageSize === null ? null : pageSize < 0 ? defaultPageSize : pageSize);
  const response = await fetch(url, {
    method: apiEndpoints.resetMoviePageSize.method
  });

  if(response.ok) {
    const res = await response.json();
    if(res===false)
        throw new Error(`Invalid Page Size: \nServer Returned: ${res}`);
    return res;
  } else {
    throw new Error(`Failed to fetch movie data: ${response.status}`);
  }
}

export async function getCastsByMovieId(movieId) {
  const url = apiEndpoints.getCastByMovieId.url.replace("{mId}", movieId === null ? null : movieId);
  const response = await fetch(url, {
    method: apiEndpoints.getCastByMovieId.method,
  });

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error(`Failed to fetch cast data: ${response.status}`);
  }
}


// exports
module.exports = {
    fetchWithFilter, 
    MovieFilterRequest, 
    getMovieListForDisplay,
    getMovieListByPage,
    getMovieForPageSizeOnTheSamePage,
    getMovieOnPage,
    resetPageSizeForMovieInfoDisplay,
    getCastsByMovieId,
    getLanguages,
    getGenres,
    getReleaseYearLowerBound,
    getReleaseYearUpperBound,
    getPopularityScoreLowerBound,
    getPopularityScoreUpperBound,
    getRandomCasts,
    getRandomKeywords
};