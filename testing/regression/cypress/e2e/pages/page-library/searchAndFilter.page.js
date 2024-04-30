class SearchAndFilterPage {

    enterTextInSearchField(searchKeyword) {
        cy.get('#page-search').type(searchKeyword)
    }

    clickOnSearchButton() {
        cy.get('#searchButton').click()
        cy.wait(1000)
    }

    checkMatchedSearchResult(searchedKeyword, columnName) {
        const list = [];
        const index = this.getColumnIndexByName(columnName);
        this.openInvestigationTable.find("tbody tr").each(($tr) => {
            list.push($tr.find("td").eq(index).text());
        });
        if (list.length) {
            expect(list[0].includes(searchedKeyword)).to.be.true;
        }
    }

    getColumnIndexByName(columnName) {
        if (columnName === "Page name") {
            return 0;
        } else if (columnName === "Event type") {
            return 1;
        } else if (columnName === "Status") {
            return 2;
        } else if (columnName === "Last updated") {
            return 3;
        } else if (columnName === "Last updated by") {
            return 4;
        }
    }

    get table() {
        return "table[data-testid=table]";
    }

    get openInvestigationTable() {
        cy.wait(1500);
        return cy.get(this.table).eq(0);
    }
}

export const searchAndFilterPage = new SearchAndFilterPage()