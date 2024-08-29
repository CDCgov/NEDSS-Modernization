import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import addPatientPage from "cypress/e2e/pages/add-patient.page";

Then("I create a new patient", () => {
    addPatientPage.addPatient()
});

Then ("I clear the Information as of Date field", () => {
    addPatientPage.clearInformationAsOfDate()
});

Then("I create a new patient without enter field and get error message", () => {
    addPatientPage.addPatientBlank()
});

Then("I create a new patient to enter information as of Date field only", () => {
    addPatientPage.addPatientSingleDetail()
});

Then("I create a new patient to enter 1 year later date in information as of Date field", () => {
    addPatientPage.addPatientSingleDeteNextYear()
});

Then("I create a new patient to enter numeric and special character in name field", () => {
    addPatientPage.addPatientNameSpecial()
});

Then("I create a new patient to enter other information with Information as of Date", () => {
    addPatientPage.addPatientOtherInformation()
});

Then("I create a new patient to enter address with Information as of Date", () => {
    addPatientPage.addPatientAddress()
});

Then("I should see profile of new patient", () => {
    addPatientPage.viewPatientProfile()
});

Then("I should see on Patient Profile {string}", (patientIDString) => {
    addPatientPage.viewPatientID(patientIDString)
  });

Then("the user add another patient profile", () => {
    addPatientPage.addAnotherPatient()
});

Then("I create a new patient with select Ethnicity option", () => {
    addPatientPage.addPatientselectEthnicity()
});

Then("I create a new patient to select one optios of Race of the patient", () => {
    addPatientPage.addPatientselectRace()
});

Then("I create a new patient to select three optios of Race of the patient", () => {
    addPatientPage.addPatientSelectTwoRace()
});

Then("I create a new patient to select ID type option of the Identification", () => {
    addPatientPage.addPatientId_Identificatione()
});

Then("I create a new patient to select Assigning authority option of the Identification", () => {
    addPatientPage.addPatientAssigningAuthority_Identificatione()
});

Then("I create a new patient to select Add another ID in the Identification", () => {
    addPatientPage.addPatientAddAnotherID()
});

Then("delete the patient profile", () => {
    addPatientPage.addPatientAndDelete()
});