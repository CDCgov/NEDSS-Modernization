import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {createNewConditionPage} from "@pages/advanced-condition-search/createNewCondition.page";

Then("User navigates to Create New Page for new condition", () => {
    createNewConditionPage.navigateToCreateNewPage();
});

Then("User clicks in the Event Type and selects Investigation", () => {
    createNewConditionPage.selectEventTypeInvestigation();
});

Then("Additional required and applicable fields displays", () => {
    createNewConditionPage.additionalFieldsDisplayed();
});

Then("User clicks the Create a new condition here button", () => {
    createNewConditionPage.clickCreateNewConditionBtn();
});

Then("Create new condition modal window displays", () => {
    createNewConditionPage.createNewConditionWindowDisplayed();
});

Then("User completes the required and applicable fields", () => {
    createNewConditionPage.completeCreateNewConditionForm();
});

Then("Clicks the Create and add to page button", () => {
    createNewConditionPage.clickCreateAndAddToPageBtn();
});

Then("Create new condition pop-up window closes and confirmation success message displays with the new condition added in the Conditions field on Create new page", () => {
    createNewConditionPage.createNewConditionWindowDisplayed(false);
});

Then("New condition created", () => {
    createNewConditionPage.selectEventTypeInvestigation();
    createNewConditionPage.clickCreateNewConditionBtn();
    createNewConditionPage.completeCreateNewConditionForm();
    createNewConditionPage.clickCreateAndAddToPageBtn();
});

Then("User already on Add new page with new condition added to the Conditions field", () => {
    createNewConditionPage.verifyConditionFieldHasValue();
});

Then("User completes the required and applicable fields remaining", () => {
    createNewConditionPage.completeRemainingFormFields();
});

Then("Clicks the Create page button to add created condition", () => {
    createNewConditionPage.clickCreatePageButton();
});

Then("New page created displays with the Patient tab active for the user to start editing the detailed page information", () => {
    createNewConditionPage.navigateToClassicDesign();
});
