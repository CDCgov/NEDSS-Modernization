import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {addEditSearchDeleteQuestion} from "@pages/edit-page/addEditSearchDeleteQuestion.page";
import {manageSubsectionPage} from "@pages/edit-page/subsection/manageSubsection.page";

Then("User navigates to Edit page and views Edit page and is at Manage Subsection window", () => {
    manageSubsectionPage.navigateEditPage();
});

Then("user is at Manage Subsection window", () => {
    manageSubsectionPage.openManageSubsectionWindow();
});

Then("clicks on the edit icon", () => {
    manageSubsectionPage.clickEditSubsectionIcon();
});

Then("verify user is at Edit subsection window with all the details", () => {
    manageSubsectionPage.verifyEditSubsectionIsVisible();
});

Then("verify Cancel and Save Changes button are available", () => {
    manageSubsectionPage.verifyButtonsAreVisible();
});

Then("verify Save changes button disabled by default", () => {
    manageSubsectionPage.checkSaveButtonDisabledByDefault();
});

Then("updates subsection name", () => {
    manageSubsectionPage.updateSubsectionName();
});

Then("verify Save changes button is enabled", () => {
    manageSubsectionPage.checkSaveButtonEnabled();
});

Then("click on Save changes button", () => {
    manageSubsectionPage.clickSaveBtn();
});

Then("verify user is brought back to manage subsection window", () => {
    manageSubsectionPage.checkManageSubsectionWindowVisible();
});

Then("verify changes in name are successfully displayed", () => {
    manageSubsectionPage.checkSubsectionNameUpdated();
});

Then("clicks on delete icon", () => {
    manageSubsectionPage.clickDeleteSubsectionIcon();
});

Then("user is given in line message {string}", (text) => {
    manageSubsectionPage.showWarningMessageOnSubsectionDelete(text);
});

Then("user clicks ok link", () => {
    manageSubsectionPage.clickOkLink();
});

Then("given options {string} and {string}", (text, text1) => {
    manageSubsectionPage.checkButtonsAreVisible(text, text1);
});

Then("user clicks on hide or unhide icon", () => {
    manageSubsectionPage.clickVisibilitySubsectionIcon(true);
});

Then("verify hide or unhide icon is greyed out if already visible state", () => {
    manageSubsectionPage.checkVisibilityIconTurnedOff(false);
});

Then("verify success message {string}", (text) => {
    manageSubsectionPage.verifyVisibilitySuccessMessage(text);
});

Then("user clicks on hide or unhide icon again", () => {
    manageSubsectionPage.clickVisibilitySubsectionIcon(false);
});

Then("verify hide or unhide icon is blue active if already invisible state", () => {
    manageSubsectionPage.checkVisibilityIconTurnedOff(true);
});

