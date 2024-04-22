class SortPage {
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

    eventTypeArrowClick() {
        cy.get(".usa-button.usa-button--unstyled").eq(1).click()
    }

    eventTypeListedInDescendingOrder() {
        this.checkOrder("Event type", "descending")
    }

    eventTypeListedInAscendingOrder() {
        this.checkOrder("Event type", "ascending")
    }

    statusArrowClick() {
        cy.get(".usa-button.usa-button--unstyled").eq(2).click()
    }

    statusListedInDescendingOrder() {
        this.checkOrder("Status", "descending")
    }

    statusListedInAscendingOrder() {
        this.checkOrder("Status", "ascending")
    }
    lastUpdatedArrowClick() {
        cy.get(".usa-button.usa-button--unstyled").eq(3).click()
    }

    lastUpdatedListedInDescendingOrder() {
        this.checkOrder("Last updated", "descending", "date")
    }

    lastUpdatedListedInAscendingOrder() {
        this.checkOrder("Last updated", "ascending", "date")
    }
    lastUpdatedView() {
        cy.get(".usa-button.usa-button--unstyled").eq(3)
    }

    lastUpdatedDateFormat() {
        this.checkDateFormat("Last updated")
    }

    lastUpdatedByArrowClick() {
        cy.get(".usa-button.usa-button--unstyled").eq(4).click()
    }

    lastUpdatedByListedInDescendingOrder() {
        this.checkOrder("Last updated by", "descending", "date")
    }

    lastUpdatedByListedInAscendingOrder() {
        this.checkOrder("Last updated by", "ascending", "date")
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

    checkDateFormat(columnName) {
        const list = [];
        const index = this.getColumnIndexByName(columnName);
        this.openInvestigationTable.find("tbody tr").each(($tr) => {
            list.push($tr.find("td").eq(index).text());
        });
        let correctFormat = false;
        const check = () => {
            return list.every((value, index, array) => {
                if (index === 0) {
                    return true; // Skip the first element
                }
                return value.split("/").length === 3;
            });
        }
        correctFormat = check();
        expect(correctFormat).to.be.true;
    }
}

export const pageLibrarySortPage = new SortPage()