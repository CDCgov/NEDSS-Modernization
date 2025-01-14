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

Then("Enters an existing Unique ID and completes all required and applicable fields, selecting Text only", () => {
    addEditSearchDeleteQuestion.fillAllRequiredFields({ withUniqueID: true });
});

Then("An error message should display similar to {string}", (text) => {
    addEditSearchDeleteQuestion.errorMessageForDuplicateUniqueID(text);
});

Then("User clicks the trash icon for deleting a question", () => {
    addEditSearchDeleteQuestion.clickQuestionDeleteBtn();
});

Then("A confirmation pop-up modal displays {string} and {string}", (text, text1) => {
    addEditSearchDeleteQuestion.checkQuestionDeleteModalText(text, text1);
});

Then("User clicks Yes, delete to delete question", () => {
    addEditSearchDeleteQuestion.clickConfirmBtnToDeleteQuestion();
});

Then("A success message displays {string}", (text) => {
    addEditSearchDeleteQuestion.displaysQuestionDeleteSuccessMessage(text);
});

Then("Application deletes the selected question and remain on the page", () => {
    addEditSearchDeleteQuestion.checkPageStayedOnEdit();
});

Then("User enters a question already added to a page in the search field", () => {
    addEditSearchDeleteQuestion.enterExistingQuestionUniqueID();
});

Then("User clicks the magnifying glass icon", () => {
    addEditSearchDeleteQuestion.clickQuestionSearchBtn();
});

Then("Question already added to a page will not display in the question library", () => {
    addEditSearchDeleteQuestion.showEmptyQuestionSearchList();
});

Then("Message {string} and {string} button will display to create a new question", (text, text1) => {
    addEditSearchDeleteQuestion.showCreateNewSection(text, text1);
});

Then("User enters an Inactive question in the search field", () => {
    addEditSearchDeleteQuestion.enterInactiveQuestionInSearchField();
});

Then("Inactive question will not display in the question library", () => {
    addEditSearchDeleteQuestion.InactiveQuestionNotDisplayed();
});

Then("Unique ID field is blank", () => {
    addEditSearchDeleteQuestion.checkUniqueIDisBlank();
});

Then("All required and applicable fields completed", () => {
    addEditSearchDeleteQuestion.fillAllRequiredFields();
});

Then("Numeric field is selected with Mask as Integer And All other required and applicable fields completed", () => {
    addEditSearchDeleteQuestion.fillAllRequiredFields({ fieldTypeNumeric: true });
});

Then("Date picker field is selected with Date format And All other required and applicable fields completed", () => {
    addEditSearchDeleteQuestion.fillAllRequiredFields({ fieldTypeDatePicker: true });
});

Then("Value Set field is selected And New value set created And All other required and applicable fields completed", () => {
    addEditSearchDeleteQuestion.fillAllRequiredFields();
});
