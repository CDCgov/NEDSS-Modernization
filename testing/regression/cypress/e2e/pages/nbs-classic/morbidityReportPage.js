class MorbidityReportPage {
  morbidityReportLink = "font.boldEightBlack";
  patientTab = "font.boldNineYellow";
  reportInformationTab = "font.boldNineYellow";
  conditionField = 'input[name="conditionCd_textbox"]';
  jurisdictionField = 'select[fieldlabel="Jurisdiction"]';
  morbidityDateField = "#morbidityReport\\.theObservationDT\\.activityToTime_s";
  treatmentDateField = '#treatment_s\\[i\\]\\.treatmentVO_s\\[0\\]\\.treatmentAdministeredDT_s\\[0\\]\\.effectiveFromTime_s';
  treatmentDropdown = 'select[fieldlabel="Treatment"][validate="required"]';
  addTreatmentButton = 'input#BatchEntryAddButtonTreatment[value="Add Treatment"]';
  facilityProviderField =
    'input[name="entity-codeLookupText-Org-ReportingOrganizationUID"]';
  codeLookupButton = 'input[value="Code Lookup"]';
  pregnantDropdown = 'select[fieldlabel="Is Patient Pregnant"][validate="pregnantMale"]';
  submitButton = "#Submit";
  submitAndCreateInvestigationButton = 'input[type="image"][name="Submit and Create Investigation"]';
  cancelButton = "#Cancel";
  editButton = '#Edit';
  printButton = "#Print";
  deleteButton = "#Delete";
  transferOwnershipButton = "input[id='Transfer Ownership']";
  createInvestigationButton = "input[id='Create Investigation']";
  associateInvestigationButton = "input[id='Associate Investigation']";
  markAsReviewedButton = "input[id='Mark as Reviewed']";
  conditionCode = "#conditionCd";

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
    cy.get(this.jurisdictionField).select(jurisdiction, {force: true});
  }

  storeCondition() {
    cy.get(this.conditionCode)
      .invoke('text')
      .then(condition => {
        Cypress.env('morbidityCondition', condition);
        cy.log('Stored morbidity condition: ' + condition);
      });
  }

  checkFirstInvestigationWithCondition(condition) {
    // Find the first row with the matching condition
    cy.get('table.dtTable tbody tr')
      .filter((index, row) => {
        // Find the condition column (4th column, index 3)
        const conditionText = Cypress.$(row).find('td:nth-child(4)').text().trim();
        return conditionText === condition;
      })
      .first()
      .scrollIntoView()
      .should('be.visible')
      .within(() => {
        // Find and check the checkbox in the first column
        cy.get('td:first-child input[type="checkbox"]')
          .should('exist')
          .and('be.visible')
          .check({ force: true });
      });
  }

  clearJurisdiction() {
    cy.get(this.jurisdictionField).select("", { force: true });
  }

  enterMorbidityDate(date) {
    const [month, day, year] = date.split("/");
    const formattedDate = `${month.padStart(2, "0")}${day.padStart(
      2,
      "0"
    )}${year}`;
    cy.get(this.morbidityDateField).type(formattedDate);
  }

  enterTreatmentDate(date) {
    const [month, day, year] = date.split("/");
    const formattedDate = `${month.padStart(2, "0")}${day.padStart(
      2,
      "0"
    )}${year}`;
    cy.get(this.treatmentDateField).type(formattedDate);
  }

  enterFacilityProvider(value) {
    cy.get(this.facilityProviderField).type(value);
  }

  selectPregnant(value) {
    cy.get(this.pregnantDropdown).select(value, { force: true });
  }

  selectTreatment(value) {
    cy.get(this.treatmentDropdown).select(value, { force: true });
  }

  clickAddTreatment() {
    cy.get(this.addTreatmentButton).click();
  }

  clickCodeLookup() {
    cy.get(this.codeLookupButton).first().click();
  }

  clickSubmit() {
    cy.get(this.submitButton).click();
  }

  clickCancel() {
    cy.get(this.cancelButton).click();
  }

  clickEdit() {
    cy.get(this.editButton).click();
  }

  clickMarkAsReviewed() {
    cy.get(this.markAsReviewedButton).first().click();
  }

  clickAndVerifyPrint() {
    let printURL = '/nbs/LoadViewObservationMorb1.do?ContextAction=PrintPage';
    cy.window().then(win => {
      cy.stub(win, 'open').as('open')
    });
    cy.get(this.printButton).click();
    cy.get("@open").should("have.been.calledWith", printURL);
    cy.request(printURL, { method: 'GET' }).then((response) => {
      const contentType = response.headers['content-type'];
      expect(contentType).to.include('application/pdf');
    });
  }

  clickTransferOwnership() {
    cy.get(this.transferOwnershipButton).first().click();
  }

  clickCreateInvestigation() {
    cy.get(this.createInvestigationButton).first().click();
  }

  clickAssociateInvestigation() {
    cy.get(this.associateInvestigationButton).first().click();
  }

  clickSubmitAndCreateInvestigation() {
    cy.get(this.submitAndCreateInvestigationButton).eq(0).click();
  }

  clickDelete() {
    cy.get(this.deleteButton).first().click();
  }

  confirmSubmission() {
    cy.on("window:confirm", () => true); // Automatically confirm the popup
    // small buffer
    cy.wait(500);
  }

  storeInvestigationIdFromAssociationMessage() {
    // Wait for the success message to appear
    cy.get('#error1', { timeout: 10000 })
      .should('be.visible')
      .invoke('text')
      .then(text => {
        // Extract the Investigation ID from parentheses
        // Example: "Morbidity Report successfully associated to investigation: Botulism, foodborne (CAS10001000GA01)"
        const match = text.match(/\(([^)]+)\)/);
        
        if (match && match[1]) {
          const investigationId = match[1].trim();
          Cypress.env('investigationId', investigationId);
          cy.log('Stored Investigation ID: ' + investigationId);
        } else {
          cy.log('No Investigation ID found in parentheses');
          throw new Error('Could not extract Investigation ID from: ' + text);
        }
      });
  }

  clickMarkAsReviewedAndHandlePopup() {
    // Wait for page to load
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    // Intercept the window.open call for the Mark as Reviewed popup
    cy.window().then(win => {
      cy.stub(win, 'open').callsFake((url) => {
        cy.log('Mark as Reviewed popup URL intercepted: ' + url);
        
        // Instead of opening a new window, navigate to the URL in the same window
        // This effectively "stubs" the popup and loads it on the same page
        cy.visit(url);
        win.markAsReviewed(); // Call the function to mark as reviewed
        
      });
    });
    
    // Click the Mark as Reviewed button
    cy.get('#Mark\\ as\\ Reviewed')
      .should('be.visible')
      .first()
      .click();
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
