import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicManageTemplatePage from "cypress/e2e/pages/nbs-classic/manageTemplates.page";

Then("Navigate to Template Library", () => {
    classicManageTemplatePage.navigateToTemplateLibrary()
});

Then("Click on Import in Template Library", () => {
    classicManageTemplatePage.clickImportBtnTemplateLibrary()
});

Then("Click on Choose File in Template Library", () => {
    classicManageTemplatePage.clickChooseFileBtnTemplateLibrary()
});

Then("Click filter button in Template Library", () => {
    classicManageTemplatePage.clickFilterIconTemplateLibrary()
});

Then("Enter filter text in the input", () => {
    classicManageTemplatePage.enterFilterTextInFilterInbox()
});

Then("Click OK button to filter", () => {
    classicManageTemplatePage.clickOKbtnTemplateLibrary()
});

Then("Click a template in template library", () => {
    classicManageTemplatePage.clickTemplateInTemplateList()
});

Then("Click View Rules button in Template view", () => {
    classicManageTemplatePage.clickViewRulesBtnTemplateLibrary()
});

Then("Verify rules listed in the results page", () => {
    classicManageTemplatePage.verifyRulesListedInResultsPage()
});

