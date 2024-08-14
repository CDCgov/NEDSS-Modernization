import { attach, Given } from "@badeball/cypress-cucumber-preprocessor";
const user = Cypress.env("LOGIN_USERNAME");
const password = Cypress.env("LOGIN_PASSWORD");
const baseUrl = Cypress.config().baseUrl;

Given("I login to the generate token", () => {
  cy.request({
    method: "POST",
    url: `${baseUrl}login`,
    headers: {
      "Content-Type": "application/json",
    },
    body: {
      username: user,
      password: password,
    },
  }).then((response) => {
    expect(response.status).to.eq(200);
    attach(`Response: ${JSON.stringify(response.body)}`);
    const authToken = response.body.token;
    expect(authToken).to.not.equal(undefined);
    cy.wrap(authToken).as("authToken");
    Cypress.env("authToken", authToken);
  });
});

Given("I get user information", () => {
  const authToken = Cypress.env("authToken");
  cy.request({
    method: "GET",
    url: `${baseUrl}nbs/api/me`,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${authToken}`,
    },
  }).then((response) => {
    expect(response.status).to.eq(200);
    attach(`Response: ${JSON.stringify(response.body)}`);
  });
});
