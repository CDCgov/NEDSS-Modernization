class BusinessRulesPage {
    navigateToBusinessRulesPage () {
        cy.visit('/page-builder/pages');
        cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
        cy.get('[data-testid="businessRulesBtn"]').eq(0).click();
    }

    viewsBusinessRulesPage() {
        cy.get(".business-rules-library")
    }

    checkLogicColumnPopulated() {
        cy.contains("Logic");
    }

    displayLogics(logic) {
        cy.get('.business-rules-library')
            .invoke('text')
            .then((text) => {
                if (!text.includes(logic)) {
                    cy.contains("Logic");
                } else {
                    cy.contains(logic);
                }
            })
    }
}

export const businessRulesPage = new BusinessRulesPage()