import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {searchAndFilterPage} from "@pages/advanced-condition-search/searchAndFilter.page";

Then("User views the Search field", () => {
    searchAndFilterPage.viewSearchField();
});

Then("User enters {string} in the Search field", (text) => {
    searchAndFilterPage.enterSearchField(text);
});

Then("User clicks the magnifying glass button", () => {
    searchAndFilterPage.clickSearchBtn();
});

Then("Conditions list will be filtered based on the keywords entered for the Condition column. Only conditions with the matching keywords west nile will be displayed", () => {
    searchAndFilterPage.verifyRowsDisplaying(1);
});

Then("User click the Cancel button footer", () => {
    searchAndFilterPage.clickCancel();
});

Then("Search and add conditions modal closes and returns the user to Add new page", () => {
    searchAndFilterPage.verifyPageReturned();
});

