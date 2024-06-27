import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {rowSelectionPage} from "@pages/advanced-condition-search/rowSelection.page";

Then("User navigates to Create New Page and Advanced condition search modal already displayed", () => {
    rowSelectionPage.navigateToCreateNewPage();
    rowSelectionPage.openSearchModal();
});

Then("User views the Search and add conditions modal", () => {
    rowSelectionPage.verifySearchModalOpen();
});

Then("User should see 10 rows of conditions by default for selection", () => {
    rowSelectionPage.verifyRowsByDefault();
});

Then("User clicks in the numeric selection box and selects 50", () => {
    rowSelectionPage.updateRowsSelection("50");
});

Then("Numeric selection is saved to the system", () => {
    rowSelectionPage.verifyRowsSelectionUpdated("50");
});

Then("User clicks the X symbol to close the modal", () => {
    rowSelectionPage.closeSearchModal();
});

Then("clicks the Advanced condition search button on Add new page", () => {
    rowSelectionPage.clickAdvancedSearchBtn();
});

Then("User should see only 50 rows of conditions listed, and for each subsequent list", () => {
    rowSelectionPage.verifyRowsDisplaying(50);
});

