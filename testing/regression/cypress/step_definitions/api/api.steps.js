import { When, Then, attach, Given } from "@badeball/cypress-cucumber-preprocessor";

Given("I login to the generate token", () => {
  cy.request({
    method: "POST",
    url: "https://app.test.nbspreview.com/login",
    headers: {
      "Content-Type": "application/json",
    },
    body: {
      username: "superuser",
    },
  }).then((response) => {
    expect(response.status).to.eq(200);
    attach(`Response: ${JSON.stringify(response.body)}`);
    const authToken = response.body.token;
    cy.wrap(authToken).as("authToken");
    cy.log("Stored Token:", authToken);
    Cypress.env("authToken", authToken);
  });
});

Given("I get user information", () => {
  const authToken = Cypress.env("authToken");
  cy.request({
    method: "GET",
    url: "https://app.test.nbspreview.com/nbs/api/me",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    expect(response.status).to.eq(200);
    attach(`Response: ${JSON.stringify(response.body)}`);
  });
});

Given("create a new patient", () => {
  const authToken = Cypress.env("authToken");
  cy.request({
    method: "POST",
    url: "https://app.test.nbspreview.com/graphql",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${authToken}`,
    },
    body: {
      query:
        "mutation create($patient: PersonInput!) {\n  createPatient(patient: $patient) {\n    id\n    shortId\n  }\n}\n",
      variables: {
        patient: {
          comments: "Created for Development testing",
          asOf: "2023-11-22T18:10:48.199Z",
          names: [{ use: "L", first: "Aimee", last: "Schuppe" }],
        },
      },
    },
  }).then((response) => {
    expect(response.status).to.eq(200);
    attach(`Response: ${JSON.stringify(response.body)}`);
  });
});
