import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {businessRulesPage} from "@pages/business-rules/businessRules.page";

Then("User navigates to Business Rules and views the Business Rules", () => {
    businessRulesPage.navigateToBusinessRulesPage();
});

Then("Application will display the Business Rules landing page", () => {
    businessRulesPage.viewsBusinessRulesPage();
});

Then("Logic column is populated", () => {
    businessRulesPage.checkLogicColumnPopulated();
});

Then("Logic will display possible values as {string}", (logic) => {
    businessRulesPage.displayLogics(logic);
});

Then("Function column is populated with Disable, Enable, Hide or Unhide", () => {
    businessRulesPage.checkFunctionColumnPopulated();
});

Then("Target fields column can be any subsections or questions in the current page", () => {
    businessRulesPage.checkTargetColumnPopulated();
});

Then("User enters a Unique name in the Search text field for Source field keyword", () => {
    businessRulesPage.enterUniqueNameInSearchField();
});

Then("User clicks the magnifying glass button", () => {
    businessRulesPage.clickBusinessRulesSearchBtn();
});

Then("Business Rule list will be filtered based on the keywords entered", () => {
    businessRulesPage.checkBusinessRulesListDisplayed();
});

Then("User enters a Unique ID", () => {
    businessRulesPage.enterUniqueIdInSearchField();
});

Then("Business Rule list will be filtered based on the Unique ID entered", () => {
    businessRulesPage.checkBusinessRulesListDisplayed();
});

