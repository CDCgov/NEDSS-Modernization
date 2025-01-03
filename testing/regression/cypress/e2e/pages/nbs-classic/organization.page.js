class ClassicPatientSearchPage {

  navigateToClassicOrganizationSearchPane() {
    cy.contains('Data Entry').eq(0).click()
    cy.contains('Organization').eq(0).click()
  }

  enterNameInClassicSearchOrganizationPage(text) {
    cy.get('input[id="organizationSearch.nmTxt"]').type(text)
  }

  clickSearchBtnInClassicOrganizationSearchPane() {
    cy.get('input[name="Submit"][id="Submit"]').eq(0).click()
  }

  viewOrganizationDetails() {
    cy.contains('View').eq(0).click()
  }

}

export default new ClassicPatientSearchPage();
