import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {addConditionPage} from "@pages/advanced-condition-search/addCondition.page";

Then("User views the Create new condition button", () => {
    addConditionPage.viewCreateNewConditionBtn();
});

Then("User clicks the Create new condition button", () => {
    addConditionPage.clickCreateNewConditionBtn();
});

Then("Create new condition window displays", () => {
    addConditionPage.createNewConditionWindowDisplayed();
});

Then("User completes all required and available fields", () => {
    addConditionPage.completeCreateNewConditionForm();
});

Then("User clicks the Create and add to page button", () => {
    addConditionPage.clickCreateAndAddToPageBtn();
});

Then("Create new condition pop-up window closes and new condition is displayed in the Conditions field on Add new page", () => {
    addConditionPage.createNewConditionWindowDisplayed(false);
});
