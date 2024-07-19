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

Then("User clicks the magnifying glass button business rules library search", () => {
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

Then("User should see by default 10 rows of rules listed in the library in Business Rules", () => {
    businessRulesPage.checkBusinessRulesLibraryDefaultRows();
});

Then("User select 50 to show the list of business rules", () => {
    businessRulesPage.selectRowsFromDisplayDropdown("50");
});

Then("User should see only 50 rows in the library and for each subsequent list where applicable", () => {
    businessRulesPage.checkBusinessRulesListMatchingRows(50);
});

Then("Add new business rules already displayed", () => {
    businessRulesPage.clickAddBusinessRuleBtn();
    businessRulesPage.addNewBusinessRulesModalDisplayed();
});

Then("Function Enable is selected", () => {
    businessRulesPage.selectEnabled();
});

Then("User enters all required and applicable fields with logic is equal to", () => {
    businessRulesPage.completeAllRequiredFields('EQUAL_TO');
});

Then("User clicks the Add to library button in new business rules modal", () => {
    businessRulesPage.clickAddToLibraryBtnNewBusinessRulesModel();
});

Then("Application will direct the user to the Business Library with the entries populated in the applicable columns", () => {
    businessRulesPage.checkRedirectedToLibraryPage();
});

Then("Function Disable is selected", () => {
    businessRulesPage.selectDisabled();
});

Then("User enters all required and applicable fields with logic is not equal to", () => {
    businessRulesPage.completeAllRequiredFields('NOT_EQUAL_TO');
});

Then("Function Hide is selected", () => {
    businessRulesPage.selectHide();
});

Then("Function Unhide is selected", () => {
    businessRulesPage.selectUnhide();
});

Then("Function Require if is selected", () => {
    businessRulesPage.selectRequireIf();
});

Then("User clicks one of the questions in the Source Field", () => {
    businessRulesPage.clickQuestion();
});

Then("Edit business rules page displays", () => {
    businessRulesPage.editBusinessRulesPageDisplayed();
});

Then("User clicks the Delete button in edit business rules page", () => {
    businessRulesPage.clickDeleteEditBusinessRulesPage();
});

Then("Warning message is presented {string} and {string}", (text) => {
    businessRulesPage.checkWarningMessage(text);
});

Then("User clicks the Yes, delete button in edit business rules page", () => {
    businessRulesPage.clickYesDeleteBtnEditBusinessRulesPage();
});

Then("Application will delete the selected business rule and return to the business rule list screen", () => {
    businessRulesPage.checkRedirectedToLibraryPage();
});
