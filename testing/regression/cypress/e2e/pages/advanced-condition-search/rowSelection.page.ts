class RowSelectionPage {
    navigateToCreateNewPage() {
        cy.visit('/page-builder/pages/add');
    }

    openSearchModal() {
        cy.wait(1000);
        cy.get('#eventType').eq(0).select('INV');
        cy.wait(1000);
        cy.get('[data-testid="advancedConditionSearchBtn"]').eq(0).click();
    }

    verifySearchModalOpen() {
        cy.contains('Search and add condition(s)');
    }

    verifyRowsByDefault() {
        cy.get('#range-toggle').should('have.value', '10');
    }

    updateRowsSelection(rowsSelection: string) {
        cy.get('#range-toggle').select(rowsSelection);
    }

    verifyRowsSelectionUpdated(rowsSelection: string) {
        cy.get('#range-toggle').should('have.value', rowsSelection);
    }

    closeSearchModal() {
        cy.get('[data-testid="closeSearchModalBtn"]').click();
    }

    clickAdvancedSearchBtn() {
        cy.get('[data-testid="advancedConditionSearchBtn"]').eq(0).click();
    }

    verifyRowsDisplaying(rows: number) {
        cy.get('tbody tr').its('length').should('be.gte', rows);
    }
}

export const rowSelectionPage = new RowSelectionPage();
