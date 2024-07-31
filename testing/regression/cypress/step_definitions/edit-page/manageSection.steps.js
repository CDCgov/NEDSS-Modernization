import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {manageSectionPage} from "@pages/edit-page/manageSection.page";

Then("User navigates to Edit page and views Manage section pop-up window", () => {
    manageSectionPage.navigateEditPage();
    manageSectionPage.openManageSectionsPopup();
});

Then("User will see the following by {string} {string} {string}", (content, type, description) => {
    manageSectionPage.seeElementOnManageSection(content, type, description);
});

Then("User views the trash icon to the right of the section name", () => {
    manageSectionPage.viewTrashIcon();
});

Then("User clicks the trash icon", () => {
    manageSectionPage.clickTrashIcon();
});

Then("Yellow inline message {string} displays above the section name with options {string} and {string}", (string, string1, string2) => {
    manageSectionPage.viewDeleteConfirmationDialogText([string, string1, string2]);
});

Then("User clicks Yes, delete button", () => {
    manageSectionPage.clickYesDeleteBtn();
});

Then("Yellow banner message closes", () => {
    manageSectionPage.closeDeleteConfirmationDialog();
});

Then("Green inline confirmation message {string} whatever the section name displays under the Manage sections heading at the top", (string) => {
    manageSectionPage.showDeleteConfirmationText(string);
});

Then("Deleted section is removed from Manage sections modal and Edit page", () => {
    manageSectionPage.checkSectionDeleted();
});

Then("verify page header as {string}", (title) => {
    manageSectionPage.verifyManageSectionsHeader(title);
});

Then("click on 'Add new section' from the pop up window", () => {
    manageSectionPage.clickAddNewSection();
});

Then("enter section name", () => {
    manageSectionPage.enterSectionName();
});

Then("click on 'Add section' button", () => {
    manageSectionPage.clickAddSectionBtn();
});

Then("verify same section is visible in edit page view", () => {
    manageSectionPage.checkAddedSectionExist();
});

Then("User views the pencil icon to the right of the section name", () => {
    manageSectionPage.viewPencilIcon();
});

Then("User clicks the pencil icon", () => {
    manageSectionPage.clickPencilIcon();
});

Then("Edit section modal window displays", () => {
    manageSectionPage.viewEditSectionModalWindow();
});

Then("User modifies the section name", () => {
    manageSectionPage.modifySectionName();
});

Then("clicks the Save button", () => {
    manageSectionPage.clickSaveBtn();
});

Then("Edit section modal closes", () => {
    manageSectionPage.closeEditSectionModal();
});

Then("Inline confirmation message {string} displays under the Manage sections heading at the top", (text) => {
    manageSectionPage.checkConfirmationMessageShowing(text);
});
