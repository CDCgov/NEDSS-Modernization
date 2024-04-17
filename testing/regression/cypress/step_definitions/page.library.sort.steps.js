import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {pageLibrarySortPage} from "@pages/page-library-sort.page";

Then("User navigates to Page Library", () => {
    pageLibrarySortPage.navigateToLibrary();
});

Then("User views the Page library", () => {
    pageLibrarySortPage.userViewsPageLibrary();
});

Then("User already on Page Library", () => {
    pageLibrarySortPage.navigateToLibrary();
    pageLibrarySortPage.userViewsPageLibrary();
});

// Page name
Then("User click the up or down arrow in the Page name column", () => {
    pageLibrarySortPage.pageNameArrowClick();
});

Then("Page names are listed in descending order", () => {
    pageLibrarySortPage.pageNameListedInDescendingOrder();
});

Then("Page names are listed in ascending order", () => {
    pageLibrarySortPage.pageNameListedInAscendingOrder();
});

// Event type
Then("User click the up or down arrow in the Event type column", () => {
    pageLibrarySortPage.eventTypeArrowClick();
});

Then("Event type is listed in descending order", () => {
    pageLibrarySortPage.eventTypeListedInDescendingOrder();
});

Then("Event type is listed in ascending order", () => {
    pageLibrarySortPage.eventTypeListedInAscendingOrder();
});
