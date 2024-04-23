import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {pageLibraryPaginationPage} from "@pages/page-library/pagination.page";

Then("User should see by default 10 rows of pages listed in the library", () => {
    pageLibraryPaginationPage.checkForDefaultRows();
});

Then("User select 20 left footer of the page to show the list of pages", () => {
    pageLibraryPaginationPage.selectNumberOfRows('20');
});

Then("User should see only 20 rows in the library and for each subsequent list where applicable", () => {
    pageLibraryPaginationPage.checkDisplayingNumberOfRowsSubsequently(20);
});
