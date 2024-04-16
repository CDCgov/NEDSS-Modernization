import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import summaryTabPage from "cypress/e2e/pages/summaryTab.page";

Then(
  "user is able to click the Investigation Number to View Investigation page",
  () => {
    summaryTabPage.openFirstInvestigation();
  }
);

Then(
  "user clicks on Document type {string}, the View Lab Report page is displayed",
  (string) => {
    summaryTabPage.openLinkInDocumentTable(string);
  }
);

Then(
  "user is able to click the {string} link to return to Patient Profile Summary page",
  (string) => {
    summaryTabPage.returnToSummary();
  }
);

Then(
  "user clicks the {string} link, the user is returned to Patient profile summary page",
  (string) => {
    cy.contains("a", string).click();
    cy.wait(1000);
  }
);

When(
  "the User sorts Open Investigations by {string} {string}",
  (column, string2) => {
    summaryTabPage.sort(column, string2);
  }
);

Then(
  "Open Investigations are sorted by {string} {string}",
  (column, sortedBy) => {
    summaryTabPage.checkIfSorted(column, sortedBy);
  }
);

When(
  "the User sorts Documents requiring review by {string} {string}",
  (column, string2) => {
    summaryTabPage.documentTablesort(column, string2);
  }
);

Then(
  "Documents requiring review are sorted by {string} {string}",
  (column, sortedBy) => {
    summaryTabPage.documentTableCheckIfSorted(column, sortedBy);
  }
);