import { faker } from "@faker-js/faker";

class NameEntryPage {
  enterValidFirstAndLastName() {
    const firstName = faker.person.firstName();
    const lastName = faker.person.lastName();

    // Store names for later validation
    cy.wrap(firstName).as("firstName");
    cy.wrap(lastName).as("lastName");

    // Enter the names into the respective fields
    cy.get("#first").clear().type(firstName);
    cy.get("#last").clear().type(lastName);

    // Select "Legal" from the Name Type dropdown
    cy.get("#name-type").select("L");

    // Click the "Add name" button
    cy.contains("button", "Add name").click();
  }
}

export default new NameEntryPage();
