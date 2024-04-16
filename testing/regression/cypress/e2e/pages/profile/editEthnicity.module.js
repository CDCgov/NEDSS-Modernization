
class EditEthnicityModule {
  get section() {
    return ('div[data-testid=grid] .common-card');
  }

  date() {
    const currentDate = new Date()
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0')
    const day = currentDate.getDate().toString().padStart(2, '0')
    const year = currentDate.getFullYear()
    const formattedDate = `${month}${day}${year}`
    cy.get(this.section).eq(2)
      .find('.usa-input.usa-date-picker__external-input')
      .clear();
    cy.get(this.section).eq(2)
      .find('.usa-input.usa-date-picker__external-input')
      .wait(500)
      .type(formattedDate)
    return this;
  }

  ethnicity(name) {
    cy.get(this.section).eq(2)
      .find('#ethnicGroup').select(name)
    return this;
  }

  save() {
    cy.get(this.section).eq(2)
      .contains('button', 'Save')
      .scrollIntoView()
      .click()
  }

  isEthnicityAdded(ethnicity) {
    cy.get(this.section).eq(2)
      .contains(ethnicity).should("be.visible");
  }
}
export default new EditEthnicityModule();
