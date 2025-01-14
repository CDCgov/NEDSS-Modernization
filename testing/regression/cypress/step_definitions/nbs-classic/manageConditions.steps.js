import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicManageConditionsPage from "cypress/e2e/pages/nbs-classic/manageConditions.page";

Then("Navigate to Condition Library", () => {
    classicManageConditionsPage.navigateToConditionsLibrary()
});

Then("Click on Add new in Condition Library", () => {
    classicManageConditionsPage.clickAddNewBtn()
});

Then("Fill the details to create new condition", () => {
    classicManageConditionsPage.fillTheDetailsCondition()
});

Then("Click submit button to create condition", () => {
    classicManageConditionsPage.clickSubmitBtnConditionInConditionLibrary()
});

Then("Click a condition in Condition Library", () => {
    classicManageConditionsPage.clickConditionInConditionList()
});

