import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicManageConditionsPage from "@pages/nbs-classic/manageConditions.page";

Then("Navigate to Condition Library", () => {
    classicManageConditionsPage.navigateToConditionsLibrary()
});

Then("Click on Add new in Condition Library", () => {
    classicManageConditionsPage.clickAddNewBtn()
});

Then("Fill the details to create new condition", () => {
    classicManageConditionsPage.fillTheDetailsCondition()
});

When("I click the Return to Condition Library link", () => {
    classicManageConditionsPage.clickReturnToConditionLibrary()
});

Then("I should see a success message saying the condition has been added to the system", () => {
    classicManageConditionsPage.checkSuccessMessageConditionAdded()
});

Then("I should see a success message saying the condition has been updated in the system", () => {
    classicManageConditionsPage.checkSuccessMessageConditionUpdated()
});

Then("Click submit button to create condition", () => {
    classicManageConditionsPage.clickSubmitBtnConditionInConditionLibrary()
});

Then("Click a condition in Condition Library", () => {
    classicManageConditionsPage.clickConditionInConditionList()
});
