import { Given } from "@badeball/cypress-cucumber-preprocessor";
import { loginPage } from "cypress/e2e/pages/login.page";

Given("I am logged in as secure user", () => {
  loginPage.navigateToHomepage();
  loginPage.login();
});
