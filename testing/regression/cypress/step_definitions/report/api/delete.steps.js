import { When } from "@badeball/cypress-cucumber-preprocessor";
import { NEW_REPORT_UID, VALID_DATA_SOURCE_UID } from "./create.steps";

function makeDeleteRequest(dataSourceId) {
  return cy.request({
    method: "DELETE",
    url: `${Cypress.config().baseUrl}nbs/api/report/configuration/${NEW_REPORT_UID}/${dataSourceId}`,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  }).as("apiResponse");
}

When(/^I send a DELETE request to the delete endpoint with a valid report id$/, () => {  
  makeDeleteRequest(VALID_DATA_SOURCE_UID);
});

When(/^I send a DELETE request to the delete endpoint with wrong report id$/, () => {  
  makeDeleteRequest(99999999);
});
