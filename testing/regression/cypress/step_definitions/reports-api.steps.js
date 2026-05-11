import { When, Then } from "@badeball/cypress-cucumber-preprocessor";

const VALID_REPORT_UID = 1;
const VALID_DATA_SOURCE_UID = 1;
const VALID_REPORT_FILTER_UID = 1;

function makeRequest(body, action) {
  return cy.request({
    method: "POST",
    url: `${Cypress.config().baseUrl}nbs/api/report/${action}`,
    body,
    failOnStatusCode: false,
    headers: {
      "Content-Type": "application/json"
    }
  }).as("apiResponse");
}

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with a valid report execution request", (action) => {
  const validRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };
  
  makeRequest(validRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with missing reportUid", (action) => {
  const invalidRequest = {
    dataSourceUid: VALID_DATA_SOURCE_UID,
    reportUid: null,
    isExport: false
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with missing dataSourceUid", (action) => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    isExport: false
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with missing isExport", (action) => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with negative reportUid", (action) => {
  const invalidRequest = {
    reportUid: -1,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with negative dataSourceUid", (action) => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: -1,
    isExport: false
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with reportUid as string", (action) => {
  const invalidRequest = {
    reportUid: "invalid-reportUid",
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with dataSourceUid as string", (action) => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: "invalid-dataSourceUid",
    isExport: false
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with isExport as string", (action) => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: "invalid-isExport"
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with invalid basic filters", (action) => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    basicFilters: [{
      reportFilterUid: VALID_REPORT_FILTER_UID,
      values: null  //  Cannot be null
    }]
  };

  makeRequest(invalidRequest, action);
});

When("I send a POST request to \\/nbs\\/api\\/report\\/{string} with an invalid advanced filter", (action) => {
  const invalidRequest = {
    reportUid: VALID_REPORT_UID,
    dataSourceUid: VALID_DATA_SOURCE_UID,
    isExport: false,
    advancedFilter: {
      reportFilterUid: VALID_REPORT_FILTER_UID,
      logic: null   // Logic cannot be null
    }
  };

  makeRequest(invalidRequest, action);
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

Then("the response should contain serialization error for {string}", (fieldName) => {
  cy.get("@apiResponse").then((response) => {
    expect(response.status).to.eq(422);
    const bodyString = typeof response.body === 'string' ? response.body : JSON.stringify(response.body);
    expect(bodyString).to.include("invalid-" + fieldName);
  });
});