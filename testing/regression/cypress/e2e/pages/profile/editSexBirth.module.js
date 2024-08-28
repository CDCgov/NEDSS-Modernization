
class EditSexBirthModule {
  get section() {
    return ('div[data-testid=grid] .common-card');
  }

  date() {
    const currentDate = new Date()
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0')
    const day = currentDate.getDate().toString().padStart(2, '0')
    const year = currentDate.getFullYear()
    const formattedDate = `${month}/${day}/${year}`
    cy.get(this.section).eq(3)
      .find('.usa-input.usa-date-picker__external-input').eq(0)
      .type(formattedDate)
    return this;
  }

  currentSex(sex) {
    cy.get(this.section).eq(3)
      .find('select[data-testid="dropdown"]').eq(0).select(sex)
    return this;
  }

  save() {
    cy.get(this.section).eq(3)
      .contains('button', 'Save')
      .scrollIntoView()
      .click()
  }

  isCurrentSexAdded(currentSex) {
    cy.get(this.section).eq(3)
      .contains(currentSex).should("be.visible");
  }
}
export default new EditSexBirthModule();
