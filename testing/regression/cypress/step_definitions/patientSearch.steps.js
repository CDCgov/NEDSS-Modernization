import { When, Then } from "@badeball/cypress-cucumber-preprocessor";
import PatientSearchPage from "cypress/e2e/pages/search.page";

// Scenario 1: Searching by Exact Date
When("I select Exact Date for Date of Birth", () => {
  PatientSearchPage.selectExactDate();
});

When("I enter a specific date of birth", () => {
  const birthDate = { month: "01", day: "15", year: "1985" };
  PatientSearchPage.enterExactDateOfBirth(birthDate.month, birthDate.day, birthDate.year);
});

Then("the system should return patients whose Date of Birth exactly matches the entered date", () => {
  PatientSearchPage.clickSearch();
  PatientSearchPage.verifySearchResults();
});

// Scenario 2: Searching by Date Range
When("I select Date Range for Date of Birth", () => {
  PatientSearchPage.selectDateRange();
});

When("I enter a start and end date for the range", () => {
  const startDate = { month: "01", day: "01", year: "1980" };
  const endDate = { month: "12", day: "31", year: "1990" };
  PatientSearchPage.enterDateRange(startDate.month, startDate.day, startDate.year, endDate.month, endDate.day, endDate.year);
});

Then("the system should return patients whose Date of Birth falls within the entered date range", () => {
  PatientSearchPage.clickSearch();
  PatientSearchPage.verifySearchResults();
});

// Scenario 3: Searching with an Invalid Exact Date
When("I enter a Date of Birth that does not exist", () => {
  const invalidDate = { month: "13", day: "32", year: "3000" }; // Invalid date
  PatientSearchPage.enterExactDateOfBirth(invalidDate.month, invalidDate.day, invalidDate.year);
});

Then("the system should display an error message", () => {
  PatientSearchPage.clickSearch();
  PatientSearchPage.verifyErrorMessage();
});
