class ClassicPlaceSearchPage {

  navigateToClassicPlaceSearchPane() {
    cy.contains('Data Entry').eq(0).click()
    cy.contains('Place').eq(0).click()
  }

  enterNameInClassicSearchPlacePage(text) {
    cy.get('input[id="city"]').type(text)
  }

  clickSearchBtnInClassicPlaceSearchPane() {
    cy.get('input[name="Submit"][value="Submit"]').eq(0).click()
  }

  navigateToAddPlace() {
    this.navigateToClassicPlaceSearchPane()
    this.enterNameInClassicSearchPlacePage('a')
    this.clickSearchBtnInClassicPlaceSearchPane()
  }

  clickSubmitBtnOnPlace() {
    cy.get('input[name="Submit"][id="Submit"]').eq(0).click()
  }

}

export default new ClassicPlaceSearchPage();
