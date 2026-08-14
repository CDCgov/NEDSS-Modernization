import { Then, When } from "@badeball/cypress-cucumber-preprocessor";
import classicManageValueSetsPage from "@pages/nbs-classic/manageValueSets.page";

Then("Navigate to Value Set Library", () => {
    classicManageValueSetsPage.navigateToValueSetsLibrary()
});

Then("Click on Add new in Value Set Library", () => {
    classicManageValueSetsPage.clickAddNewBtn()
});

When("I store the Value Set count", () => {
    classicManageValueSetsPage.storeValueSetCount()

});

Then("the Value Set count should increase by 1", () => {
    classicManageValueSetsPage.verifyValueSetIncreased()
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

Then("Enter filter text {string} in the input in Value Set library", (text) => {
    classicManageValueSetsPage.enterFilterTextValueSetLibrary(text)
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

Then("Verify all value set names contain {string}", text => {
    classicManageValueSetsPage.verifyValueSetNamesContain(text)
});

Then("the Value Set page should be collapsed", () => {
    classicManageValueSetsPage.verifyValueSetPageCollapsed()
});

Then("the Value Set page should be expanded", () => {
    classicManageValueSetsPage.verifyValueSetPageExpanded()
});


