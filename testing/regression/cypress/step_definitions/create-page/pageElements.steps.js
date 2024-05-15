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
