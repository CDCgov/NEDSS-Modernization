import DecisionSupportPage from '@pages/nbs-classic/decisionSupport.page';
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";

When('I click on the "System Management" link in the menu', () => {
  DecisionSupportPage.navigateToSystemManagement();
});

When('I expand the Decision Support Management menu', () => {
  DecisionSupportPage.expandDecisionSupportManagement();
});

When('I click on Manage Alerts', () => {
  DecisionSupportPage.goToManageAlerts();
});

When('I select {string} from the "Condition" dropdown', (condition) => {
  DecisionSupportPage.selectCondition(condition);
});

When('I select {string} from the Jurisdiction dropdown', (jurisdiction) => {
  DecisionSupportPage.selectJurisdiction(jurisdiction);
});

When('I select {string} from the Public Health Event dropdown', (eventType) => {
  DecisionSupportPage.selectEventType(eventType);
});

When('I click the Search button', () => {
  DecisionSupportPage.clickSearchButton();
});

When('I select {string} for Severity from the dropdown', (severity) => {
  DecisionSupportPage.selectSeverity(severity);
});

When('I enter {string} in the Extended Alert Message box', (message) => {
  DecisionSupportPage.enterExtendedAlertMessage(message);
});

When('I click the Add Alert button', () => {
  DecisionSupportPage.clickAddAlertButton();
});

Then('I should see the error {string}', (expectedError) => {
  DecisionSupportPage.verifyErrorMessage(expectedError);
});
