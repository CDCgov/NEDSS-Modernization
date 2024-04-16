const { defineConfig } = require("cypress");
const createBundler = require("@bahmutov/cypress-esbuild-preprocessor");
const preprocessor = require("@badeball/cypress-cucumber-preprocessor");
const createEsbuildPlugin = require("@badeball/cypress-cucumber-preprocessor/esbuild");

async function setupNodeEvents(on, config) {
  await preprocessor.addCucumberPreprocessorPlugin(on, config);

  on(
    "file:preprocessor",
    createBundler({
      plugins: [createEsbuildPlugin.default(config)],
    })
  );
  return config;
}

module.exports = defineConfig({
  e2e: {
    setupNodeEvents,
    specPattern: "./cypress/**/**/*.feature",
    baseUrl: "http://localhost:8080/",
    //baseUrl: "https://app.test.nbspreview.com/",
    //baseUrl: "https://app.int1.nbspreview.com/",
    chromeWebSecurity: false,
    video: false,
  },
});
