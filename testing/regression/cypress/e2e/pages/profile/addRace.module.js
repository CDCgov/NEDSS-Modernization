import addCommentModule from './addComment.module';

class AddRaceModule {
    date() {
        addCommentModule.date();
        return this;
    }

    comment() {
        addCommentModule.comment();
        return this;
    }

    add() {
        addCommentModule.add();
    }

    race() {
        cy.get('.usa-modal-wrapper.is-visible select').select('Asian');
        return this;
    }

    detailRace(detRace) {
        cy.get('.usa-modal-wrapper.is-visible .multi-select__control').type(`${detRace}{enter}`);
        cy.get('.usa-modal-wrapper.is-visible .multi-select__control').click();
        return this;
    }

    isRaceAdded(detRace) {
        cy.contains(detRace).should('be.visible');
    }
}
export default new AddRaceModule();
