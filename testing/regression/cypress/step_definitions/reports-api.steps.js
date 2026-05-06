import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

const REPORT_RUN_ENDPOINT = "http://localhost:8080/nbs/api/report/run";
const VALID_REPORT_UID = 1;
const VALID_DATA_SOURCE_UID = 1;

When("I send a POST request to \\/nbs\\/api\\/report\\/run with a valid report execution request", () => {
  const validRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    columnUids: [1, 2, 3],
    filters: []
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: validRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing reportUid", () => {
  const invalidRequest = {
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing dataSourceUid", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing isExport", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with reportUid as string", () => {
  const invalidRequest = {
    reportUid: "invalid",
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with dataSourceUid as string", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: "invalid",
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with isExport as string", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: "invalid"
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with invalid BasicFilter", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    filters: [{
      type: "BasicFilter",
      isBasic: true,
      reportFilterUid: "invalid", // Should be number
      values: []
    }]
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with invalid AdvancedFilter", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    filters: [{
      type: "AdvancedFilter",
      isBasic: false,
      logic: "invalid" // Should be Expr object
    }]
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

Then("the response status should be {int}", (statusCode) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(statusCode);
  });
});

Then("the response should contain a report result", () => {
  cy.get("@apiResponse").then((response) => {
    expect(response.body).to.have.property("content_type");
    expect(response.body).to.have.property("content");
    expect(response.body.content_type).to.eq("table");
  });
});

Then("the response should contain validation error for {string}", (fieldName) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(422);
    const bodyString = typeof response.body === 'string' ? response.body : JSON.stringify(response.body);
    expect(bodyString).to.include(fieldName);
  });
});