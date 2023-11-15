/**
 * @author Rakesh Kumar
 *
 * For more information, visit:
 * GitHub: [imraklr]{@link https://github.com/imraklr}
 */

import { apiEndpoints } from "../endpoints.js";

export async function fetchSearchResults(searchQueryString, pageNumber, pageSize) {
    // perform request data validation
    try {
        const url = apiEndpoints.suggestions.url
        .replace("{pageNumber}", pageNumber === null ? '1' : pageNumber)
        .replace("{pageSize}", pageSize === null ? ''+defaultPageSize : pageSize);
        const response = await fetch(url, {
          method: apiEndpoints.suggestions.method,
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(searchQueryString)
        });
      
        if (response.ok) {
          return await response.json();
        } else {
          throw new Error(`Failed to fetch data: ${response.status}`);
        }
    }
    catch(err) {
      console.error(err);
    }
  }

module.exports = {
  fetchSearchResults
}

// Call Example
// await fetchSearchResults('find movies about love', 1, 2);