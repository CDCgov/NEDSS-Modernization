import addCommentModule from "./addComment.module";

class AddNameModule {
  date() {
    addCommentModule.date();
    return this;
  }

  type() {
    cy.get(".usa-modal-wrapper.is-visible select[data-testid=dropdown]")
      .first()
      .select('Alias name');
    return this;
  }

  first(fName) {
    cy.get(".usa-modal-wrapper.is-visible #first").scrollIntoView().type(fName);
    return this;
  }

  last() {
    cy.get(".usa-modal-wrapper.is-visible #last").type(this.getRandomName());
    return this;
  }

  comment() {
    addCommentModule.comment();
    return this;
  }

  add() {
    addCommentModule.add();
  }

  nameIsAdded(fName) {
    cy.contains(fName).should("be.visible");
  }

  isSuccessfullyAdded() {
    cy.get(".usa-alert__body").should("contain", "Added name");
  }

  getRandomName() {
    const names = ["Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry"];
    const randomIndex = Math.floor(Math.random() * names.length);
    return names[randomIndex];
  }
}
export default new AddNameModule();
