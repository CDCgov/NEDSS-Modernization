import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicManagePagesPage from "cypress/e2e/pages/nbs-classic/managePages.page";

Then("Navigate to Page Library on classic", () => {
    classicManagePagesPage.navigateToPageLibrary()
});

Then("Click on Add New button on add new classic page", () => {
    classicManagePagesPage.clickAddNewBtn()
});

Then("Select Page as {string} type on add new classic page", (pageType) => {
    classicManagePagesPage.selectPageType(pageType)
});

Then("Select Template form dropdown on add new classic page", () => {
    classicManagePagesPage.selectTemplate()
});

Then("Select Message Mapping Guide from dropdown on add new classic page", () => {
    classicManagePagesPage.selectMappingGuide()
});

Then("Enter Page name on add new classic page", () => {
    classicManagePagesPage.enterPageName()
});

Then("Click on Submit button on add new classic page", () => {
    classicManagePagesPage.clickSubmitBtn()
});

Then("Click on view icon to display the page details on classic", () => {
    classicManagePagesPage.clickViewIcon()
});

Then("Check details displayed for {string}", (header) => {
    classicManagePagesPage.checkDisplayed(header)
});
