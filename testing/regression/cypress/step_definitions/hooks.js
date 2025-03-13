import { Before } from "@badeball/cypress-cucumber-preprocessor";

Before({ tags: "@skip-if-disabled-not-test" }, function () {
  if (Cypress.config().baseUrl !== "https://app.test.nbspreview.com/") {
    this.skip();
  }
});

Before({ tags: "@skip-if-disabled-not-int" }, function () {
  if (Cypress.config().baseUrl !== "https://app.int1.nbspreview.com/") {
    this.skip();
  }
});

Before({ tags: "@skip-if-disabled-is-int" }, function () {
  if (Cypress.config().baseUrl === "https://app.int1.nbspreview.com/") {
    this.skip();
  }
});