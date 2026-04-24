import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

When("I send a POST request to \\/nbs\\/api\\/report\\/run with a valid report execution request", () => {
  const validRequest = {
    reportUid: 123,
    dataSourceUid: 456,
    isExport: false,
    columnUids: [1, 2, 3],
    filters: []
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
    body: validRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing reportUid", () => {
  const invalidRequest = {
    dataSourceUid: 456,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing dataSourceUid", () => {
  const invalidRequest = {
    reportUid: 123,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing isExport", () => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with reportUid as string", () => {
  const invalidRequest = {
    reportUid: "invalid",
    dataSourceUid: 456,
    isExport: false
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with dataSourceUid as string", () => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: "invalid",
    isExport: false
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with isExport as string", () => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456,
    isExport: "invalid"
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with invalid BasicFilter", () => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456,
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
    url: "/nbs/api/report/run",
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with invalid AdvancedFilter", () => {
  const invalidRequest = {
    reportUid: 123,
    dataSourceUid: 456,
    isExport: false,
    filters: [{
      type: "AdvancedFilter",
      isBasic: false,
      logic: "invalid" // Should be Expr object
    }]
  };

  cy.request({
    method: "POST",
    url: "/nbs/api/report/run",
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