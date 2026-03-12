import { openInvestigationPage } from '@pages/nbs-classic/openInvestigation.page';
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

//Edit Treatment
When('the user clicks on Open Investigations under My Queues', () => {
  openInvestigationPage.openInvestigationsQueue();
});

When('the user searches for the Investigation ID {string}', (investigationId) => {
  openInvestigationPage.searchForInvestigation(investigationId);
});

When('the user clicks on the Condition name', () => {
  openInvestigationPage.clickConditionName();
});

When('the user clicks on Manage Associations', () => {
  openInvestigationPage.clickManageAssociations();
});

When('the user clicks on the Treatment Date link', () => {
  openInvestigationPage.clickTreatmentDate();
});

When('the user clicks on the Edit button', () => {
  openInvestigationPage.clickEditButton();
});

When('the user enters {string} into the Treatment Comments field', (comment) => {
  openInvestigationPage.enterTreatmentComments(comment);
});

When('the user clicks the Submit button', () => {
  openInvestigationPage.clickSubmitButton();
});

Then('the Treatment Comment is updated with the text {string}', (expectedComment) => {
  openInvestigationPage.verifyUpdatedComment(expectedComment);
});

Then('Click on Patient name from open investigation queue', () => {
  openInvestigationPage.clickPatientName();
});

Then('Click Events tab on Patient Profile Page', () => {
  openInvestigationPage.clickEventsTab();
});

Then('Click Add Investigation button on Events tab', () => {
  openInvestigationPage.clickAddInvestigationBtn();
});

Then('Select condition form the dropdown in Select Condition Page', () => {
  openInvestigationPage.selectConditionFromDropdown();
});

Then('Click Submit button in Select Condition Page', () => {
  openInvestigationPage.clickSubmitBtnInSelectConditionPage();
});

Then('Click on Case Info Tab in Add Investigation for the selected condition', () => {
  openInvestigationPage.clickCaseInfoTab();
});

Then('Select Jurisdiction as it is mandatory field in Add Investigation for the selected condition', () => {
  openInvestigationPage.selectJurisdictionFromDropdown();
});

Then('Select status from Case Status dropdown in Add Investigation for the selected condition', () => {
  openInvestigationPage.selectCaseStatusFromDropdown();
});

Then('Click Submit button in Add Investigation for the selected condition', () => {
  openInvestigationPage.clickSubmitBtnInAddInvestigationPage();
});

Then('Click Create Notifications button from top action button group', () => {
  openInvestigationPage.clickCreateInvestigationsBtn();
});

Then('Click Submit button in newly opened window Create Notification Page', () => {
  openInvestigationPage.clickSubmitBtnInCreateNotificationPage();
});
