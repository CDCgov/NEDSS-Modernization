import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {createNewPagePage} from "@pages/create-page/createNewPage.page";

Then("User views the Create new page button", () => {
    createNewPagePage.userViewsCreateNewPageButton();
});
Then("User clicks the Create new page button", () => {
    createNewPagePage.clickCreateNewPageButton();
});
Then("Add new page with the required field Event Type displays for user selection", () => {
    createNewPagePage.userViewsEventTypeField();
});
Then("User selects Event Type - Investigation", () => {
    createNewPagePage.clickCreateNewPageButton();
});
Then("Additional required fields and other information displays for user selection by {string} {string}", (string, string1) => {
    createNewPagePage.seeElementText(string, string1);
});
Then("User selects {string} from Event Type {string}", (string, string1) => {
    createNewPagePage.navigateToCreatePage();
    createNewPagePage.selectEventType(string1);
});

Then("Rectangular yellow box appears with the message: {string}", (string) => {
    createNewPagePage.viewTextOnPage(string);
});

Then("User clicks Create page button", () => {
    createNewPagePage.clickCreatePageButton();
});
Then("Application redirects to the classic design of Add page to continue", () => {
    createNewPagePage.navigateToClassicDesign();
});
Then("User already on Create new page with Event Type 'Investigation' selected", () => {
    createNewPagePage.navigateToCreatePage();
    createNewPagePage.selectEventType("INV");
});
Then("User selects an existing Condition", () => {
    createNewPagePage.selectCondition();
});
Then("User enters a Page name", () => {
    createNewPagePage.selectPageName();
});
Then("User selects an existing Template", () => {
    createNewPagePage.selectTemplate();
});
Then("User selects an existing Reporting mechanism", () => {
    createNewPagePage.selectReportingMechanism();
});
Then("User enters a Page description", () => {
    createNewPagePage.enterPageDescription();
});
Then("clicks the Create page button", () => {
    createNewPagePage.clickCreatePageButton();
});
Then("New page is saved to the database and the application directs the user to the Page library to edit the additional page information", () => {
    createNewPagePage.navigateToLibrary();
});