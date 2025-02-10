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
}

export default new ClassicPatientSearchPage();
