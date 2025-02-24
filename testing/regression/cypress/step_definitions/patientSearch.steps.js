import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import PatientSearchPage from "cypress/e2e/pages/patientSearchPage.page";

When("I select Exact Date for Date of Birth", () => {
  PatientSearchPage.selectExactDate();
});

And("I enter a specific date of birth", () => {
  PatientSearchPage.enterExactDateOfBirth();
});

When("I click the Search button", () => {
  PatientSearchPage.clickSearch();
});

Then("the system should return patients whose Date of Birth exactly matches the entered date", () => {
  PatientSearchPage.verifySearchResults();
});
