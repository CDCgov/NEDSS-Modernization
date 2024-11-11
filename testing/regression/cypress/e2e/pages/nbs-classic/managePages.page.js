class ClassicManagePagesPage {

  navigateToPageLibrary() {
    cy.contains('System Management').click()
    cy.get('#systemAdmin5 a').eq(0).click()
    cy.contains('Manage Pages').click()
  }

  clickAddNewBtn() {
    cy.get('input[type="button"][value="Add New"]').eq(0).click()
  }

  selectPageType(pageType) {
    const pageTypes = {
      Investigation: 3,
      Interview: 2,
      ["Contact Record"]: 1
    }
    cy.get('#busObjType').eq(0).select(pageTypes[pageType], { force: true })
    cy.get('input[name="busObjType_textbox"]').eq(0).click()
  }

  selectTemplate() {
    cy.get('#existingTemplate').eq(0).select(1, { force: true })
    cy.get('input[name="existingTemplate_textbox"]').eq(0).click()
  }

  selectMappingGuide() {
    cy.get('#mappingGuide').eq(0).select(1, { force: true })
    cy.get('input[name="mappingGuide_textbox"]').eq(0).click()
  }

  enterPageName() {
    const newName = this.newName()
    cy.get('#uniquePageName').type(`Page name ${newName}`)
  }

  clickSubmitBtn() {
    cy.get('#submitButton').eq(0).click()
  }

  newName() {
    return Math.random().toString(36).substring(2, 8);
  }

  clickViewIcon() {
    cy.get('#parent tbody tr td a').eq(0).click()
  }

  checkDisplayed(text) {
    cy.contains(text)
  }
}
export default new ClassicManagePagesPage();

