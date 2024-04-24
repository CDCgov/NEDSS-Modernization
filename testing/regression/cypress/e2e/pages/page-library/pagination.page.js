class PaginationPage {
    checkForDefaultRows () {
        cy.get('#range-toggle').should('have.value', '10')
    }

    get table() {
        return "table[data-testid=table]";
    }

    selectNumberOfRows (numberOfRows) {
        cy.get('#range-toggle').select(numberOfRows).should('have.value', numberOfRows);
    }

    checkDisplayingNumberOfRowsSubsequently(rowsPerPage, onlyOnNext, pageNumber) {
        this.totalRowsCountFromDOM().then((totalRowsCount) => {
            const totalPages = Math.ceil(totalRowsCount / rowsPerPage);
            const lastPageRowsCount = totalRowsCount % rowsPerPage || rowsPerPage;
            const arrayMapFun = (_, i) => i === totalPages - 1 ? lastPageRowsCount : rowsPerPage;
            const rowsCountPerPage = Array.from({length: totalPages}, arrayMapFun);

            if (onlyOnNext) {
                this.openInvestigationTable.find("tbody tr").should('have.length', rowsCountPerPage[pageNumber-1]);
            } else {
                rowsCountPerPage.forEach((countPerPage, i) => {
                    if (i !== 0) {
                        this.clickNextPage();
                    }
                    this.openInvestigationTable.find("tbody tr").should('have.length', countPerPage);
                });
            }
        })
    }

    clickNextPage() {
        cy.get('.usa-pagination__next-page').click();
    }

    get openInvestigationTable() {
        cy.wait(1500);
        return cy.get(this.table).eq(0);
    }

    totalRowsCountFromDOM(){
        return cy.get('#totalRowCount').invoke('text')
            .then(text => {
                return parseInt(text, 10)
            });
    }

    navigateToCreatePage () {
        cy.visit('/page-builder/pages/add')
    }
}

export const pageLibraryPaginationPage = new PaginationPage()