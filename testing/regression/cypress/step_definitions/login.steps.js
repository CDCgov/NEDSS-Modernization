import { Given } from "@badeball/cypress-cucumber-preprocessor";
import { loginPage } from "cypress/e2e/pages/login.page";

Given("I am logged in as {string} and password {string}", (string, string2) => {
  loginPage.navigateToHomepage();
  loginPage.login(string, string2);
});
