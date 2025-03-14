class ClassicPatientSearchPage {

  navigateToClassicPatientSearchPane() {
    cy.get('#homePageAdvancedSearch').click()
    cy.contains('Go to classic search').click()
  }

  enterLastNameInClassicSearchPatientPage(text) {
    cy.get('#DEM102').type(text)
  }

  clickSearchBtnInClassicPatientSearchPane() {
    cy.get('input[type="button"][name="Submit"][value="Submit"]').eq(0).click()
  }

  selectPatientToEdit() {
    this.navigateToClassicPatientSearchPane()
    this.enterLastNameInClassicSearchPatientPage('Simpson')
    this.clickSearchBtnInClassicPatientSearchPane()
    cy.get('table#searchResultsTable tbody tr td a').eq(0).click()
  }

  viewPatientDetails() {
    cy.contains('Patient profile').eq(0)
  }

  goToNewPatientExtendedForm() {
    cy.get('#homePageAdvancedSearch').click()
    cy.get('input[id="name.last"]').type("Simpson")
    cy.get('button[type="button"]').contains("Search").eq(0).click()
    cy.wait(2000)
    cy.get('button[data-testid="button"]').eq(0).click()
    cy.contains("button", "Add new patient").click()
    cy.contains("button", "Add extended data").click()
  }

  fillExtendedFormDetails() {
    cy.get('select[id="birthAndSex.current"]').select("M")
    cy.get('select[id="general.maritalStatus"]').select("M")
  }

  clickSaveExtendedForm() {
    cy.contains("button", "Save").click()
  }

  VerifySuccessfulFormSubmit() {
    cy.contains("Success")
  }

  verifyConfirmationMessage() {
    cy.contains("You have successfully added a new patient")
  }

  fillInformationAsOfDateField(date) {
    const field = cy.get('input[id="administrative.asOf"]')
    field.invoke('val', date || "01/20/2024").trigger('change')
    field.click()
    cy.contains("Administrative").eq(0).click()
  }

  errorMessageInformationAsOfField() {
    cy.contains("The Information as of date should occur before or within the current year")
  }

  fillCommentsField(type) {
    let commentText;
    if(type === 'empty') {
      return commentText = ''
    }

    if(type === 'invalid') {
        commentText = 'A'.repeat(2001)
    } else if(type === '2000') {
        commentText = 'A'.repeat(2000)
    } else {
        commentText = 'Comments about the new patient'
    }
    cy.get('textarea[id="administrative.comment"]').type(commentText)
    cy.contains("Administrative").eq(0).click()
  }

  errorMessageCommentsField() {
    cy.contains("The Comments only allows 2000 characters")
  }

  clearCommentsField() {
    cy.get('textarea[id="administrative.comment"]').clear().blur()
  }

  fillExtendedAddressFormDetails(type) {
    if(type === 'invalid') {
        cy.contains('button', 'Add address').click()
        return
    }

    cy.get('#address-type').select('H')
    cy.get('#address-use').select('BDL')
    cy.contains('button', 'Add address').click()
  }

  errorMessageAddressField() {
    cy.contains('The Type is required')
    cy.contains('The Use is required')
  }

  fillDropdownFields() {
    cy.get('#races-category-race').select('1002-5')
    cy.get('footer button').contains('Add race').eq(0).click()
    cy.get('select[id="ethnicity.ethnicGroup"]').select('2135-2')
    cy.get('select[id="birthAndSex.current"]').select('M')
    cy.get('select[id="birthAndSex.sex"]').select('M')
  }

  doNotFillDropdownValues() {
    cy.get('#races-category-race').select('')
    cy.get('footer button').contains('Add race').eq(0).click()
  }

  errorMessageDropdownField() {
    cy.contains('The Race is required.')
  }

  errorMessageSectionField(inputId, sectionId) {
    cy.get("section#phoneEmails").contains(sectionId);
  }

  clickAddIdentificationButton() {
    cy.get("section#identifications button").contains("Add identification").click();
  }

  clickAddPhoneButton() {
    cy.get("section#phoneEmails button").contains("Add phone & email").click();
  }

  selectPhoneType() {
    cy.get("section#phoneEmails select#phone-type").select("Phone");
  }

  selectPhoneUse() {
    cy.get("section#phoneEmails select#phone-use").select("Home");
  }

  typeValidPhoneNumber() {
    cy.get("section#phoneEmails input#phoneNumber").type("8888888888");
  }

  errorSectionField(sectionId, text) {
    cy.get(`section#${sectionId}`).contains(text);
  }

  selectSectionField(sectionId, inputId, text) {
    cy.get(`section#${sectionId} select#${inputId}`).select(text);
  }

  typeInputSectionField(sectionId, inputId, text) {
    cy.get(`section#${sectionId} input#${inputId}`).type(text);
  }

  addNewPatient() {
    this.goToNewPatientExtendedForm()
    this.fillExtendedFormDetails()
    this.clickSaveExtendedForm()
  }

  successModalDisplayed() {
    cy.contains('You have successfully added a new patient')
  }

  clickAddLabReportBtn() {
    cy.contains('button', 'Add lab report').eq(0).click()
  }

  redirectAddLabReportForm() {
    cy.contains('Lab Report')
  }

  enterReportingFacility() {
    cy.get('#NBS_LAB365Text').type('test')
    cy.get('#NBS_LAB365CodeLookupButton').eq(0).click()
  }

  selectProgramArea() {
    cy.get('#INV108').eq(0).select('ARBO', {force: true})
  }

  selectJurisdiction() {
    cy.get('#INV107').eq(0).select('130006', {force: true})
  }

  selectResultedTestAndFillDetails() {
    cy.get('#NBS_LAB220').eq(0).select('T-50130', {force: true})
    cy.get('#NBS_LAB280').eq(0).select('ABN', {force: true})
    cy.get('#AddButtonToggleRESULTED_TEST_CONTAINER input').eq(0).click()
  }

  clickAddReportFormSubmitBtn() {
    cy.get('#SubmitBottom').eq(0).click()
  }

  verifyPatientProfileWithAddedLabReport() {
    cy.contains('Patient profile')
  }

  clickAddInvestigationBtn() {
   cy.contains('button', 'Add investigation').eq(0).click()
  }

  redirectAddInvestigationForm() {
    cy.contains('Select Condition')
  }

  selectCondition() {
    cy.get('#ccd').eq(0).select('11065', {force: true})
    cy.get('#Submit').eq(0).click()
    const field = cy.get('#NBS104')
    field.invoke('val', "01/20/2024").trigger('change')
    field.click()
    cy.contains("Comments").eq(0).click()
  }

   selectJurisdictionInInvestigationForm() {
     cy.get('#INV107').eq(0).select('130006', {force: true})
   }

   clickAddInvestigationFormSubmitBtn() {
     cy.get('#SubmitBottom').eq(0).click()
   }

   verifyInvestigationAddedSuccessfully() {
     cy.contains('Investigation has been successfully saved in the system')
   }

   navigateToModernizedPatientSearchPane() {
     cy.get('#homePageAdvancedSearch').click()
   }

   startsWithForLastName() {
    cy.get('select[id="name.lastOperator"]').select('startsWith')
   }

   enterLastNameInModernizedSearchPatientPage(text) {
     cy.get('input[id="name.last"]').type(text)
   }

   clickSearchBtnInModernizedPatientSearchPane() {
     cy.contains('button', 'Search').eq(0).click()
   }

   patientListEnteredValue() {
    cy.contains('Simpson')
   }

   containsForLastName() {
     cy.get('select[id="name.lastOperator"]').select('contains')
   }

   soundsLikeForLastName() {
     cy.get('select[id="name.lastOperator"]').select('soundsLike')
   }

   selectExactDateForDateOfBirth() {
    cy.get('input[type="radio"][name="dateOperation"]').should('have.value', 'equals')
   }

   enterDateOfBirthInModernizedSearchPatientPage() {
     cy.get('#bornOn-exact-date-month').type('07')
     cy.get('#bornOn-exact-date-day').type('24')
     cy.get('#bornOn-exact-date-year').type('1940')
   }

   patientListEnteredValueForDateOfBirth() {
    cy.contains('Martin')
   }

   equalForLastName() {
     cy.get('select[id="name.lastOperator"]').select('equals')
   }

  notEqualForLastName() {
     cy.get('select[id="name.lastOperator"]').select('not')
   }

  selectSearchNameType(idName, type) {
    cy.get(`select[id="${idName}"]`).select(type);
  }

  findSearchResultByDataItemType(text, id) {
    cy.get(`div[data-item-type="${id}"]`).contains(text)
  }

  fillIdInputWithText(id, text) {
    cy.get(`input[id="${id}"]`).type(text);
  }

}

export default new ClassicPatientSearchPage();
