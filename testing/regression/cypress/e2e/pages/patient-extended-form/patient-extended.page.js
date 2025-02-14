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

  selectPatientDeceasedYes() {
    // Select "Yes" from the mortality status dropdown
    cy.get("#mortality\\.deceased").select("Y");
  }

  completeMortalityFields() {
    // Select the current date for Date of Death
     // Format current date as MM/DD/YYYY
     const today = new Date();
     const formattedDate = `${String(today.getMonth() + 1).padStart(2, "0")}/${String(today.getDate()).padStart(2, "0")}/${today.getFullYear()}`;
 
     // Enter the formatted date
     cy.get("#mortality\\.deceasedOn").clear().type(formattedDate);

    // Enter a random city for Death City
    const randomCity = faker.location.city();
    cy.get("#mortality\\.city").clear().type(randomCity);

    // Select a random state from the Death State dropdown
    cy.get("#mortality\\.state")
      .find("option:not(:first)") // Exclude the default "- Select -" option
      .then(($options) => {
        const randomIndex = Math.floor(Math.random() * $options.length);
        const randomValue = $options[randomIndex].value;
        cy.get("#mortality\\.state").select(randomValue);
      });

    // Select "United States" from the Death Country dropdown
    cy.get("#mortality\\.country").select("840");

  }

}

export default new NameEntryPage();
