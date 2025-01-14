class PaginationPage {
    shouldHaveDefaultRows() {
        cy.contains(10)
    }
}

export const questionLibraryPaginationPage = new PaginationPage()