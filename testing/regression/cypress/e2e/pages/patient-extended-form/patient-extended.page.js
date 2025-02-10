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

  verifyRequiredFieldError() {
    // Click the "Add name" button
    cy.contains("button", "Add name").click({ force: true });

    // Verify that the error message is displayed
    cy.get(".alert-message_title__UqoEz")
      .should("be.visible")
      .and("contain.text", "Please fix the following errors:");
  }

  enterValidPhoneNumber() {
    const phoneNumber = faker.phone.number("###-###-####");
    const extension = "123456";

    // Select "Phone" from the Type dropdown
    cy.get("#phone-type").select("PH");

    // Select "Mobile contact" from the Use dropdown
    cy.get("#phone-use").select("MC");

    // Enter the generated phone number
    cy.get("#phoneNumber").clear().type(phoneNumber);

    // Enter the extension
    cy.get("#extension").clear().type(extension);

    // Click the "Add phone & email" button
    cy.contains("button", "Add phone & email").click();
  }

}

export default new NameEntryPage();
