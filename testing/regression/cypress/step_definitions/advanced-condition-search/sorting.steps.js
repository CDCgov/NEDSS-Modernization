import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {sortingPage} from "@pages/advanced-condition-search/sorting.page";

Then("User clicks the up or down arrows in the Condition name column", () => {
    sortingPage.clickConditionColumnHeader();
});

Then("Condition names are listed in descending order", () => {
    sortingPage.conditionsInOrder('ASC');
});

Then("User clicks the up or down arrow again", () => {
    sortingPage.clickConditionColumnHeader();
});

Then("Condition names are listed in ascending order", () => {
    sortingPage.conditionsInOrder('DSC');
});
