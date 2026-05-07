import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

const REPORT_EXPORT_ENDPOINT = "http://localhost:8080/nbs/api/report/export";
const VALID_REPORT_UID = 1;
const VALID_DATA_SOURCE_UID = 1;

When("I send a POST request to \\/nbs\\/api\\/report\\/export with missing reportUid", () => {
  const invalidRequest = {
    dataSourceUid: VALID_DATA_SOURCE_UID,
    reportUid: null,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with missing dataSourceUid", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with missing isExport", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with negative reportUid", () => {
  const invalidRequest = {
    reportUid: -1,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with negative dataSourceUid", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: -1,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with reportUid as string", () => {
  const invalidRequest = {
    reportUid: "invalid-reportUid",
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with dataSourceUid as string", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: "invalid-dataSourceUid",
    isExport: false
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with isExport as string", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: "invalid-isExport"
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with invalid basic filters", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    basicFilters: [{
      reportFilterUid: VALID_REPORT_UID,
      values: null  //  Cannot be null
    }]
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/export with an invalid advanced filter", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    advancedFilter: {
      reportFilterUid: VALID_REPORT_UID,
      logic: null   // Logic cannot be null
    }
  };

  cy.request({
    method: "POST",
    url: REPORT_EXPORT_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

Then("the export response status should be {int}", (statusCode) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(statusCode);
  });
});

Then("the export response should contain a report result", () => {
  cy.get("@apiResponse").then((response) => {
    expect(response.body).to.have.property("content_type");
    expect(response.body).to.have.property("content");
    expect(response.body.content_type).to.eq("table");
  });
});

Then("the export response should contain validation error for {string}", (fieldName) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(422);
    const bodyString = typeof response.body === 'string' ? response.body : JSON.stringify(response.body);
    expect(bodyString).to.include(fieldName);
  });
});

Then("the export response should contain serialization error for {string}", (fieldName) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(422);
    const bodyString = typeof response.body === 'string' ? response.body : JSON.stringify(response.body);
    expect(bodyString).to.include("invalid-" + fieldName);
  });
});