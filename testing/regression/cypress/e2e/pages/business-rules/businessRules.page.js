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

    checkFunctionColumnPopulated() {
        cy.get('.business-rules-library')
            .invoke('text')
            .then((text) => {
                if (!text.includes("Disable")) {
                    cy.contains("Disable");
                } else if (!text.includes("Enable")) {
                   cy.contains("Enable");
               } else if (!text.includes("Hide")) {
                    cy.contains("Hide");
               } else if (!text.includes("Unhide")) {
                    cy.contains("Unhide");
               }
            })
    }

    checkTargetColumnPopulated() {
        cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr) => {
            expect($tr.find("td").eq(4).text()).to.exist;
        });
    }

    enterUniqueNameInSearchField() {
        cy.get('#business-rules-search').type('date of birth');
    }

    clickBusinessRulesSearchBtn() {
        cy.get('[data-testid="businessRulesSearchBtn"]').click();
    }

    checkBusinessRulesListDisplayed() {
        cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr) => {
            expect($tr.find("td").eq(0).text()).to.exist;
        });
    }

    enterUniqueIdInSearchField() {
        cy.get('#business-rules-search').type('dem115');
    }
}

export const businessRulesPage = new BusinessRulesPage()