import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {questionLibraryPaginationPage} from "cypress/e2e/pages/question-library/pagination.page";

Then("User should see by default 10 rows of pages listed in the question library", () => {
    questionLibraryPaginationPage.shouldHaveDefaultRows();
});
