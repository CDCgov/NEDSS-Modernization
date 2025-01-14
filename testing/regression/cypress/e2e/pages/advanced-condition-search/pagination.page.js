class PaginationPage {
    verifyPaginationRows(rowNumbers) {
        rowNumbers.forEach((pageNumber) => {
            cy.get('nav ul li').eq(pageNumber).click();
        });
    }

    clickNext() {
        cy.get('[data-testid="pagination-next"]').click();
    }

    verifyRowsDisplaying() {
        cy.get('tbody tr').its('length').should('be.gte', 10);
    }

    clickPrevious() {
        cy.get('[data-testid="pagination-previous"]').click();
    }
}

export const paginationPage = new PaginationPage()