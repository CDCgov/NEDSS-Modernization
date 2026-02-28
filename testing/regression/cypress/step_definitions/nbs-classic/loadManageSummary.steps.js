import { loadManageSummaryPage } from '@pages/nbs-classic/loadManageSummary.page';
import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

When('I click on Open Data Entry in the menu bar', () => {
  loadManageSummaryPage.clickOpenDataEntry();
});

Then('I should see the Summary Data link', () => {
  loadManageSummaryPage.verifySummaryLink();
});

When('I click on the Summary Data link', () => {
  loadManageSummaryPage.clickSummaryLink();
});

Then('I should see the Summary Data page', () => {
  loadManageSummaryPage.verifySummaryPage();
});

When('I select {int} from the MMWR Year dropdown', (year) => {
  loadManageSummaryPage.selectMMWRYear(year);
});

Then('I should see {int} options in the MMWR Week dropdown', (count) => {
  loadManageSummaryPage.verifyMMWRWeekOptions(count);
});
