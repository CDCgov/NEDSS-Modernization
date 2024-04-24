import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {pageLibraryPaginationPage} from "@pages/page-library/pagination.page";

Then("User should see by default 10 rows of pages listed in the library", () => {
    pageLibraryPaginationPage.checkForDefaultRows();
});

Then("User select {string} left footer of the page to show the list of pages", (string) => {
    pageLibraryPaginationPage.selectNumberOfRows(string);
});

Then("User should see only {string} rows in the library and for each subsequent list where applicable", (string) => {
    pageLibraryPaginationPage.checkDisplayingNumberOfRowsSubsequently(string);
});

Then("User navigates to the Create page", () => {
    pageLibraryPaginationPage.navigateToCreatePage();
});
