class AddConditionPage {
    viewCreateNewConditionBtn() {
        cy.contains('Create new condition').eq(0);
    }

    clickCreateNewConditionBtn() {
        cy.get('[data-testid="createNewConditionBtn"]').click();
    }

    createNewConditionWindowDisplayed(show = true) {
        if(show) {
            cy.contains('Create new condition');
        } else {
            cy.contains('Create new page');
        }
    }

    completeCreateNewConditionForm() {
        const num = Math.floor(Math.random() * 90000) + 10000;
        cy.get('[data-testid="conditionName"]').type('NewConditionName' + num);
        cy.get('[data-testid="textInput"]').eq(2).type(num);
        cy.get('[data-testid="dropdown"]').eq(3).select('ARBO');
    }

    clickCreateAndAddToPageBtn() {
        cy.contains('Create and add to page').click();
    }
}

export const addConditionPage = new AddConditionPage()