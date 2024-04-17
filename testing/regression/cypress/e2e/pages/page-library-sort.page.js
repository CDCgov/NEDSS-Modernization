class PageLibrarySortPage {
    navigateToLibrary () {
        cy.visit('/page-builder/pages')
    }

    userViewsPageLibrary() {
        cy.get("#pageLibrary")
    }

    pageNameArrowClick() {
        cy.get(".usa-button.usa-button--unstyled").first().click()
    }

    pageNameListedInDescendingOrder() {
        this.checkOrder("Page name", "descending")
    }

    pageNameListedInAscendingOrder() {
        this.checkOrder("Page name", "ascending")
    }
    checkOrder(columnName, sortType, dataType) {
        const list = [];
        const index = this.getColumnIndexByName(columnName);
        this.openInvestigationTable.find("tbody tr").each(($tr) => {
            list.push($tr.find("td").eq(index).text());
        });
        let isOrdered = false;
        if (sortType === 'ascending') {
            isOrdered = this.isAscending(list, dataType);
        } else if (sortType === 'descending') {
            isOrdered = this.isDescending(list, dataType);
        }
        expect(isOrdered).to.be.true;
    }

    getColumnIndexByName(columnName) {
        if (columnName === "Page name") {
            return 0;
        } else if (columnName === "Condition") {
            return 1;
        } else if (columnName === "Document type") {
            return 0;
        }
    }

    get table() {
        return "table[data-testid=table]";
    }
    get openInvestigationTable() {
        return cy.get(this.table).eq(0);
    }

    isAscending(list, dataType) {
        return list.every((value, index, array) => {
            if (index === 0) {
                return true; // Skip the first element
            }
            if (dataType === "date") {
                return new Date(value) - new Date(array[index - 1]);
            }
            return value >= array[index - 1];
        });
    }

    isDescending(list, dataType) {
        return list.every((value, index, array) => {
            if (index === 0) {
                return true; // Skip the first element
            }
            if (dataType === "date") {
                return new Date(array[index - 1]) - new Date(value);
            }
            return value <= array[index - 1];
        });
    }
}

export const pageLibrarySortPage = new PageLibrarySortPage()