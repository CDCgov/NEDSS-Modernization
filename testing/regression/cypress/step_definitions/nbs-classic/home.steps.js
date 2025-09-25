import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicHomePage from "cypress/e2e/pages/nbs-classic/home.page";

Then("Navigate to Patient Search pane", () => {
    classicHomePage.navigateToPatientSearchPane()
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

Then("Classic Click on Reports tab on upper left side", () => {
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
