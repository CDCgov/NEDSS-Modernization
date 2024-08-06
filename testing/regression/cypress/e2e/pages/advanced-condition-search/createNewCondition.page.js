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
        const newStr = Math.random().toString(36).substring(2, 8);
        cy.get('[data-testid="conditionName"]').type('Condition' + newStr);
        cy.get('[data-testid="textInput"]').eq(2).type(newStr);
        cy.get('[data-testid="dropdown"]').eq(3).select('ARBO');
    }

    clickCreateAndAddToPageBtn() {
        cy.wait(5000)
        cy.get('.cancel-btn').eq(0).click({ force: true });
    }

    verifyConditionFieldHasValue() {
        cy.contains("Condition");
    }

    completeRemainingFormFields() {
        const num = Math.random().toString(36).substring(2, 8);
        cy.get('#name').type('New Test Page ' + num);
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
        cy.wait(5000)
        cy.contains('New Test Page');
    }
}

export const createNewConditionPage = new CreateNewConditionPage()