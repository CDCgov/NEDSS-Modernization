import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicEditOpenInvestigationPage from "cypress/e2e/pages/nbs-classic/editInvestigation.page";

When("Click on Open Investigation in the main menu bar", () => {
  classicEditOpenInvestigationPage.clickOpenInvestigation();
});

Then("Should land on the Open Investigation Queue page", () => {
  classicEditOpenInvestigationPage.verifyQueuePage();
});

Then("Click and view an Investigation", () => {
    classicEditOpenInvestigationPage.verifyInvestigation()
});

Then("Click Edit button in Open Investigation", () => {
    classicEditOpenInvestigationPage.clickEditBtnInOpenInvestigation()
});

Then("Edit info in the form in Open Investigation", (type) => {
    classicEditOpenInvestigationPage.editCommentInOpenInvestigation(type)
});

Then("Click submit on edit page in Open Investigation", () => {
    classicEditOpenInvestigationPage.clickSubmitBtnOpenInvestigation()
});
