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

