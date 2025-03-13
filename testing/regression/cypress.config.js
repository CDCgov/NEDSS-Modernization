const { defineConfig } = require("cypress");
const createBundler = require("@bahmutov/cypress-esbuild-preprocessor");
const preprocessor = require("@badeball/cypress-cucumber-preprocessor");
const createEsbuildPlugin = require("@badeball/cypress-cucumber-preprocessor/esbuild");
const fs = require("fs").promises;

async function setupNodeEvents(on, config) {
  await preprocessor.addCucumberPreprocessorPlugin(on, config);

  on(
    "file:preprocessor",
    createBundler({
      plugins: [createEsbuildPlugin.default(config)],
    })
  );
  on("after:run", async (results) => {
    if (results) {
      await preprocessor.afterRunHandler(config);
      await fs.writeFile(
        "results.json",
        JSON.stringify(
          {
            browserName: results.browserName,
            browserVersion: results.browserVersion,
            osName: results.osName,
            osVersion: results.osVersion,
            nodeVersion: results.config.resolvedNodeVersion,
            cypressVersion: results.cypressVersion,
            startedTestsAt: results.startedTestsAt,
            endedTestsAt: results.endedTestsAt,
          },
          null,
          "\t"
        )
      );
    }
  });
  return config;
}

module.exports = defineConfig({
  e2e: {
    setupNodeEvents,
    specPattern: "./cypress/**/**/*.feature",
    //baseUrl: "http://localhost:8080/",
    //baseUrl: "https://app.test.nbspreview.com/",
    baseUrl: "https://app.int1.nbspreview.com/",
    chromeWebSecurity: false,
    video: false,
  },
});
