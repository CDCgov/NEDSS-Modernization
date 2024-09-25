class ClassicManageQuestionsPage {

  navigateToQuestionsLibrary() {
    cy.contains('System Management').click()
    cy.get('#systemAdmin5 a').eq(0).click()
    cy.contains('Manage Questions').click()
  }

  clickAddNewBtn() {
    cy.get('input[type="button"][value="Add New"]').eq(0).click()
  }

  fillTheDetailsLocalQuestion(type) {
    const newName = this.newName()
    if (type === 'LOCAL') {
        cy.get('input[name="questionType_textbox"]').type('Locally Defined')
    } else if (type === 'PHIN') {
        cy.get('input[name="questionType_textbox"]').type('PHIN Standard')
        cy.get('#questionIdentifier').type(newName)
    }
    cy.get('#questionNm').type(`new question ${newName}`)
    cy.get('input[name="subGroup_textbox"]').type('Administrative Information')
    cy.get('#desc').type('Description for new question')
    cy.get('input[name="dataType_textbox"]').type('Text')
    cy.get('#questionLabel').type(`new label ${newName}`)
    cy.get('#fieldLenTxt').type('10')
    cy.get('#questionLabel').type(`new label ${newName}`)
    cy.get('#questionToolTip').type('tooltip message')
    cy.get('#defaultLabelReport').type(`new label report ${newName}`)
    cy.get('#rdbcolumnNm').type(`Name ${newName}`)
  }

  clickSubmitBtnLocalQuestion() {
    cy.get('input[type="button"][value="Submit"]').eq(0).click()
  }

  clickQuestionInQuestionList() {
    cy.get('#parent tbody tr td a').eq(1).click()
  }

  newName() {
    return Math.random().toString(36).substring(2, 8);
  }
}
export default new ClassicManageQuestionsPage();

