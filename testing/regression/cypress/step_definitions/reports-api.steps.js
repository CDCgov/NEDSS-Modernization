import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

When("I send a POST request to {string} with a valid report execution request", (endpoint) => {
  const validRequest = {
    reportUid: 123,
    dataSourceUid: 456,
    isExport: true,
    columnUids: [1, 2, 3],
    filters: []
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: validRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with missing reportUid", (endpoint) => {
  const invalidRequest = {
    dataSourceUid: 456,
    isExport: true
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with missing dataSourceUid", (endpoint) => {
  const invalidRequest = {
    reportUid: 123,
    isExport: true
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with missing isExport", (endpoint) => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with reportUid as string", (endpoint) => {
  const invalidRequest = {
    reportUid: "invalid",
    dataSourceUid: 456,
    isExport: true
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with dataSourceUid as string", (endpoint) => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: "invalid",
    isExport: true
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with isExport as string", (endpoint) => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456,
    isExport: "invalid"
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with invalid BasicFilter", (endpoint) => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456,
    isExport: true,
    filters: [{
      type: "BasicFilter",
      isBasic: true,
      reportFilterUid: "invalid", // Should be number
      values: []
    }]
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to "/nbs/api/report/execute" with invalid AdvancedFilter", (endpoint) => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456,
    isExport: true,
    filters: [{
      type: "AdvancedFilter",
      isBasic: false,
      logic: "invalid" // Should be Expr object
    }]
  };

  cy.request({
    method: "POST",
    url: endpoint,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

Then("the response status should be {int}", (statusCode) => {
  cy.get("@apiResponse").should((response) => {
    expect(response.status).to.eq(statusCode);
  });
});

Then("the response should contain a report result", () => {
  cy.get("@apiResponse").should((response) => {
    expect(response.body).to.have.property("content_type");
    expect(response.body).to.have.property("content");
    expect(response.body.content_type).to.eq("table");
  });
});

Then("the response should contain validation error for {string}", (fieldName) => {
  cy.get("@apiResponse").should((response) => {
    expect(response.status).to.eq(422);
    const bodyString = typeof response.body === 'string' ? response.body : JSON.stringify(response.body);
    expect(bodyString).to.include(fieldName);
  });
});