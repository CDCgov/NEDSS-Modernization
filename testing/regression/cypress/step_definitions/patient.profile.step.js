import { Given, When, Then } from "@badeball/cypress-cucumber-preprocessor";
import PatientProfilePage from "cypress/e2e/pages/patientProfile.page";

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