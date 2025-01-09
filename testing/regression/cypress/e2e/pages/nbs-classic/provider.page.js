class ClassicProviderSearchPage {

  navigateToClassicProviderSearchPane() {
    cy.contains('Data Entry').eq(0).click()
    cy.contains('Provider').eq(0).click()
  }

  enterNameInClassicSearchProviderPage(text) {
    cy.get('input[id="providerSearch.lastName"]').type(text)
  }

  clickSearchBtnInClassicProviderSearchPane() {
    cy.get('input[name="Submit"][id="Submit"]').eq(0).click()
  }

  navigateToAddProvider() {
    this.navigateToClassicProviderSearchPane()
    this.enterNameInClassicSearchProviderPage('test')
    this.clickSearchBtnInClassicProviderSearchPane()
  }

  clickAddButtonOnAddProvider() {
    cy.get('#Add').eq(0).click()
  }

  enterQuickCode() {
    const newName = Math.random().toString(36).substring(2, 8)
    cy.get('input[name="quickCodeIdDT.rootExtensionTxt"]').eq(0).type(newName)
  }

  clickSubmitBtnOnProvider() {
    cy.get('input[name="Submit"][id="Submit"]').eq(0).click()
  }

}

export default new ClassicProviderSearchPage();
