import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {deleteSectionPage} from "@pages/edit-page/deleteSection.page";

Then("User navigates to Edit page and views Edit page", () => {
    deleteSectionPage.navigateEditPage();
});

Then("clicks on 3 dots next to section", () => {
    deleteSectionPage.clickMenuIcon();
});

Then("click on 'Delete section' link", () => {
    deleteSectionPage.clickDeleteSubsection();
});

Then("verify warning pop up window is displayed with message {string} and {string}", (title, description) => {
    deleteSectionPage.verifyWaringMessage(title, description);
});

Then("verify {string} button is displayed and enabled section will be displayed with the entered information on Edit page", (text) => {
    deleteSectionPage.verifyOkayBtnShowing(text);
});

Then("clicks on 3 dots next to section without subsections", () => {
    deleteSectionPage.clickMenuIconWithoutSubsections();
});
