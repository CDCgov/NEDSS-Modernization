import { openInvestigationPage } from 'cypress/e2e/pages/nbs-classic/openInvestigation.page';
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";

// Accessing and verifying Open Investigation Queue page
When('I click on "Open Investigation" in the menu bar', () => {
  openInvestigationPage.clickOpenInvestigation();
});

Then('I should land on the "Open Investigation Queue" page', () => {
  openInvestigationPage.verifyQueuePage();
});

// Navigating through pages
When('I click on the "Next" link', () => {
  openInvestigationPage.clickNext();
});

Then('I should see the next page of results', () => {
  openInvestigationPage.clickPrevious();
});

When('I click on the "Previous" link', () => {
  openInvestigationPage.clickPrevious();
});

Then('I should see the previous page of results', () => {
  openInvestigationPage.clickNext();
});

// Sorting by Case Status
When('I open the sort menu', () => {
  openInvestigationPage.openSortMenu();
});

When('I click on "Select All"', () => {
  openInvestigationPage.clickSelectAll();
});

When('I select "Confirmed" from the sort options', () => {
  openInvestigationPage.selectConfirmed();
});

When('I deselect "Confirmed"', () => {
  openInvestigationPage.deselectConfirmed();
});

When('I select "Probable" from the sort options', () => {
  openInvestigationPage.selectProbable();
});

When('I click the "Ok" button', () => {
  openInvestigationPage.clickOk();
});

Then('I should see the results sorted by "Confirmed" status', () => {
  openInvestigationPage.verifyNoSortingChanges();
});

When('I click the "Cancel" button', () => {
  openInvestigationPage.clickCancel();
});

Then('I should see no change in sorting', () => {
  openInvestigationPage.verifyNoSortingChanges();
});

When('I click on the "Remove All Filters Sorts" link', () => {
  openInvestigationPage.clickRemoveFilters();
});

Then('all filters and sorts should be cleared', () => {
});

Then('I click and view an Investigation', () => {
 openInvestigationPage.verifInvestigation();
});
