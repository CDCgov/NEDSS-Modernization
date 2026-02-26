import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";

Then("user submits {string} vaccination with a popup and deletes it", (string) => {
  const vaccinationUrl = Cypress.config().baseUrl + "nbs/PageAction.do?method=createGenericLoad&businessObjectType=VAC&Action=DSFilePath";
  cy.visit(vaccinationUrl);
  cy.wait(1000);
  cy.get("td#tabs0head1").first().click({force: true});
  cy.get("#VAC101").select(string, {force: true});
  cy.get("input[name='Submit'").first().click();
  cy.contains("#successMessages", "Vaccination has been successfully saved in the system.");
  cy.get("input[name='Delete'").first().click();
  cy.on("window:confirm", () =>  {
    return true;
  });
  cy.contains("#successMessages", "Vaccination has been successfully deleted in the system.");
});
