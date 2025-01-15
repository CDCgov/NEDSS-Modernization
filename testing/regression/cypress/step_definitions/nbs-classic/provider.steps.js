import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicProviderPage from "cypress/e2e/pages/nbs-classic/provider.page";

Then("Navigate to classic provider add page", () => {
    classicProviderPage.navigateToAddProvider()
});

Then("Navigate to classic provider edit page", () => {
    classicProviderPage.navigateToEditProvider()
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

Then("Click Edit button on provider page", () => {
    classicProviderPage.clickEditBtnOnProvider()
});

Then("Check Edit Provider on the page", () => {
    classicProviderPage.checkEditProviderPage()
});

Then("Click New Provider Edit Radio Option", () => {
    classicProviderPage.clickEditNewProviderRadio()
});

Then("Type new name for Edit Provider first name", () => {
    classicProviderPage.clickEditProviderAddName()
});
