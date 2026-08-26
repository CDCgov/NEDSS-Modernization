class AddConditionPage {
    viewCreateNewConditionBtn() {
        cy.contains('Create new condition').eq(0);
    }

    clickCreateNewConditionBtn() {
        cy.get('[data-testid="createNewConditionBtn"]').click();
    }

    createNewConditionWindowDisplayed(show = true) {
        if (show) {
            cy.contains('Create new condition');
        } else {
            cy.contains('Create new page');
        }
    }

    completeCreateNewConditionForm() {
        const num = Math.floor(Math.random() * 90000) + 10000;
        cy.findByLabelText("Condition Name").type('NewConditionName' + num);
        cy.findByLabelText("Condition Code").type(num);
        cy.findByLabelText("Program Area").select('ARBO');
    }

    clickCreateAndAddToPageBtn() {
        cy.contains('Create and add to page').click();
    }
}

export const addConditionPage = new AddConditionPage();
