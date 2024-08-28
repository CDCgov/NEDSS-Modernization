import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {createNewQuestionPage} from "cypress/e2e/pages/question-library/createNewQuestion.page";

Then("Click on Create New button", () => {
    createNewQuestionPage.clickCreateNewQuestionButton();
});

Then("select LOCAL as your option", () => {
    createNewQuestionPage.selectLocalOption();
});

Then("enter unique id if not system will autogenerate it own", () => {
    createNewQuestionPage.enterUniqueId();
});

Then("enter unique name", () => {
    createNewQuestionPage.enterUniqueName();
});

Then("select a subgroup Clinical Information or any from dropdown", () => {
    createNewQuestionPage.subgroupClinicalInformation();
});

Then("enter Description as same as unique name or any", () => {
    createNewQuestionPage.enterDescription();
});

Then("select field type as Value Set", () => {
    createNewQuestionPage.fieldTypeValueSet();
});

Then("verify you have more set of questions available", () => {
    createNewQuestionPage.verifyQuestionsAvailable();
});

Then("select a value set Assigning Authority from drop down or click on search value set", () => {
    createNewQuestionPage.selectValueSet();
});

Then("select a default value Centers for Disease Control and Prevention", () => {
    createNewQuestionPage.selectDefaultValueField();
});

Then("enter Question Label", () => {
    createNewQuestionPage.enterQuestionLabel();
});

Then("enter Tool Tip", () => {
    createNewQuestionPage.enterTooltip();
});

Then("select Display Type as Single or multiple select or code lookup from dropdown", () => {
    createNewQuestionPage.selectDisplayType();
});

Then("enter default label in report", () => {
    createNewQuestionPage.EnterDefaultLabelReport();
});

Then("enter RDB column name as RDB_DBO.Investigation", () => {
    createNewQuestionPage.EnterRdbColumnName();
});

Then("verify Data mart column name auto populates same as RDB column name 'RDB_DBO.Investigation", () => {
    createNewQuestionPage.dataMartColumnNamePopulated();
});

Then("toggle messaging to included", () => {
    createNewQuestionPage.toggleMessagingIncluded();
});

Then("enter message variable id as AA12345", () => {
    createNewQuestionPage.enterMessageVariableId();
});

Then("enter message label as Assigning Authority", () => {
    createNewQuestionPage.enterMessageLabel();
});

Then("select Code system name as Entity Code from dropdown", () => {
    createNewQuestionPage.selectCodeSystem();
});

Then("toggle to required in message", () => {
    createNewQuestionPage.toggleRequiredInMessage();
});

Then("select HL7 data type from dropdown as CE or any", () => {
    createNewQuestionPage.selectHl7DataType();
});

Then("verify next 2 sections are disabled and has default value as OBX-3.0 and Group 2", () => {
    createNewQuestionPage.checkFieldsAreDisabled();
});

Then("enter Administrative comments test", () => {
    createNewQuestionPage.enterAdministrativeComments();
});

Then("verify Create and apply to page button is enabled", () => {
    createNewQuestionPage.createAndApplyToPageBtnIsEnabled();
});

Then("user clicks on create and apply to page button", () => {
    createNewQuestionPage.clickCreateAndAddToPageBtn();
});

Then("verify user sees success message and question is added with all the selections made by the user on Edit draft page", () => {
    createNewQuestionPage.checkConfirmationMessage();
});

Then("navigate to page library page", () => {
    createNewQuestionPage.navigatePageLibrary();
});

Then("verify question added is in the list by searching the unique id", () => {
    createNewQuestionPage.navigateEditPage();
    createNewQuestionPage.clickAddQuestionBtn();
    createNewQuestionPage.searchWithCreatedQuestion();
    createNewQuestionPage.clickSearchButton();
});
