class ClassicManageValueSetsPage {

  navigateToValueSetsLibrary() {
    cy.contains('System Management').click()
    cy.get('#systemAdmin5 a').eq(0).click()
    cy.contains('Manage Value Sets').click()
  }

  clickAddNewBtn() {
    cy.get('input[type="submit"][value="Add New"]').eq(0).click()
  }

  fillTheDetailsValueSetFields(type) {
    const newName = this.newName()
    if (type === 'LOCAL') {
        cy.get('input[name="ValStF_textbox"]').type('Locally Defined')
    } else if (type === 'PHIN') {
        cy.get('input[name="ValStF_textbox"]').type('PHIN Standard')
    }
    cy.get('#ValSCF').type(`Set code ${newName}`)
    cy.get('#ValSNF').type(`Set Name ${newName}`)
    cy.get('#ValSDF').type('Description for new value set')
  }

  clickSubmitBtnValueSetForm() {
    cy.get('#submitA').eq(0).click()
  }

  clickFilterBtnValueSetLibrary() {
    cy.get('.multiSelect').eq(2).click()
  }

  enterFilterTextValueSetLibrary() {
    cy.get('#SearchText2').eq(0).type('NBS')
  }

  clickFilterOkBtnValueSetLibrary() {
    cy.get('#b1SearchText2').eq(0).click()
  }

  clickValueSetInValueSetList() {
    cy.get('#parent tbody tr td a').eq(0).click()
  }

  clickCollapseSubsectionsInValueSetList() {
    cy.wait(2000)
    cy.contains('Collapse Subsections').click()
  }

  clickExpandSubsectionsInValueSetList() {
    cy.wait(2000)
    cy.contains('Expand Subsections').click()
  }

  clickAddNewBtnInValueSetConceptSection() {
    cy.window().then((win) => {
        cy.stub(win, 'open').callsFake((url) => {
            cy.visit(url)
        })
    })

    cy.get('#submitCr').click()
  }

  fillTheDetailsNewValueSetConcept() {
    const newName = this.newName()
    cy.get('#ValLC').type(`local code ${newName}`)
    cy.get('#ValLDN').type(`display name ${newName}`)
    cy.get('#ValSDN').type(`short name ${newName}`)
//    cy.get('#ValSCF').type(`concept code ${newName}`)
//    cy.get('#ValSNF').type(`name ${newName}`)
//    cy.get('#ValPSNF').type(`pref name ${newName}`)
    cy.get('#CodeSNF_DD').select(1)
  }

  clickSubmitBtnInValueSetConceptForm() {
    cy.get('#submitA').click()
    cy.wait(2000)
    cy.visit('/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true')
    this.clickValueSetInValueSetList()
  }

  clickMakeInactiveInValueSet() {
    cy.get('body').then(($body) => {
        if($body.find('input[type="button"][value="Make Inactive"]').length > 0) {
            cy.get('input[type="button"][value="Make Inactive"]').eq(0).click()
        }
    })
  }

  newName() {
    return Math.random().toString(36).substring(2, 8);
  }
}
export default new ClassicManageValueSetsPage();

