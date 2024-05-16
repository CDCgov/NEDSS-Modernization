import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {pageLibraryDataElementsPage} from "@pages/page-library/dataElements.page";

Then("User views the {string} column", (string) => {
    pageLibraryDataElementsPage.userViewsColumnAndSeeList(string);
});

Then("User will see a list of the {string} populated in the {string} column", (string, string1) => {
    pageLibraryDataElementsPage.userViewsColumnAndSeeList(string1);
});
