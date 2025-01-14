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

}

export default new ClassicPatientSearchPage();
