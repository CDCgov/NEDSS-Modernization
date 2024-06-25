class SearchAndFilterPage {
    viewSearchField() {
        cy.get('#condition-search');
    }

    enterSearchField(text) {
        cy.get('#condition-search').type(text);
    }

    clickSearchBtn() {
        cy.get('#searchButton').click();
    }

    verifyRowsDisplaying(rows) {
        cy.get('tbody tr').its('length').should('be.gte', rows);
    }

    clickCancel() {
        cy.get('[data-testid="advancedConditionSearchCancelBtn"]').click();
    }

    verifyPageReturned() {
        cy.contains('Create new page');
    }
}

export const searchAndFilterPage = new SearchAndFilterPage()