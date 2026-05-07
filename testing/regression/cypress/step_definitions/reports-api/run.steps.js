import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

const REPORT_RUN_ENDPOINT = `${Cypress.config().baseUrl}nbs/api/report/run`;
const VALID_REPORT_UID = 1;
const VALID_DATA_SOURCE_UID = 1;

function buildRequest(body) {
  return {
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  };
}

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing reportUid", () => {
  const invalidRequest = {
    dataSourceUid: VALID_DATA_SOURCE_UID,
    reportUid: null,
    isExport: false
  };

  cy.request(buildRequest(invalidRequest)).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing dataSourceUid", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    isExport: false
  };

  cy.request(buildRequest(invalidRequest)).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with missing isExport", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID
  };

  cy.request(buildRequest(invalidRequest)).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with negative reportUid", () => {
  const invalidRequest = {
    reportUid: -1,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  cy.request(buildRequest(invalidRequest)).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with negative dataSourceUid", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: -1,
    isExport: false
  };

  cy.request(buildRequest(invalidRequest)).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with reportUid as string", () => {
  const invalidRequest = {
    reportUid: "invalid-reportUid",
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  cy.request(buildRequest(invalidRequest)).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with dataSourceUid as string", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: "invalid-dataSourceUid",
    isExport: false
  };

  cy.request(buildRequest(invalidRequest)).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with isExport as string", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: "invalid-isExport"
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with invalid basic filters", () => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    basicFilters: [{
      reportFilterUid: VALID_REPORT_UID,
      values: []  //  Cannot be empty
    }]
  };

  cy.request({
    method: "POST",
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

When("I send a POST request to \\/nbs\\/api\\/report\\/run with an invalid advanced filter", () => {
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
    url: REPORT_RUN_ENDPOINT,
    body: invalidRequest,
    failOnStatusCode: false
  }).as("apiResponse");
});

Then("the run response status should be {int}", (statusCode) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(statusCode);
  });
});

Then("the run response should contain a report result", () => {
  cy.get("@apiResponse").then((response) => {
    expect(response.body).to.have.property("content_type");
    expect(response.body).to.have.property("content");
    expect(response.body.content_type).to.eq("table");
  });
});

Then("the run response should contain validation error for {string}", (fieldName) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(422);
    const bodyString = typeof response.body === 'string' ? response.body : JSON.stringify(response.body);
    expect(bodyString).to.include(fieldName);
  });
});

Then("the run response should contain serialization error for {string}", (fieldName) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(422);
    const bodyString = typeof response.body === 'string' ? response.body : JSON.stringify(response.body);
    expect(bodyString).to.include("invalid-" + fieldName);
  });
});