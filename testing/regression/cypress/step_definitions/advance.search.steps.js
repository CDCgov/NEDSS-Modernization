import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import addPatientPage from "cypress/e2e/pages/add-patient.page";
import addInvestigationPage from "cypress/e2e/pages/events/add-investigation.page";
import eventsTabPage from "cypress/e2e/pages/eventsTab.page";
import patientProfilePage from "cypress/e2e/pages/patientProfile.page";
import { searchPage } from "cypress/e2e/pages/search.page";
import searchResultsPage from "cypress/e2e/pages/search.results.page";

Then(
  "the user has searched for a patient by {string} as {string}",
  (string, string2) => {    
    searchPage.selectId();
    searchPage.enterIdType(string);
    searchPage.enterId(string2);
    searchPage.search();
  }
);

Then("I set patient id profile ENV", () => {
  patientProfilePage.setPatientProfileENVID();
});

Then(
  "the user has enters for a patient by {string} as {string}",
  (string, string2) => {
    searchPage.selectId();
    searchPage.enterIdType(string);
    searchPage.enterId(string2);
  }
);

Then(
  "the user has searched for a patient by name {string} as {string}",
  (string, string2) => {
    searchPage.selectName();
    searchPage.enterLastName(string);
    searchPage.enterFirstName(string2);
    searchPage.search();
  }
);

When("the user clicks on a patient's profile", () => {
  searchResultsPage.openProfile();
});

Then("the patient's profile is displayed", () => {
  patientProfilePage.isDisplayed();
});

Then("I should see search button disabled", () => {
  cy.get('button').contains("Search").should('be.disabled')
});

Then("I should see {string}", (string) => {
  cy.get('main').contains(string).should("be.visible");
});

Then("I should see error message {string}", (string) => {
  cy.get('span[data-testid="errorMessage"].usa-error-message').contains(string).should("be.visible")

});

When("the User close the error message", () => {
  searchPage.closeErrorMsg();
});

Given("the user is on a Patient Profile page for {string}", (string) => {
  searchPage.selectId();
  searchPage.enterIdType("Person number");
  searchPage.enterId(string);
  searchPage.search();
  searchResultsPage.openProfile();
  patientProfilePage.isDisplayed();
});

When("the user clicks on the Print button", () => {
  patientProfilePage.print();
});

Then("the user is directed to the printable patient demographics page", () => {
  patientProfilePage.isDemographicPageDisplayed();
});

When("the user clicks on the Delete Patient button", () => {
  patientProfilePage.delete();
});

Then("confirms the deletion of the patient", () => {
  patientProfilePage.confirmDelete();
});

Then("the user is directed to the Home screen", () => {
  cy.url().should("include", "/search/patients");
});

Then("cancels the deletion of the patient", () => {
  patientProfilePage.cancelDelete();
});

Then("the user remains on the same profile page", () => {
  cy.url().should("include", "/patient-profile/");
});

When("the user clicks on the Back to top button", () => {
  patientProfilePage.backToTop();
});

Then("the user is taken to the top of the page", () => {
  cy.window().its("scrollY").should("be.equal", 0);
});

Then("user clicks on a patient's profile {string} tab", (string) => {
  patientProfilePage.clickOnTab(string);
  cy.wait(5000);
});

Then("I should see the following elements", (dataTable) => {
  dataTable.rawTable.forEach((row) => {
    const label = row[0];
    const regex = new RegExp(label, "i");
    cy.contains(regex).should("be.visible");
  });
});

When("user clicks on the {string} button", (string) => {
  patientProfilePage.clickOnButton(string);
  cy.wait(1000);
});

Then("user adds the comments", () => {
  patientProfilePage.addComments();
});

Then("comment is displayed on the patient profile page", () => {
  patientProfilePage.isCommentSuccessfullyAdded();
});

Then("user adds the name {string}", (string) => {
  patientProfilePage.addName(string);
});

Then(
  "Name {string} information is displayed on the patient profile page",
  (string) => {
    patientProfilePage.isNameAdded(string);
  }
);

Then("user adds the new address as {string}", (string) => {
  patientProfilePage.addAddress(string);
});

Then("Address information is updated", () => {
  patientProfilePage.isAddressAdded();
});

Then("user adds the new phone as {string}", (string) => {
  patientProfilePage.addPhoneNumber(string);
});

When("phone number is updated", () => {
  patientProfilePage.isPhoneNumberAdded();
});

Then("user adds the new Add identification as {string}", (string) => {
  patientProfilePage.addIdentification(string);
});

Then("identification information is added successfully", () => {
  patientProfilePage.isIdAdded();
});

Then("user adds the new detail race as {string}", function (string) {
  patientProfilePage.addRace(string);
});

Then("race information as {string} is displayed", (string) => {
  patientProfilePage.isRaceAdded(string);
});

When("User clicks on the edit button under {string}", (string) => {
  patientProfilePage.clickOnEdit(string);
});

Then(
  "user adds the general patient info with the mother's name as {string}",
  (string) => {
    patientProfilePage.editGeneralInfo(string);
  }
);

Then("general information as {string} is displayed", (string) => {
  patientProfilePage.isGeneralInformationAdded(string);
});

Then("user adds ethnicity as {string}", (string) => {
  patientProfilePage.editEthnicity(string);
});

Then("ethnicity information {string} is displayed", (string) => {
  patientProfilePage.isEthnicityAdded(string);
});

Then("user adds sex and birth for current sex as {string}", (string) => {
  patientProfilePage.editSexBirth(string);
});

Then(
  "sex and birth information is added and the current sex is shown as {string}",
  (string) => {
    patientProfilePage.isCurrentSexAdded(string);
  }
);

Then("user edits mortality with the deceased option as {string}", (string) => {
  patientProfilePage.editMortality(string);
});

Then(
  "mortality information is saved with the deceased option as {string}",
  (string) => {
    patientProfilePage.isMortalityAdded(string);
  }
);

Given(
  "the user navigate to a new patient profile page", () => {
    const clientid = Cypress.env("patientId");
    cy.visit(`/patient-profile/${clientid}`);
  }
);

Given(
  "the user navigate to the patient profile page for {string}",
  (string) => {    
    cy.visit(`/patient/${string}/summary`);
  }
);

Then(
  "I should see the following columns for {string} table",
  (string, dataTable) => {
    eventsTabPage.validateTableColumns(string, dataTable);
  }
);

When("the user has selected multiple investigations", () => {
  eventsTabPage.selectMultipleInvestigations();
});

Then("Add a new investigation", () => {
  addInvestigationPage.add();
});

Then("nagivate to add new patient page", () => {
  searchResultsPage.naviageToAddNewPatient();
});

Given("create a new patient profile", () => {
  cy.visit('/patient/add');
  addPatientPage.addSimplePatient()
  addPatientPage.clickViewPatientProfile();
});
