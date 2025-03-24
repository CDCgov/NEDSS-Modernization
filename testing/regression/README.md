# Cypress Cucumber Automation Framework with Cucumber Reporting

This repository contains an automation framework built using Cypress and Cucumber for creating feature-rich, maintainable, and efficient automated tests. The framework offers the following features:

## Features

- Utilizes the Cypress testing framework, which provides a powerful API for interacting with web applications.
- Integrates the Cucumber framework for writing tests in a behavior-driven development (BDD) style using Gherkin syntax.
- Supports the execution of tests in both headless and interactive modes, allowing for flexible testing options.
- Provides easy-to-use commands and assertions for validating the behavior of web applications.
- Enables test execution and reporting using various plugins, including html reporting, to generate comprehensive test reports.
- Offers reusable step definitions and custom commands to improve test maintainability and reduce duplication.
- Allows for easy integration with continuous integration (CI) systems, such as Jenkins or GitLab CI, for seamless test execution as part of the development pipeline.

## Prerequisites

Make sure you have the following dependencies installed:

- Node.js (version 18 or higher)
- NPM (Node Package Manager)

## Getting Started

1. Clone this repository to your local machine.
2. Navigate to the project directory.
3. Install the project dependencies by running the command: `npm install`
4. Install Cypress by running the command `./node_modules/cypress/bin/cypress install`
5. Configure your test scenarios by creating feature files in the `cypress/e2e/features` directory using Gherkin syntax.
6. Implement step definitions for your scenarios in the `cypress/e2e/step_definitions` directory.
7. Run the tests using one of the available commands:
   - Run cypress ui: `npm run cypress`
   - Run tests in interactive mode: `npm run cy:e2e -- --browser=chrome --headed`
   - Run tests in headless mode: `npm run cy:e2e`
   - Run tests in interactive mode for a specific spec: `npx cypress run --spec "./cypress/e2e/features/race.feature" --headed`

## Configuration

- `cypress.config.json`: Cypress configuration file containing various settings for test execution.
- `cypress/support/commands.js`: Custom commands and global configurations for Cypress.
- `cypress/support/e2e.js`: Cypress e2e configuration file.
- `.cypress-cucumber-preprocessorrc.json`: The preprocessor uses [cosmiconfig](https://github.com/davidtheclark/cosmiconfig), which means you can place configuration options in EG. `.cypress-cucumber-preprocessorrc.json` or `package.json`. An example configuration is shown below.

   ```json
    {
    "json": {
      "enabled": true,
      "output": "jsonlogs/log.json",
      "formatter": "cucumber-json-formatter.exe"
    },
    "messages": {
      "enabled": false,
      "output": "jsonlogs/messages.ndjson"
    },
    "html": {
      "enabled": false
    },
    "stepDefinitions": [
      "[filepath]/**/*.{js,ts}",
      "[filepath].{js,ts}",
      "cypress/e2e/step_definitions/*.{js,ts}",
      "[filepath]\\***.{js,ts}",
      "[filepath].{js,ts}",
      "cypress\\e2e\\step_definitions\\*.{js,ts}"
    ]
  }
   ```

## Set Private Variable
- Add `.cypress.env.json` to regression directory
  File is listed in .gitignore that contains environment variables and should never be committed.

## Run Manually
- set baseUrl in `cypress.config.js` file. Currently it's set to http://localhost:8080/
  ```javascript
    module.exports = defineConfig({
      e2e: {
        setupNodeEvents,
        specPattern: "./cypress/**/**/*.feature",
        baseUrl: "http://localhost:8080/",
        //baseUrl: "https://app.test.nbspreview.com/",
        // baseUrl: "https://app.int1.nbspreview.com/",
        chromeWebSecurity: false,
        video: false,
      },
    });
  ```
- `npx cypress open`

## Viewing Reports
Once the tests have been executed, the [multiple-cucumber-html-reporter](https://github.com/WasiqB/multiple-cucumber-html-reporter) reports will be generated in the specified output directory. You can view the reports by running the following command:
```
npm run report
```
This command will open a web browser displaying the cucumber-html report, which provide detailed information about the test results, including screenshots, logs, and more.

## Continuous Integration
This framework can be seamlessly integrated with popular CI/CD tools such as Jenkins, CircleCI, or GitLab CI. Configure your CI/CD pipeline to execute the `npm run test` command and generate cucumber-html report as part of your build process.
