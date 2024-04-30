import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {searchAndFilterPage} from "@pages/page-library/searchAndFilter.page";

Then("User enters keyword {string} in the Search field", (string) => {
    searchAndFilterPage.enterTextInSearchField(string);
});
Then("User click the magnifying glass icon", () => {
    searchAndFilterPage.clickOnSearchButton();
});
Then("All pages related to {string} will be returned as a list in the library in {string}", (string, string2) => {
    searchAndFilterPage.checkMatchedSearchResult(string, string2);
});