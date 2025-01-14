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

  checkEditProviderPage() {
    cy.get('td[class="boldTwelveBlack"]').contains('Edit Provider')
  }

  navigateToAddProvider() {
    this.navigateToClassicProviderSearchPane()
    this.enterNameInClassicSearchProviderPage('test')
    this.clickSearchBtnInClassicProviderSearchPane()
  }

  navigateToEditProvider() {
    this.navigateToClassicProviderSearchPane()
    this.enterNameInClassicSearchProviderPage('test')
    this.clickSearchBtnInClassicProviderSearchPane()
    cy.get('a').contains("View").eq(0).click()
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

  clickEditBtnOnProvider() {
    cy.get('input[id="Edit"]').eq(0).click();
  }

  clickEditOverwrittenRadio() {
    cy.get('input[id="c"]').eq(0).click();
  }

  clickEditNewProviderRadio() {
    cy.get('input[id="n"]').eq(0).click();
  }

  clickEditProviderAddName() {
    cy.get('select[id="provider.nmPrefix"]').select("Brother", {force: true});
    cy.get('input[name="provider.nmPrefix_textbox"]').type("Brother", {force: true});
    cy.get('input[id="provider.firstNm"]').type("TestFirstName");
    cy.get('input[name="Submit"][id="Submit"]').eq(0).click()
  }

}

export default new ClassicProviderSearchPage();
