class ClassicManagePagesPage {
    
  navigateToPageLibrary() {
    cy.contains('System Management').click()    
    cy.contains('Expand Subsections').click()
    cy.contains('Manage Pages').click()
  }

  clickAddNewBtn() {
    cy.get('body').then(($body) => {
        if($body.find('input[type="button"][value="Add New"]').length > 0) {
            cy.get('input[type="button"][value="Add New"]').eq(0).click()
        }
    })
  }

  clickAddNewTabImg() {
    cy.get('body').then(($body) => {
        if($body.find('img[title="Add New Tab"]').length > 0) {
            cy.get('img[title="Add New Tab"]').eq(0).click()
        }
    })
  }  

  clickAddSection() {
    cy.get(".addSection").eq(0).click();
  }

  clickAddSubSection() {
    cy.get(".addSubSection").eq(0).click();
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

  clickPageDetailsBtn () {
    cy.get('input[type="button"][name="Page Details"]').eq(0).click()
  }

  clickClonePageBtn () {
    cy.get('input[type="button"][name="Clone Page"]').eq(0).click()
  }

  addRelatedConditions () {
    cy.get('[name="conditionCodes"]').select(1)
    cy.get('input[type="button"][value="Add >"]').eq(0).click()
  }

  clickSubmitBtn2 () {
    cy.get('input[type="button"][name="Submit"]').eq(0).click()
  }

  clickEditBtn () {
    cy.get('input[type="button"][name="Edit"]').eq(0).click()
  }

  enterDescription () {
    cy.get('textarea[name="selection.waTemplateDT.descTxt"]')
      .eq(0)
      .clear()
      .type('description edited')
  }

  clickPageRulesBtn () {
    cy.get('input[type="button"][name="Page Rules"]').eq(0).click()
  }

  selectFunction () {
    cy.get('#function').eq(0).select(1, { force: true })
    cy.get('input[name="function_textbox"]').eq(0).click()
  }

  selectSource () {
    cy.get('#sourceDC').eq(0).select(1, { force: true })
    cy.get('input[name="sourceDC_textbox"]').eq(0).click()
  }

  selectLogic () {
    cy.get('#logicDC').eq(0).select(1, { force: true })
    cy.get('input[name="logicDC_textbox"]').eq(0).click()
  }

  selectTarget () {
    cy.get('#targetDC').select(2)
  }

  clickEditBtnInViewPage() {
    cy.get('input[type="button"][name="Edit Page"]').eq(0).click()
  }

  clickAddElements() {
    cy.window().then((win) => {
      cy.stub(win, 'open').callsFake((url) => {
        win.location.href = url; // Redirect to the popup's URL in the same tab
      });
    });
    cy.get('.importQuestionLink').eq(0).click()
  }

  selectStaticElement() {
    cy.get('input[type="radio"][value="staticElt"]').check()
  }

  selectStaticElementType() {
    cy.get('#eltType').select(1)
  }

  clickSubmitBtnInElementPage() {
    cy.get('input[type="button"][name="SubmitForm"]').eq(0).click()
  }

  clickCloseBtnInAddElementPage() {
    cy.get('input[type="button"][value="Close"]').eq(0).click()
  }

}
export default new ClassicManagePagesPage();
