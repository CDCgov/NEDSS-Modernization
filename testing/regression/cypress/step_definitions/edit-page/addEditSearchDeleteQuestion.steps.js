import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {addEditSearchDeleteQuestion} from "@pages/edit-page/addEditSearchDeleteQuestion.page";

Then("User navigates to Edit page and views Edit page and Subsection already expanded", () => {
    addEditSearchDeleteQuestion.navigateEditPage();
    addEditSearchDeleteQuestion.checkSubsectionExpanded();
});
Then("User clicks the Add question button", () => {
    addEditSearchDeleteQuestion.clickAddQuestionBtn();
});
Then("Question library pop-up modal displays", () => {
    addEditSearchDeleteQuestion.addQuestionModalDisplays();
});
Then("User clicks Create New button", () => {
    addEditSearchDeleteQuestion.clickCreateNewQuestionButton();
});
Then("Add question pop-up modal displays", () => {
    addEditSearchDeleteQuestion.checkAddQuestionModalVisibility();
});
Then("User completes all required and applicable fields Local or Phin, selecting Text only from Field Type", () => {
    addEditSearchDeleteQuestion.fillAllRequiredFields();
});
Then("User clicks Create and add to page button", () => {
    addEditSearchDeleteQuestion.clickCreateAndAddToPageBtn();
});
Then("A confirmation message displays {string}", (string) => {
    addEditSearchDeleteQuestion.checkConfirmationMessage(string);
});
Then("Add new question pop-up window will disappear with the newly added question displayed on Edit page", () => {
    addEditSearchDeleteQuestion.checkNewlyAddedQuestionDisplayed();
});
Then("User clicks the pencil icon for editing a question", () => {
    addEditSearchDeleteQuestion.clickQuestionEditBtn();
});
Then("Edit question pop-up modal displays", () => {
    addEditSearchDeleteQuestion.editQuestionModalDisplayed();
});
Then("User completes the applicable fields that are editable", () => {
    addEditSearchDeleteQuestion.updateQuestionDetails();
});
Then("User clicks the Save button", () => {
    addEditSearchDeleteQuestion.clickEditQuestionSaveBtn();
});
Then("Application will close the Edit question pop-window with the changes saved", () => {
    addEditSearchDeleteQuestion.closeEditQuestionModal();
});