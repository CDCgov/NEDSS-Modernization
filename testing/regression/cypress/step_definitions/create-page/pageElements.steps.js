import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {pageElementsPage} from "@pages/create-page/pageElements.page";

Then("User navigates to Create New Page and views the page", () => {
    pageElementsPage.navigateToCreatePage();
    pageElementsPage.userViewsCreatePage();
});

Then("User should see the following required elements by {string} {string}", (string, string1) => {
    pageElementsPage.seeElementText(string, string1);
});

Then("User clicks in the Condition field", () => {
    pageElementsPage.clickConditionField();
});

Then("A drop-down box displays with a list of conditions", () => {
    pageElementsPage.dropdownConditionsOpen();
});

Then("User clicks the check box to select a single condition", () => {
    pageElementsPage.selectValueFromConditions();
});

Then("A single condition is added in the Conditions field", () => {
    pageElementsPage.conditionFieldHasValue();
});

Then("User clicks the up or down arrow - right-side of the field", () => {
    pageElementsPage.clickOnConditionDropdownArrow();
});

Then("Drop-down list box closes", () => {
    pageElementsPage.dropdownConditionsClose();
});

Then("User clicks in the Page name field", () => {
    pageElementsPage.clickPageNameField();
});

Then("Page name field is highlighted with a rectangular blue box", () => {
    pageElementsPage.pageNameFieldFocused();
});

Then("User enters a Page name in the text field", () => {
    pageElementsPage.enterValueInPageNameField();
});

Then("Page name field allows entry of text successfully", () => {
    pageElementsPage.pageNameFieldAllows();
});

Then("User clicks the Event Type field", () => {
    pageElementsPage.clickEventTypeField();
});

Then("Event Type field is highlighted with a rectangular blue box", () => {
    pageElementsPage.eventTypeFieldFocused();
});

Then("Drop-down box displays with the following required values by {string}", (string) => {
    pageElementsPage.eventTypeFieldHasValue(string);
});

Then("User clicks the Template field", () => {
    pageElementsPage.clickTemplateField();
});

Then("Template field is highlighted with a rectangular blue box", () => {
    pageElementsPage.templateFieldFocused();
});

Then("Drop-down box displays with a list of Templates to select", () => {
    pageElementsPage.templateFieldHasValueList();
});

Then("User selects a Template", () => {
    pageElementsPage.selectValueFromTemplateList();
});

Then("A Template is populated successfully in the Templates field template", () => {
    pageElementsPage.templateFieldHasValue();
});

Then("User clicks the MMG field", () => {
    pageElementsPage.clickMMGField();
});

Then("MMG field is highlighted with a rectangular blue box", () => {
    pageElementsPage.mmgFieldFocused();
});

Then("Drop-down box displays with a list of MMGs to select", () => {
    pageElementsPage.mmgFieldHasValueList();
});

Then("User selects an MMG", () => {
    pageElementsPage.selectValueFromMMGList();
});

Then("Message Mapping Guide is populated successfully in the MMG field", () => {
    pageElementsPage.mmgFieldHasValue();
});

Then("User clicks the Cancel button in the footer", () => {
    pageElementsPage.clickCancelButton();
});

Then("Add new page closes and user is returned to the Page Library", () => {
    pageElementsPage.checkPageLibraryShowing();
});

Then("User clicks the Page Library link - top left of the page", () => {
    pageElementsPage.clickPageLibraryLink();
});
