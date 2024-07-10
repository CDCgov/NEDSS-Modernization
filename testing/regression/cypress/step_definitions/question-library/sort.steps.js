import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {questionLibrarySortPage} from "cypress/e2e/pages/question-library/sort.page";

Then("User navigates to Question Library and views the Question library", () => {
    questionLibrarySortPage.navigateEditPage();
    questionLibrarySortPage.clickAddQuestionBtn();
});

Then("User click the up or down arrow in the {string} column", (column) => {
    questionLibrarySortPage.clickColumnArrow(column);
});

Then("In {string} column {string} are listed in descending order", (column) => {
    questionLibrarySortPage.listedInDescendingOrder(column);
});

Then("User click the up or down arrow in the {string} column again", (column) => {
    questionLibrarySortPage.clickColumnArrow(column);
});

Then("In {string} column {string} are listed in ascending order", (column) => {
    questionLibrarySortPage.listedInAscendingOrder(column);
});
