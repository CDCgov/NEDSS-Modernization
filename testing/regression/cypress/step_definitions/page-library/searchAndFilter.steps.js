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
Then("Filter section already displayed", () => {
    searchAndFilterPage.showFilterSection();
});
Then("User selects {string} from the drop-down box", (string) => {
    searchAndFilterPage.selectColumn(string);
});
Then("User selects {string} from the Operator field", (string) => {
    searchAndFilterPage.selectOperator(string);
});
Then("User enters {string} in the Type a value field", (string) => {
    searchAndFilterPage.enterValue(string);
});
Then("User clicks the Done button", () => {
    searchAndFilterPage.clickDone();
});
Then("User clicks the Apply button", () => {
    searchAndFilterPage.clickApply();
});
Then("Added filters {string} and {string} are applied and only the records matching the filters are displayed in the Page Library list", (string, string1) => {
    searchAndFilterPage.showingContainedResults(string, string1);
});
Then("User clicks the Cancel button", () => {
    searchAndFilterPage.clickCancel();
});
Then("The application will cancel adding a filter and return to display the + Add Filter link", () => {
    searchAndFilterPage.canSeeFilterOverlay();
});
Then("User enters {string} in the Type a value field - multi select", (string) => {
    searchAndFilterPage.enterTextInMultiInputValue(string);
});
Then("Type a value field is hidden", () => {
    searchAndFilterPage.checkValueFiledIsHidden();
});
