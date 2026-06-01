import { When } from "@badeball/cypress-cucumber-preprocessor";
import { NEW_REPORT_UID, VALID_DATA_SOURCE_UID } from "./create.steps";

const VALID_REPORT_LIBRARY_ID = 10000001;
const VALID_SECTION_CODE = "1001";

function makeEditRequest(body) {
  return cy.request({
    method: "PUT",
    url: `${Cypress.config().baseUrl}nbs/api/report/configuration/${NEW_REPORT_UID}/${VALID_DATA_SOURCE_UID}`,
    body,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  }).as("apiResponse");
}

When(/^I send a PUT request to the edit endpoint with a valid report$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Edited Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "PRIVATE",
    filterRequests: [],
    description: "Edited Description",
  };
  
  makeEditRequest(validReport);
});

When(/^I send a PUT request to the edit endpoint with missing dataSourceId$/, () => {
  const validReport = {
    dataSourceId: null,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Edited Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "PRIVATE",
    filterRequests: [],
    description: "Edited Description",
  };
  
  makeEditRequest(validReport);
});

When(/^I send a PUT request to the edit endpoint with missing libraryId$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: null,
    reportTitle: "Edited Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "PRIVATE",
    filterRequests: [],
    description: "Edited Description",
  };
  
  makeEditRequest(validReport);
});

When(/^I send a PUT request to the edit endpoint with missing reportTitle$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: null,
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "PRIVATE",
    filterRequests: [],
    description: "Edited Description",
  };
  
  makeEditRequest(validReport);
});

When(/^I send a PUT request to the edit endpoint with missing sectionCode$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Edited Report Title",
    sectionCode: null,
    ownerId: 0,
    group: "PRIVATE",
    filterRequests: [],
    description: "Edited Description",
  };
  
  makeEditRequest(validReport);
});

When(/^I send a PUT request to the edit endpoint with missing ownerId$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Edited Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: null,
    group: "PRIVATE",
    filterRequests: [],
    description: "Edited Description",
  };
  
  makeEditRequest(validReport);
});

When(/^I send a PUT request to the edit endpoint with missing group$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Edited Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: null,
    filterRequests: [],
    description: "Edited Description",
  };
  
  makeEditRequest(validReport);
});