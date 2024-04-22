class PaginationPage {
    checkForDefaultRows () {
        cy.get('#range-toggle').should('have.value', '10')
    }

    get table() {
        return "table[data-testid=table]";
    }
}

export const pageLibraryPaginationPage = new PaginationPage()