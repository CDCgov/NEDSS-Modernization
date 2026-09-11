class AddComment {
    date() {
        const currentDate = new Date();
        const month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
        const day = currentDate.getDate().toString().padStart(2, '0');
        const year = currentDate.getFullYear();
        const formattedDate = `${month}${day}${year}`;
        cy.get('.usa-modal-wrapper.is-visible .usa-date-picker__external-input').clear();
        cy.get('.usa-modal-wrapper.is-visible .usa-date-picker__external-input').type(formattedDate);
        return this;
    }

    comment() {
        return cy.enterInput('.usa-modal-wrapper.is-visible #comment', 'sample comments');
    }

    add() {
        cy.get('.usa-modal-wrapper.is-visible button').contains('Save');
        cy.get('.usa-modal-wrapper.is-visible button').click();
        cy.wait(1000);
    }

    isPopupClosed() {
        cy.get('.usa-modal-wrapper.is-visible').should('have.css', 'visibility', 'hidden');
    }

    isSuccessfullyAdded() {
        cy.get('.usa-alert__body').should('contain', 'Updated Comment');
    }
}
export default new AddComment();
