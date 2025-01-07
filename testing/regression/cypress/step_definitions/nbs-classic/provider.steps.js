import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicProviderPage from "cypress/e2e/pages/nbs-classic/provider.page";

Then("Navigate to classic provider add page", () => {
    classicProviderPage.navigateToAddProvider()
});

Then("Click on Add button on provider add page", () => {
    classicProviderPage.clickAddButtonOnAddProvider()
});

Then("Enter quick code for new provider", () => {
    classicProviderPage.enterQuickCode()
});

Then("Click Submit button on provider add page", () => {
    classicProviderPage.clickSubmitBtnOnProvider()
});
