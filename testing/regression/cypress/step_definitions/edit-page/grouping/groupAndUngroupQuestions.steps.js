import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {groupAndUngroupQuestions} from "@pages/edit-page/grouping/groupAndUngroupQuestions.page";

Then("User navigates to Edit page and views Edit page for Grouping", () => {
    groupAndUngroupQuestions.navigateEditPage();
});

Then("user already has grouped question for a subsection", () => {
    groupAndUngroupQuestions.checkForGroupedQuestions();
});

Then("user clicks on 3 dots to edit subsection", () => {
    groupAndUngroupQuestions.clickMenuIcon();
});

Then("user should see option for {string}", (text) => {
    groupAndUngroupQuestions.shouldSee(text);
});

Then("clicks on Ungroup questions", () => {
    groupAndUngroupQuestions.clickUngroupQuestionOption();
});

Then("verify a warning pop up window is opened", () => {
    groupAndUngroupQuestions.shouldSee('Warning');
});

Then("verify {string} and {string} button are available", (text1, text2) => {
    groupAndUngroupQuestions.shouldSee(text1, text2);
});

Then("user clicks on Ungroup button", () => {
    groupAndUngroupQuestions.clickUngroupBtn();
});

Then("verify success message subsection is ungrouped on top of screen", () => {
    groupAndUngroupQuestions.shouldSee("successfully ungrouped", null, true);
});

Then("subsection does not have 'R' next to subsection name", () => {
    groupAndUngroupQuestions.verifyQuestionUngrouped();
});

Then("user navigates to subsection which has grouped questions", () => {
    groupAndUngroupQuestions.checkForGroupedQuestions();
});

Then("click on edit subsection", () => {
    groupAndUngroupQuestions.clickEditSubsectionOption();
});

Then("verify user is brought to {string} pop window", (text) => {
    groupAndUngroupQuestions.shouldSee(text);
});

Then("verify edit subsection modal is prefilled with all the information for both sections", () => {
    groupAndUngroupQuestions.verifyEditSubsectionPrefilled();
});

Then("user should see {string} and {string} button are enabled", (text, text1) => {
    groupAndUngroupQuestions.shouldSee(text, text1);
});

Then("change Subsection name to new name", () => {
    groupAndUngroupQuestions.renameSubsection();
});

Then("change Block name to new block name", () => {
    groupAndUngroupQuestions.renameBlockName();
});

Then("change Data mart request number to 2", () => {
    groupAndUngroupQuestions.updateDatamartValue(2);
});

Then("change one of the question Appears in table to No", () => {
    groupAndUngroupQuestions.updateAppearsInTableValueToNo();
});

Then("change the required Table column % to meet 100%", () => {
    groupAndUngroupQuestions.checkTableColumnPercentage();
});

Then("verify Submit button is enabled", () => {
    groupAndUngroupQuestions.verifyGroupedSubmitButtonEnabled();
});

Then("user clicks on Submit button grouped", () => {
    groupAndUngroupQuestions.clickGroupedSubmitBtn();
});

Then("user is brought back to Edit page with success message on top", () => {
    groupAndUngroupQuestions.shouldSee("successfully grouped", null, true);
});

Then("verify all the changes made are visible on edit page subsection", () => {
    groupAndUngroupQuestions.shouldSee("Edited section name");
});

Then("user clicks on 3 dots to edit subsection to group", () => {
    groupAndUngroupQuestions.clickMenuIcon(true);
});

Then("user clicks on Group question", () => {
    groupAndUngroupQuestions.clickGroupQuestionOption();
});

Then("verify user is brought to Edit Subsection page", () => {
    groupAndUngroupQuestions.shouldSee("Edit subsection");
});

Then("verify Subsection name is prefilled", () => {
    groupAndUngroupQuestions.verifySubsectionNamePrefilled();
});

Then("Subsection Visible is default to yes", () => {
    groupAndUngroupQuestions.verifySubsectionVisible("true");
});

Then("enter Block name as TEST123", () => {
    groupAndUngroupQuestions.renameBlockName("TEST123");
});

Then("Data mart repeat number as 0", () => {
    groupAndUngroupQuestions.updateDatamartValue(0);
});

Then("verify below field in Repeating block section {string} and {string}", (text, text1) => {
    groupAndUngroupQuestions.shouldSee(text);
});

Then("user enter percentage for each question eg 50 for each when 2 questions to make {string}", () => {
    groupAndUngroupQuestions.fillTableColumnWidths();
});

Then("verify Submit button is available and enabled", () => {
    groupAndUngroupQuestions.verifyUngroupedSubmitButtonEnabled();
});

Then("user clicks on Submit button ungrouped", () => {
    groupAndUngroupQuestions.clickUngroupedSubmitBtn();
});

Then("verify user is brought back on edit draft page with success message", () => {
    groupAndUngroupQuestions.shouldSee("successfully grouped", null, true);
});

Then("verify {string} is displayed next to subsection name", (text) => {
    groupAndUngroupQuestions.shouldSee(text);
});

Then("clicked on Preview button", () => {
    groupAndUngroupQuestions.clickPreviewBtn();
});

Then("verify Subsection shows grouped with {string} on preview page with proper columns", (text) => {
    groupAndUngroupQuestions.shouldSee(text);
});
