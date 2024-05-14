import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {pageElementsPage} from "@pages/create-page/pageElements.page";

Then("User navigates to Create New Page and views the page", () => {
    pageElementsPage.navigateToCreatePage();
    pageElementsPage.userViewsCreatePage();
});

Then("User should see the following required elements by {string} {string}", (string, string1) => {
    pageElementsPage.seeElementText(string, string1);
});
