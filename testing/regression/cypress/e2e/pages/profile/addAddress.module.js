import addCommentModule from "./addComment.module";
import {enterInput} from "../../../helpers/form.helper";

class AddAddressModule {
  date() {
    addCommentModule.date();
    return this;
  }

  type() {
    cy.get(".usa-modal-wrapper.is-visible select[data-testid=dropdown]")
      .first()
      .select("Dormitory", {force: true});
    return this;
  }

  use() {
    cy.get(".usa-modal-wrapper.is-visible select[data-testid=dropdown]")
      .eq(1)
      .select("Home");
    return this;
  }

  street1(address1) {
    return enterInput('.usa-modal-wrapper.is-visible #address1', address1);
  }

  city() {
    return enterInput('.usa-modal-wrapper.is-visible #city', 'houston');
  }

  state() {
    cy.get(".usa-modal-wrapper.is-visible select")
      .eq(2)
      .select("Texas");
    return this;
  }

  comment() {
    addCommentModule.comment();
    return this;
  }

  add() {
    addCommentModule.add();
  }

  isPopupClosed() {
    addCommentModule.isPopupClosed();
  }

  isSuccessfullyAdded() {
    cy.get(".usa-alert__body").should("contain", "Added address");
  }
}
export default new AddAddressModule();
