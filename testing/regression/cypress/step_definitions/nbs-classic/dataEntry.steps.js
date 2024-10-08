import { labReportPage } from 'cypress/e2e/pages/nbs-classic/dataEntry.page';
import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";

When('I click on Data Entry in the menu bar', () => {
  labReportPage.clickDataEntry();
});

When('I click on Lab Report', () => {
  labReportPage.clickLabReport();
});

When('I click on the Lab Report tab', () => {
  labReportPage.clickLabReportTab();
});

When('I enter {string} in the Reporting Facility field', (value) => {
  labReportPage.enterReportingFacility(value);
});

When('I click the Quick Code Lookup button', () => {
  labReportPage.clickQuickCodeLookup();
});

When('I check the "Same as Reporting Facility" checkbox', () => {
  labReportPage.checkSameAsReportingFacility();
});

When('I select the third element in the Jurisdiction dropdown', () => {
  labReportPage.selectJurisdiction();
});

When('I select the sixth element in the Resulted Test dropdown', () => {
  labReportPage.selectResultedTest();
});

When('I select the second element in the Coded Result dropdown', () => {
  labReportPage.selectCodedResult();
});

When('I enter {string} in the Numeric Result field', (value) => {
  labReportPage.enterNumericResult(value);
});

When('I select % for the Units field', () => {
  labReportPage.selectUnits();
});

When('I enter {string} in the Text Result field', (text) => {
  labReportPage.enterTextResult(text);
});

When('I click the add button to add the lab report', () => {
  labReportPage.clickAddButton();
});

When('I click the submit button', () => {
  labReportPage.clickSubmitButton();
});
