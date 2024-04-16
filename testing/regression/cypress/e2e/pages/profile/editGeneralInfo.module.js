
class EditGeneralInfoModule {
  get section() {
    return ('div[data-testid=grid] .common-card');
  }

  date() {
    const currentDate = new Date()
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0')
    const day = currentDate.getDate().toString().padStart(2, '0')
    const year = currentDate.getFullYear()
    const formattedDate = `${month}/${day}/${year}`
    cy.get(this.section)
      .find('.usa-input.usa-date-picker__external-input')
      .type(formattedDate)
    return this;
  }

  motherName(name) {
    cy.get(this.section)
      .find('#maternalMaidenName').type(name)
    return this;
  }

  save() {
    cy.get(this.section).contains('button', 'Save')
      .scrollIntoView()
      .click()
  }

  isGeneralInformationAdded(motherName) {
    cy.get(this.section).contains(motherName).should("be.visible");
  }
}
export default new EditGeneralInfoModule();
