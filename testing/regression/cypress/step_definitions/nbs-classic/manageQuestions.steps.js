import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
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

Then("Click on a question in Question Library", () => {
    classicManageQuestionsPage.clickQuestionInQuestionList()
});
