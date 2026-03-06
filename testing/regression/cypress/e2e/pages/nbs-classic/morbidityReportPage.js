class MorbidityReportPage {
  morbidityReportLink = "font.boldEightBlack";
  patientTab = "font.boldNineYellow";
  reportInformationTab = "font.boldNineYellow";
  conditionField = 'input[name="conditionCd_textbox"]';
  jurisdictionField =
    'input[name="morbidityReport.theObservationDT.jurisdictionCd_textbox"]';
  morbidityDateField = "#morbidityReport\\.theObservationDT\\.activityToTime_s";
  facilityProviderField =
    'input[name="entity-codeLookupText-Org-ReportingOrganizationUID"]';
  codeLookupButton = 'input[value="Code Lookup"]';
  submitButton = "#Submit";

  // Patient entry fields
  patientLastNameField = "#entity\\.lastNm";
  patientFirstNameField = "#entity\\.firstNm";
  patientSearchResults = "#searchResultsTable tbody tr";
  patientSearchResultsTable = "#searchResultsTable";

  // Validation error selectors
  errorMessages =
    'font.boldTenRed, font[color="red"], .error, .errorMessage, [class*="error"]';

  clickMorbidityReport() {
    cy.get(this.morbidityReportLink).contains("Morbidity Report").click();
  }

  clickPatientTab() {
    cy.get(this.patientTab).contains("Patient").click();
  }

  clickReportInformationTab() {
    cy.get(this.reportInformationTab).contains("Report Information").click();
  }

  selectCondition(condition) {
    cy.get(this.conditionField).type(condition).type("{enter}");
  }

  selectJurisdiction(jurisdiction) {
    cy.get(this.jurisdictionField).type(jurisdiction).type("{enter}");
  }

  clearJurisdiction() {
    cy.get(this.jurisdictionField).clear();
  }

  enterMorbidityDate(date) {
    const [month, day, year] = date.split("/");
    const formattedDate = `${month.padStart(2, "0")}${day.padStart(
      2,
      "0"
    )}${year}`;
    cy.get(this.morbidityDateField).type(formattedDate);
  }

  enterFacilityProvider(value) {
    cy.get(this.facilityProviderField).type(value);
  }

  clickCodeLookup() {
    cy.get(this.codeLookupButton).first().click();
  }

  clickSubmit() {
    cy.get(this.submitButton).click();
  }

  confirmSubmission() {
    cy.on("window:confirm", () => true); // Automatically confirm the popup
  }

  // Patient entry methods
  enterPatientBothNames(firstName, lastName) {
    cy.get(this.patientFirstNameField).type(firstName);
    cy.get(this.patientLastNameField).type(lastName);
  }

  // Validation error checking
  verifyValidationErrors() {
    // Check for visible error messages on the Report Information tab
    cy.get(this.reportInformationTab).contains("Report Information").click();
    cy.wait(500);
    // Check that error text contains required field messaging
    cy.contains(/required|must be|cannot be empty|not valid/i, {
      timeout: 5000,
    }).should("be.visible");
  }

  verifyFieldValidationError(fieldName) {
    // Look for error messages specifically mentioning the field name
    cy.contains(
      new RegExp(`${fieldName}.*required|required.*${fieldName}`, "i")
    ).should("be.visible");
    // Also check for error styling near the field - but only visible ones
    cy.get(this.errorMessages)
      .filter(":visible")
      .should("have.length.greaterThan", 0);
  }

  // Form state verification
  verifyPatientFirstNameContains(value) {
    cy.get(this.patientFirstNameField).should("have.value", value);
  }

  // Successful submission verification
  verifySuccessfulSubmission() {
    // After successful submission, the page redirects either to:
    // 1. ObservationMorbDataEntry1 (to enter another report) OR
    // 2. MyTaskList1 (home page/task list)
    // Both indicate successful submission
    cy.wait(2000);

    // Verify the URL changed to one of the expected destinations
    cy.url().should("satisfy", (url) => {
      return (
        url.includes("ObservationMorbDataEntry1") ||
        url.includes("MyTaskList1") ||
        url.includes("Home")
      );
    });

    // Simply verify the page loaded successfully by checking for visible content
    // If there was an error, the page wouldn't redirect or would show an error page
    cy.get("body").should("be.visible");
  }
}
export const morbidityReportPage = new MorbidityReportPage();
