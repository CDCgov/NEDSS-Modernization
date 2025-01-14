import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicSearchOrganizationPage from "cypress/e2e/pages/nbs-classic/organization.page";

Then("Navigate to classic organization Search pane", () => {
    classicSearchOrganizationPage.navigateToClassicOrganizationSearchPane()
});

Then("Enter organization name in input text field {string}", (text) => {
    classicSearchOrganizationPage.enterNameInClassicSearchOrganizationPage(text)
});

Then("Click on Search in classic organization Search pane", () => {
    classicSearchOrganizationPage.clickSearchBtnInClassicOrganizationSearchPane()
});

Then("View organization details through classic search", () => {
    classicSearchOrganizationPage.viewOrganizationDetails()
});

Then("Navigate to classic organization add page", () => {
    classicSearchOrganizationPage.navigateToAddOrganisation()
});

Then("Click on Add button on organization add page", () => {
    classicSearchOrganizationPage.clickAddButtonOnAddOrganisation()
});

Then("Enter quick code for new organisation", () => {
    classicSearchOrganizationPage.enterQuickCode()
});

Then("Click Submit button on organization add page", () => {
    classicSearchOrganizationPage.clickSubmitBtnOnOrganisation()
});
