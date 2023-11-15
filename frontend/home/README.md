# Frontend Development for Home Page

## Project Setup

We leverage [Material Web](https://m3.material.io/develop/web) components in our project, ensuring a modern and cohesive design. To initiate the bundling of the essential JavaScript file bundle.js, execute the following commands from the root folder:

```npm install @material/web```

```npm install rollup @rollup/plugin-node-resolve```

**NOTE:** Bundling requires the existence of `index.js` at the root, encompassing all necessary imports. Any .js files not specified in `index.js` should remain in their designated folders within the repository. The 'md' folder, a sample theme from the [material theme builder](https://m3.material.io/theme-builder#/custom). These themes are necessary part of the material web components. If there are no 'md' folder under 'css' folder, create one.

Execute the following command for bundling:

```npx rollup -p @rollup/plugin-node-resolve index.js -o js/bundles/bundle.js```

Post-bundling, you can test the application by double-clicking the `index.html` file in the root folder.

Ensure execution of the above commands from the root folder where the `index.js` file resides.

## Folder Structure

* root folder: Contains index.html, serving as the home page.
* asset: Holds assets such as the brand logo.
* css: Includes CSS files.
  * css/shapes: Houses custom shapes for web components.
  * css/page-parts: Stores CSS files for components.
* js: Encompasses the land.js file for the home page.
  * js/data: Stores data in JS files for use throughout the project.
  * js/page-parts: Contains JS files for components.
  * js/wrappers: Holds JS wrappers for API endpoints.

## Home Page Overview

The home page caters to client needs by providing essential features like filtering, searching, and paging for a seamless user experience.
