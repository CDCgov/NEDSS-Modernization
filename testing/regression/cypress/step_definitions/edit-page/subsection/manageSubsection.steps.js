import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {addEditSearchDeleteQuestion} from "@pages/edit-page/addEditSearchDeleteQuestion.page";
import {manageSubsectionPage} from "@pages/edit-page/subsection/manageSubsection.page";

Then("User navigates to Edit page and views Edit page and is at Manage Subsection window", () => {
    manageSubsectionPage.navigateEditPage();
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
