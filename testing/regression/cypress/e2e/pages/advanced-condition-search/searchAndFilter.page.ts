class SearchAndFilterPage {
    viewSearchField() {
        cy.get('#condition-search');
    }

    enterSearchField(text: string) {
        cy.get('#condition-search').type(text);
    }

    clickSearchBtn() {
        cy.get('#searchButton').click();
    }

    verifyRowsDisplaying(rows: number) {
        cy.get('tbody tr').its('length').should('be.gte', rows);
    }

    clickCancel() {
        cy.get('[data-testid="advancedConditionSearchCancelBtn"]').click();
    }

    verifyPageReturned() {
        cy.contains('Create new page');
    }
}

export const searchAndFilterPage = new SearchAndFilterPage();
