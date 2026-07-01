import { Given } from "@badeball/cypress-cucumber-preprocessor";
import { loginPage } from "cypress/e2e/pages/login.page";

Given("I am logged in as secure user", () => {
  loginPage.navigateToHomepage();
  loginPage.login();
});

Given("I am logged in as {string} username", (username) => {
  loginPage.navigateToHomepage();
  loginPage.loginAsUserName(username);
});

Given("I clear cookies", () => {
  // need to clear cookies after different user login
  cy.clearCookies()
})

Given("I am logged in as secure user and stay on classic", () => {
  loginPage.navigateToHomepage();
  loginPage.loginStayOnClassic();
});
