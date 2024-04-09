import addCommentModule from "./addComment.module";

class AddPhoneModule {
  date() {
    addCommentModule.date();
    return this;
  }

  type() {
    cy.get(".usa-modal-wrapper.is-visible select[data-testid=dropdown]")
      .first()
      .select("Phone");
    return this;
  }

  use() {
    cy.get(".usa-modal-wrapper.is-visible select[data-testid=dropdown]")
      .eq(1)
      .select("Home");
    return this;
  }

  comment() {
    addCommentModule.comment();
    return this;
  }

  add() {
    addCommentModule.add();
  }

  countryCode() {
    cy.get(".usa-modal-wrapper.is-visible #countryCode")
      .scrollIntoView()
      .type("971");
    return this;
  }

  phoneNumber(phone) {
    cy.get(".usa-modal-wrapper.is-visible #number")
      .scrollIntoView()
      .type(phone);
    return this;
  }

  email() {
    cy.get(".usa-modal-wrapper.is-visible #email").type(
      "testemail@eqsandbox.com"
    );
    return this;
  }

  isSuccessfullyAdded() {
    cy.get(".usa-alert__body").should("contain", "Added Phone & Email");
  }
}
export default new AddPhoneModule();
