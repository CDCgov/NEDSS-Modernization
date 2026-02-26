class ClassicManageConditionsPage {

  navigateToConditionsLibrary() {
    cy.contains('System Management').click()

    // Expand the "Page Management" subsection
    cy.get('table[id="systemAdmin5"]').find('a[class="toggleIconHref"]').eq(0).click();
    cy.contains('Manage Conditions').click()
  }

  clickAddNewBtn() {
    cy.get('#submitCr').click()
  }

  fillTheDetailsCondition() {
    const newName = this.newName()
    cy.get('#cCodeFld').type(`code ${newName}`)
    cy.get('#condFld').type(`Name ${newName}`)
    cy.get('input[name="pAreaFld_textbox"]').type('ARBO')
  }

  clickSubmitBtnConditionInConditionLibrary() {
    cy.get('#submitB').eq(0).click()
  }

  clickConditionInConditionList() {
    cy.get('table#parent tbody tr td a').eq(1).click()
  }

  newName() {
    return Math.random().toString(36).substring(2, 8);
  }
}
export default new ClassicManageConditionsPage();
