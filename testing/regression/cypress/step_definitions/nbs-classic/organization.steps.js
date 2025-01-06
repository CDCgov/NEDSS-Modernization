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
