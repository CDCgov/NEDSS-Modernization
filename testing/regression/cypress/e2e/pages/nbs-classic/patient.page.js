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

  fillInformationAsOfDateField() {
    cy.get('input[id="administrative.asOf"]').type("01/20/2025")
  }

}

export default new ClassicPatientSearchPage();
