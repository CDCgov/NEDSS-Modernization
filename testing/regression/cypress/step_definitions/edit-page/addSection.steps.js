import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {addSectionPage} from "@pages/edit-page/addSection.page";

Then("User navigates to Edit page and views Add a section pop-up window", () => {
    addSectionPage.navigateEditPage();
    addSectionPage.openAddSectionPopup();
});

Then("User enters a section name in the the required text field", () => {
    addSectionPage.enterSectionName();
});

Then("User clicks the Add section button enabled", () => {
    addSectionPage.clickAddSectionBtn();
});

Then("Confirmation success message will display for 3 to 5 seconds", () => {
    addSectionPage.checkAlertIsShowing();
});

Then("Newly created section will be displayed with the entered information on Edit page", () => {
    addSectionPage.checkNewlyCreatedSectionShowing();
});
