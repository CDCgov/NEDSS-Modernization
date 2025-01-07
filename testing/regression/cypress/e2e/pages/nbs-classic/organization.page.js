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

  navigateToAddOrganisation() {
    this.navigateToClassicOrganizationSearchPane()
    this.enterNameInClassicSearchOrganizationPage('test')
    this.clickSearchBtnInClassicOrganizationSearchPane()
  }

  clickAddButtonOnAddOrganisation() {
    cy.get('#Add').eq(0).click()
  }

  enterQuickCode() {
    const newName = Math.random().toString(36).substring(2, 8)
    cy.get('input[name="quickCodeIdDT.rootExtensionTxt"]').eq(0).type(newName)
  }

  clickSubmitBtnOnOrganisation() {
    cy.get('input[name="Submit"][id="Submit"]').eq(0).click()
  }

}

export default new ClassicPatientSearchPage();
