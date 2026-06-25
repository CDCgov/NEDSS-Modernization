import { Given } from "@badeball/cypress-cucumber-preprocessor";
import { loginPage } from "cypress/e2e/pages/login.page";

Given("I am logged in as secure user", () => {
  loginPage.navigateToHomepage();
  loginPage.login();
});

Given("I am logged in as {string} username", (username) => {
  cy.clearCookies()
  loginPage.navigateToHomepage();
  loginPage.loginAsUserName(username);
});

Given("I am logged in as secure user and stay on classic", () => {
  loginPage.navigateToHomepage();
  loginPage.loginStayOnClassic();
});
