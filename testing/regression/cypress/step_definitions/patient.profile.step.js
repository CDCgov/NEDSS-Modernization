import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import PatientProfilePage from "cypress/e2e/pages/patientProfile.page";
import eventsTabPage from "cypress/e2e/pages/eventsTab.page";

Then("I explor the patient profile", () => {
    PatientProfilePage.navigatePatinet()
});

Then("I add invstigation of patient", () => {
    PatientProfilePage.addPatientInvestigations()
});

Then("I add invstigation - 2019 Novel Coronavirus of patient", () => {
    PatientProfilePage.addPatientInvestigations_NovelCoronavirus()
});

Then("I add invstigation - Acanthamoeba Disease of patient", () => {
    PatientProfilePage.addPatientInvestigations_AcanthamoebaDisease()
});

Then("I add invstigation - African Tick Bite Fever of patient", () => {
    PatientProfilePage.addPatientInvestigations_AfricanTickBite()
});

Then("I add invstigation - AIDS of patient", () => {
    PatientProfilePage.addPatientInvestigations_AIDS()
});

Then("I add invstigation - Amebiassis of patient", () => {
    PatientProfilePage.addPatientInvestigations_Amebiassis()
});

Then("I add invstigation - Anaplasma phagocytophilum of patient", () => {
    PatientProfilePage.addPatientInvestigations_AnaplasmaPhagocytophilum()
});

When("user clicks on the {string} button within the Events tab", (buttonValue) => {
    eventsTabPage.clickAddButton(buttonValue);
});

When("I check the current count of {string} in the Events tab", (reportType) => {
    eventsTabPage.getReportCount(reportType);
});

Then("the {string} count should increase by 1 in the Events tab", (reportType) => {
    eventsTabPage.verifyReportCountIncreased(reportType);
});

Then("the {string} count should remain the same in the Events tab", (reportType) => {
    eventsTabPage.verifyReportCountUnchanged(reportType);
});

When("I click on the first morbidity report link in the Events tab", () => {
    eventsTabPage.clickFirstMorbidityReportLink();
});

When("I count the number of treatments in the first morbidity report", () => {
    eventsTabPage.saveInitialTreatmentCount();
});

Then("the treatment count should increase by 1 in the first morbidity report", () => {
    eventsTabPage.verifyTreatmentCountIncreased();
});