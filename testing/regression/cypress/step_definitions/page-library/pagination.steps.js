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

Then("User click 2 in the pagination section bottom right", () => {
    pageLibraryPaginationPage.clickByPageNumber(2);
});

Then("User should see the subsequent rows listed in the library for the number of rows selected and Same results when paginating pages 3, 4, and 5", () => {
    pageLibraryPaginationPage.clickByPageNumber();
});

Then("User click the Next link bottom right", () => {
    pageLibraryPaginationPage.clickNextPage();
});

Then("User should see the subsequent row of pages listed in the library", () => {
    pageLibraryPaginationPage.checkDisplayingNumberOfRowsSubsequently(10, true, 2);
});

Then("User should see the next list in sequence", () => {
    pageLibraryPaginationPage.checkDisplayingNumberOfRowsSubsequently(10, true, 3);
});
