class CreateNewConditionPage {
    navigateToCreateNewPage () {
        cy.visit('/page-builder/pages/add')
    }

    selectEventTypeInvestigation() {
        cy.wait(1000);
        cy.get('#eventType').eq(0).select('INV');
        cy.wait(1000);
    }

    additionalFieldsDisplayed() {
        cy.contains("Create a new condition here");
    }

    clickCreateNewConditionBtn() {
        cy.get('[data-testid="createNewConditionHereBtn"]').click();
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

    verifyConditionFieldHasValue() {
        cy.contains("NewConditionName");
    }

    completeRemainingFormFields() {
        const num = Math.floor(Math.random() * 90000) + 10000;
        cy.get('#name').type('New Test Page' + num);
        cy.get('#templateId').find('option').eq(1).then((option) => {
            cy.get('#templateId').select(option.attr('value'))
            cy.get('#messageMappingGuide').find('option').eq(1).then((option) => {
                cy.get('#messageMappingGuide').select(option.attr('value'))
            });
        });
    }

    clickCreatePageButton() {
        cy.get('.createPage').eq(0).click()
    }

    navigateToClassicDesign () {
        cy.contains('New Test Page');
    }
}

export const createNewConditionPage = new CreateNewConditionPage()