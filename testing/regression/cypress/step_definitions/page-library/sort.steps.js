import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {pageLibrarySortPage} from "@pages/page-library/sort.page";

Then("User navigates to Page Library and views the Page library", () => {
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

// Status
Then("User click the up or down arrow in the Status column", () => {
    pageLibrarySortPage.statusArrowClick();
});

Then("Status is listed in descending order", () => {
    pageLibrarySortPage.statusListedInDescendingOrder();
});

Then("Status is listed in ascending order", () => {
    pageLibrarySortPage.statusListedInAscendingOrder();
});

// Last updated
Then("User click the up or down arrow in the Last Updated column", () => {
    pageLibrarySortPage.lastUpdatedArrowClick();
});

Then("Last Updated is listed in descending order - latest date", () => {
    pageLibrarySortPage.lastUpdatedListedInDescendingOrder();
});

Then("Last Updated is listed in ascending order - earliest date", () => {
    pageLibrarySortPage.lastUpdatedListedInAscendingOrder();
});

// Last updated - date format
Then("User views the Last Updated column", () => {
    pageLibrarySortPage.lastUpdatedView();
});

Then("Application should display the Last Updated date in the correct format", () => {
    pageLibrarySortPage.lastUpdatedDateFormat();
});

// Last updated by
Then("User click the up or down arrow in the Last Updated by column", () => {
    pageLibrarySortPage.lastUpdatedByArrowClick();
});

Then("Last Updated by is listed in descending order", () => {
    pageLibrarySortPage.lastUpdatedByListedInDescendingOrder();
});

Then("Last Updated by is listed in ascending order", () => {
    pageLibrarySortPage.lastUpdatedByListedInAscendingOrder();
});