import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";

Then("user submits {string} vaccination with a popup and deletes it", (string) => {
  cy.visit("https://app.int1.nbspreview.com/nbs/PageAction.do?method=createGenericLoad&businessObjectType=VAC&Action=DSFilePath");
  cy.wait(1000)
  cy.get("td#tabs0head1").first().click({force: true})
  cy.get("#VAC101").select(string, {force: true})
  cy.get("input[name='Submit'").first().click();
  cy.get("#successMessages").contains("Vaccination has been successfully saved in the system.")
  cy.get("input[name='Delete'").first().click();
  cy.on("window:confirm", (t) =>  {
    return true;
  });
});