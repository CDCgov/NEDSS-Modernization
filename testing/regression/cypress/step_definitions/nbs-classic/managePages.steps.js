import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicManagePagesPage from "cypress/e2e/pages/nbs-classic/managePages.page";

Then("Navigate to Page Library on classic", () => {
    classicManagePagesPage.navigateToPageLibrary()
});

Then("Click on Add New button on add new classic page", () => {
    classicManagePagesPage.clickAddNewBtn()
});

Then("Select Page as {string} type on add new classic page", (pageType) => {
    classicManagePagesPage.selectPageType(pageType)
});

Then("Select Template form dropdown on add new classic page", () => {
    classicManagePagesPage.selectTemplate()
});

Then("Select Message Mapping Guide from dropdown on add new classic page", () => {
    classicManagePagesPage.selectMappingGuide()
});

Then("Enter Page name on add new classic page", () => {
    classicManagePagesPage.enterPageName()
});

Then("Click on Submit button on add new classic page", () => {
    classicManagePagesPage.clickSubmitBtn()
});

Then("Click on view icon to display the page details on classic", () => {
    classicManagePagesPage.clickViewIcon()
});

Then("Check details displayed for {string}", (header) => {
    classicManagePagesPage.checkDisplayed(header)
});

Then("Check {string} tab displayed in investigation page details", (tabName) => {
    classicManagePagesPage.checkDisplayed(tabName)
});

Then("Click on Page Details button on investigation page view", () => {
    classicManagePagesPage.clickPageDetailsBtn()
});

Then("Click on Clone Page button on investigation page details view", () => {
    classicManagePagesPage.clickClonePageBtn()
});

Then("Add Related Conditions on details page classic page", () => {
    classicManagePagesPage.addRelatedConditions()
});

Then("Click on Submit button in clone page on add new classic page", () => {
    classicManagePagesPage.clickSubmitBtn2()
});

Then("Click on Edit button on investigation page details view", () => {
    classicManagePagesPage.clickEditBtn()
});

Then("Re-enter Page description on add new classic page", () => {
    classicManagePagesPage.enterDescription()
});

Then("Click on Submit button in edit page on add new classic page", () => {
    classicManagePagesPage.clickSubmitBtn2()
});

Then("Click on Page Rules button on investigation page view", () => {
    classicManagePagesPage.clickPageRulesBtn()
});

Then("Click on Add New button on investigation page rules view", () => {
    classicManagePagesPage.clickAddNewBtn()
});

Then("Select Function form dropdown on add new rule classic page", () => {
    classicManagePagesPage.selectFunction()
});

Then("Select Source form dropdown on add new rule classic page", () => {
    classicManagePagesPage.selectSource()
});

Then("Select Logic form dropdown on add new rule classic page", () => {
    classicManagePagesPage.selectLogic()
});

Then("Select Target form dropdown on add new rule classic page", () => {
    classicManagePagesPage.selectTarget()
});

Then("Click on Submit button in add new rule on classic page", () => {
    classicManagePagesPage.clickSubmitBtn2()
});

Then("Click on Edit button on investigation view page", () => {
    classicManagePagesPage.clickEditBtnInViewPage()
});

Then("Click Add Elements Icon in Edit page", () => {
    classicManagePagesPage.clickAddElements()
});

Then("Select Static Element as Element Type in Add Element page", () => {
    classicManagePagesPage.selectStaticElement()
});

Then("Select static element type from dropdown", () => {
    classicManagePagesPage.selectStaticElementType()
});

Then("Click on Submit Button in Add Element page", () => {
    classicManagePagesPage.clickSubmitBtnInElementPage()
});

Then("Click on Close Button in Add Element page", () => {
    classicManagePagesPage.clickCloseBtnInAddElementPage()
});
