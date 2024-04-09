class EditMortalityModule {
  get section() {
    return ('div[data-testid=grid] .common-card');
  }

  date() {
    const currentDate = new Date()
    const month = (currentDate.getMonth() + 1).toString().padStart(2, '0')
    const day = currentDate.getDate().toString().padStart(2, '0')
    const year = currentDate.getFullYear()
    const formattedDate = `${month}${day}${year}`
    cy.get(this.section).eq(1)
      .find('.usa-input.usa-date-picker__external-input').eq(0)
      .clear();
    cy.get(this.section).eq(1)
      .find('.usa-input.usa-date-picker__external-input').eq(0)
      .wait(500)
      .type(formattedDate)
    return this;
  }

  mortality(option) {
    cy.get(this.section).eq(1)
      .find('select[data-testid=dropdown]').select(option)
    return this;
  }

  save() {
    cy.get(this.section).eq(1)
      .contains('button', 'Save')
      .scrollIntoView()
      .click()
  }

  isMortalityAdded(mortality) {
    cy.get(this.section).eq(1)
      .contains(mortality).should("be.visible");
  }
}
export default new EditMortalityModule();
