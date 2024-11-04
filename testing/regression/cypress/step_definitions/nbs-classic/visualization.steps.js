import { homePage } from 'cypress/e2e/pages/nbs-classic/homePage.page';
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";

// Step to select a visualization
When('I select {string} from the dropdown', (chartName) => {
  homePage.selectChart(chartName);
});

// Step to verify that the correct visualization or table is displayed
Then('I should see the {string} graph displayed', (chartTitle) => {
  homePage.verifyVisualizationIsDisplayed(chartTitle);
});

Then('I should see the {string} table displayed', (tableTitle) => {
  homePage.verifyVisualizationIsDisplayed(tableTitle);
});
