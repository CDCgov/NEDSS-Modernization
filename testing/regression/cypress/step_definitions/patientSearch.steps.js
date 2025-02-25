import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import PatientSearchPage from "cypress/e2e/pages/patientSearchPage.page";

When("I select Exact Date for Date of Birth", () => {
  PatientSearchPage.selectExactDate();
});

When("I enter a specific date of birth", () => {
  PatientSearchPage.enterExactDateOfBirth();
});

When("I search for patients", () => {
    PatientSearchPage.clickSearch();
  });

Then("the system should return patients whose Date of Birth exactly matches the entered date", () => {
  PatientSearchPage.verifySearchResults();
});

/*When("I select Date Range for Date of Birth", () => {
    PatientSearchPage.selectDateRange();
  });*/

  When("I select Date Range for Date of Birth", () => {
    PatientSearchPage.selectDateRange();
  });
  
When("I enter a start and end date for the range", () => {
    PatientSearchPage.enterDateRange();
  });
  
Then("the system should return patients whose Date of Birth falls within the entered date range", () => {
    PatientSearchPage.verifySearchResults();
  });

  When("I enter a Date of Birth that does not exist", () => {
    PatientSearchPage.enterInvalidDateOfBirth();
  });
  
  Then("the system should display an error message", () => {
    PatientSearchPage.verifyErrorMessage();
  });
