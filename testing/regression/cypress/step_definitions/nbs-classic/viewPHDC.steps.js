import { viewPHDCPage } from '@pages/nbs-classic/viewPHDC.page';
import { Given, Then } from "@badeball/cypress-cucumber-preprocessor";


Given("I navigate to the DRSA Queue", () => {
  viewPHDCPage.navigateToDRSAQueue();
})

Given("I import PHDC documents", () => {
  viewPHDCPage.runPHCRImporter();
})

Given("I click on a patient's \"Case Report\"", () => {
  viewPHDCPage.openPatientCaseReport("Minnie");
})

Then("I can view the PHDC for {string} {string}", (patientFirstName, patientLastName) => {
  viewPHDCPage.viewPHDC(`${patientFirstName} ${patientLastName}`);
})

Then("I can open the eCR in the eICR viewer", () => {
  viewPHDCPage.openECRViewer();
})

Given("I search for a patient with a PHDC", () => {
  viewPHDCPage.getPatientWithPHDC({patientLastName: "undisclosed", patientFirstName: "undisclosed"});
})

Then("The \"View eICR Document\" button is not visible", () => {
  viewPHDCPage.eCRViewButtonNotVisible();
})
