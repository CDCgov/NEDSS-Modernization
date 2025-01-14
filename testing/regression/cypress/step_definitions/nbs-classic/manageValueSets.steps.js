import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import classicManageValueSetsPage from "cypress/e2e/pages/nbs-classic/manageValueSets.page";

Then("Navigate to Value Set Library", () => {
    classicManageValueSetsPage.navigateToValueSetsLibrary()
});

Then("Click on Add new in Value Set Library", () => {
    classicManageValueSetsPage.clickAddNewBtn()
});

Then("Fill the details to create new {string} Value Set", (type) => {
    classicManageValueSetsPage.fillTheDetailsValueSetFields(type)
});

Then("Click submit button to create Value Set", () => {
    classicManageValueSetsPage.clickSubmitBtnValueSetForm()
});

Then("Click filter button in Value Set library", () => {
    classicManageValueSetsPage.clickFilterBtnValueSetLibrary()
});

Then("Enter filter text in the input in Value Set library", () => {
    classicManageValueSetsPage.enterFilterTextValueSetLibrary()
});

Then("Click OK button to filter in Value Set library", () => {
    classicManageValueSetsPage.clickFilterOkBtnValueSetLibrary()
});

Then("Click on a Value set in Value Set library", () => {
    classicManageValueSetsPage.clickValueSetInValueSetList()
});

Then("Click Collapse Subsections to collapse the sections in Value Set library", () => {
    classicManageValueSetsPage.clickCollapseSubsectionsInValueSetList()
});

Then("Click Expand Subsections to expand the sections in Value Set library", () => {
    classicManageValueSetsPage.clickExpandSubsectionsInValueSetList()
});

Then("Click on Add new in Value Set Concept section", () => {
    classicManageValueSetsPage.clickAddNewBtnInValueSetConceptSection()
});

Then("Fill the details to create new concept Value Set", () => {
    classicManageValueSetsPage.fillTheDetailsNewValueSetConcept()
});

Then("Click submit button to create new concept in Value Set", () => {
    classicManageValueSetsPage.clickSubmitBtnInValueSetConceptForm()
});

Then("Click on Make Inactive button to inactive the value set", () => {
    classicManageValueSetsPage.clickMakeInactiveInValueSet()
});