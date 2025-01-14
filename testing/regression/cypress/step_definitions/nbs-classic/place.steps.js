import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import ClassicPlaceSearchPage from "cypress/e2e/pages/nbs-classic/place.page";

Then("Navigate to classic place search page", () => {
    ClassicPlaceSearchPage.navigateToAddPlace()
});
