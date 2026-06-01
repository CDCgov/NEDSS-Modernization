import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

const VALID_DATA_SOURCE_UID = 1;
const VALID_REPORT_LIBRARY_ID = 10000001;
const VALID_SECTION_CODE = "1000";

export let NEW_REPORT_UID;
export let NEW_DATA_SOURCE_UID;

function makeCreateRequest(body) {
  return cy.request({
    method: "POST",
    url: `${Cypress.config().baseUrl}nbs/api/report/configuration`,
    body,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  }).as("apiResponse");
}

When(/^I send a POST request to the create endpoint with a valid report$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Test Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "Private",
    filterRequests: [],
    description: "Test Description",
  };
  
  makeCreateRequest(validReport);
});

When(/^I send a POST request to the create endpoint with missing dataSourceId$/, () => {
  const validReport = {
    dataSourceId: null,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Test Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "Private",
    filterRequests: [],
    description: "Test Description",
  };
  
  makeCreateRequest(validReport);
});

When(/^I send a POST request to the create endpoint with missing libraryId$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: null,
    reportTitle: "Test Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "Private",
    filterRequests: [],
    description: "Test Description",
  };
  
  makeCreateRequest(validReport);
});

When(/^I send a POST request to the create endpoint with missing reportTitle$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: null,
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: "Private",
    filterRequests: [],
    description: "Test Description",
  };
  
  makeCreateRequest(validReport);
});

When(/^I send a POST request to the create endpoint with missing sectionCode$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Test Report Title",
    sectionCode: null,
    ownerId: 0,
    group: "Private",
    filterRequests: [],
    description: "Test Description",
  };
  
  makeCreateRequest(validReport);
});

When(/^I send a POST request to the create endpoint with missing ownerId$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Test Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: null,
    group: "Private",
    filterRequests: [],
    description: "Test Description",
  };
  
  makeCreateRequest(validReport);
});

When(/^I send a POST request to the create endpoint with missing group$/, () => {
  const validReport = {
    dataSourceId: VALID_DATA_SOURCE_UID,
    libraryId: VALID_REPORT_LIBRARY_ID,
    reportTitle: "Test Report Title",
    sectionCode: VALID_SECTION_CODE,
    ownerId: 0,
    group: null,
    filterRequests: [],
    description: "Test Description",
  };
  
  makeCreateRequest(validReport);
});

Then("the response should contain a ReportId", () => {
  cy.get("@apiResponse").then((response) => {
    expect(response.body).to.have.property("reportUid");
    expect(response.body).to.have.property("dataSourceUid");

    NEW_REPORT_UID = response.body.reportUid;
    NEW_DATA_SOURCE_UID = response.body.dataSourceUid;
  });
});