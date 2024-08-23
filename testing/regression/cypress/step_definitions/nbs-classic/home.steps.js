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