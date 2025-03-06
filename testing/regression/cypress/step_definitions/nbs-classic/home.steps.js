import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicHomePage from "cypress/e2e/pages/nbs-classic/home.page";

Then("Navigate to Patient Search pane", () => {
    classicHomePage.navigateToPatientSearchPane()
});

Then("Enter Last Name text box input {string}", (text) => {
    classicHomePage.enterLastName(text)
});

Then("Click on Search in Patient Search pane", () => {
    classicHomePage.clickSearchBtnInPatientSearchPane()
});

Then("Enter First Name text box input {string}", (text) => {
    classicHomePage.enterFirstName(text)
});

Then("Click on Add New button in patient Search pane", () => {
    classicHomePage.clickAddNewBtnInPatientSearchPane()
});

Then("Click on Add new lab report in patient Search pane", () => {
    classicHomePage.clickAddNewLabReportBtnInPatientSearchPane()
});

Then("Verify Add Lab Report page displayed", () => {
    classicHomePage.verifyAddLabReport()
});

Then("Click each of the following {string}", (queueName) => {
    classicHomePage.clickDefaultQueue(queueName)
});

Then("Create two users with same firstname and last name", () => {
    classicHomePage.createTwoPatients()
});

Then("Click on Marge Patients tab on upper left side", () => {
    classicHomePage.clickMergePatientTab()
});

Then("Click on Manual Search tab", () => {
    classicHomePage.clickOnManualSearch()
});

Then("Verify user navigated to Find Patient page", () => {
    classicHomePage.verifyFindPatientPage()
});

Then("Search user to manual merge", () => {
    classicHomePage.searchUser()
});

Then("Select users and merge", () => {
    classicHomePage.selectUsersToMerge()
});

Then("Click on System Identified tab", () => {
    classicHomePage.clickSystemIdentifiedTab()
});

Then("Verify Merge Candidate List is displayed to user", () => {
    classicHomePage.verifyMergeCandidateListDisplayed()
});

Then("Click on Reports tab on upper left side", () => {
    classicHomePage.clickReportsTab()
});

Then("Verify user navigated to Reports page", () => {
    classicHomePage.verifyReportsPageDisplayed()
});

Then("Create and run a report", () => {
    classicHomePage.createRunReports()
});

Then("Verify Documents Requiring Security Assignment", () => {
    classicHomePage.verifyDocumentsRequiringSecurityAssignment()
});

Then("Verify Documents Requiring Review", () => {
    classicHomePage.verifyDocumentsRequiringReview()
});

Then("Verify Open Investigations", () => {
    classicHomePage.verifyOpenInvestigations()
});

Then("Sort Search results by {string}", (string) => {
    classicHomePage.clickSortTableOption(string);
});

Then("Verify top Search result by {string}", (string) => {
    classicHomePage.verifyTopAfterSortSearch(string);
});

Then("Verify top Search result is not {string}", (string) => {
    classicHomePage.verifyNoTopAfterSortSearch(string);
});

Then("I click first patient Search results to view profile", () => {
    classicHomePage.clickResultIdLink();
});

