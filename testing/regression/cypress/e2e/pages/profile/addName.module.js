import addCommentModule from './addComment.module';

class AddNameModule {
    date() {
        addCommentModule.date();
        return this;
    }

    type() {
        cy.get('.usa-modal-wrapper.is-visible select[data-testid=dropdown]').first().select('Alias name');
        return this;
    }

    first(fName) {
        return cy.enterInput('.usa-modal-wrapper.is-visible #first', fName);
    }

    last() {
        return cy.enterInput('.usa-modal-wrapper.is-visible #last', this.getRandomName());
    }

    comment() {
        addCommentModule.comment();
        return this;
    }

    add() {
        addCommentModule.add();
    }

    nameIsAdded(fName) {
        cy.contains(fName).should('be.visible');
    }

    isSuccessfullyAdded() {
        cy.get('.usa-alert__body').should('contain', 'Added name');
    }

    getRandomName() {
        const names = ['Bob', 'Charlie', 'David', 'Eve', 'Frank', 'Grace', 'Henry'];
        const randomIndex = Math.floor(Math.random() * names.length);
        return names[randomIndex];
    }
}
export default new AddNameModule();
