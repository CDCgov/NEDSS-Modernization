import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {questionLibraryPaginationPage} from "@pages/Question-library/pagination.page";

Then("User should see by default 10 rows of pages listed in the question library", () => {
    questionLibraryPaginationPage.shouldHaveDefaultRows();
});
