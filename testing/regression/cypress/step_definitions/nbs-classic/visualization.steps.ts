import { homePage } from '@pages/nbs-classic/homePage.page';
import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

// Step to select a visualization
When('I select {string} from the dropdown', (chartName: string) => {
    homePage.selectChart(chartName);
});

// Step to verify that the correct visualization or table is displayed
Then('I should see the {string} graph displayed', (chartTitle: string) => {
    homePage.verifyVisualizationIsDisplayed(chartTitle);
});

Then('I should see the {string} table displayed', (tableTitle: string) => {
    homePage.verifyVisualizationIsDisplayed(tableTitle);
});
