class AddComment {
  date() {
    const currentDate = new Date();
    const month = (currentDate.getMonth() + 1).toString().padStart(2, "0");
    const day = currentDate.getDate().toString().padStart(2, "0");
    const year = currentDate.getFullYear();
    const formattedDate = `${month}${day}${year}`;
    cy.get(
      ".usa-modal-wrapper.is-visible .usa-date-picker__external-input"
    ).clear();
    cy.get(
      ".usa-modal-wrapper.is-visible .usa-date-picker__external-input"
    ).type(formattedDate);
    return this;
  }

  comment() {
    cy.get(".usa-modal-wrapper.is-visible #comment")
      .scrollIntoView()
      .type("sample comments");
    return this;
  }

  add() {
    cy.get(".usa-modal-wrapper.is-visible button")
      .contains("Save")
      .click()
      .wait(1000);
  }

  isPopupClosed() {
    cy.get(".usa-modal-wrapper.is-visible").should(
      "have.css",
      "visibility",
      "hidden"
    );
  }

  isSuccessfullyAdded() {
    cy.get(".usa-alert__body").should("contain", "Updated Comment");
  }
}
export default new AddComment();
