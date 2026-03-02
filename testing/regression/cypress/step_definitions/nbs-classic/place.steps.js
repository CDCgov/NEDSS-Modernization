import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import ClassicPlaceSearchPage from "@pages/nbs-classic/place.page";

Then("Navigate to classic place search page", () => {
    ClassicPlaceSearchPage.navigateToAddPlace()
});
