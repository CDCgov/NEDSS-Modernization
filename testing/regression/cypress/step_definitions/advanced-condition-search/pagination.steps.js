import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {paginationPage} from "@pages/advanced-condition-search/pagination.page";

Then("User clicks 2 in the pagination section", () => {
    paginationPage.verifyPaginationRows([2]);
});

Then("User should see the subsequent rows listed on the modal for the number of rows selected. Same results when paginating pages 3, 4, and 5", () => {
    paginationPage.verifyPaginationRows([3, 4, 5]);
});

Then("User click the Next", () => {
    paginationPage.clickNext();
});

Then("User should see the subsequent row of conditions listed on the Search and add conditions modal", () => {
    paginationPage.verifyRowsDisplaying();
});

Then("User click Next again", () => {
    paginationPage.clickNext();
});

Then("User should see the next list in sequence in search list", () => {
    paginationPage.verifyRowsDisplaying();
});

Then("User click the Previous link", () => {
    paginationPage.clickPrevious();
});

Then("User should see the previous list in sequence in search list", () => {
    paginationPage.verifyRowsDisplaying();
});

