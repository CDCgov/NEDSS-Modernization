import { Then } from "@badeball/cypress-cucumber-preprocessor";
import classicManageQuestionsPage from "@pages/nbs-classic/manageQuestions.page";

Then("Navigate to Question Library", () => {
    classicManageQuestionsPage.navigateToQuestionsLibrary()
});

Then("Click on Add new in Question Library", () => {
    classicManageQuestionsPage.clickAddNewBtn()
});

Then("Fill the details to create new {string} question", (type) => {
    classicManageQuestionsPage.fillTheDetailsLocalQuestion(type)
});

Then("Click submit button to create question", () => {
    classicManageQuestionsPage.clickSubmitBtnLocalQuestion()
});

Then("I should see a success message that the question has been successfully added to the system", () => {
    classicManageQuestionsPage.checkSuccessMessageQuestionAdded()
});


Then("I should see a success message that the question has been successfully saved to the system", () => {
    classicManageQuestionsPage.checkSuccessMessageQuestionSaved()
});

Then("Click on a question in Question Library", () => {
    classicManageQuestionsPage.clickQuestionInQuestionList()
});
