import addCommentModule from './addComment.module';

class AddIdentificationModule {
    date() {
        addCommentModule.date();
        return this;
    }

    add() {
        addCommentModule.add();
    }

    type() {
        cy.get('.usa-modal-wrapper.is-visible select[data-testid=dropdown]').first().select('Person number');
        return this;
    }

    id(idNumber: string) {
        return cy.enterInput('input[data-testid="textInput"]', idNumber);
    }

    isSuccessfullyAdded() {
        cy.get('.usa-alert__body').should('contain', 'Added Identification');
    }

    isIdAdded(idNumber: string) {
        cy.contains(idNumber).should('be.visible');
    }
}
export default new AddIdentificationModule();
