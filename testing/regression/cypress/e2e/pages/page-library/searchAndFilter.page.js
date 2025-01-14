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

    getColumnValueByName(columnName) {
        if (columnName === "Page name") {
            return "name";
        } else if (columnName === "Event type") {
            return "event-type";
        } else if (columnName === "Related Condition(s)") {
            return "conditions";
        } else if (columnName === "Status") {
            return "status";
        } else if (columnName === "Last updated") {
            return "lastUpdate";
        } else if (columnName === "Last updated by") {
            return "lastUpdatedBy";
        }
    }

    showFilterSection() {
        cy.get('#filter-button').click();
        cy.get('#add-filter').click();
    }

    selectColumn(columnName) {
        cy.get('#select-column').select(this.getColumnValueByName(columnName), { force: true });
    }

    selectOperator(operatorValue) {
        cy.get('#select-operator').select(operatorValue);
    }

    enterValue(value) {
        cy.get('#value').type(value);
    }

    clickDone() {
        cy.get('#done-button').click();
    }

    clickApply() {
        cy.get('#apply-button').click();
    }

    clickCancel() {
        cy.get('#cancel-button').click();
    }

    enterTextInMultiInputValue(value) {
        cy.get('.multi-select__input').type(value);
        cy.get('.multi-select__option--is-focused').click();
    }

    showingContainedResults(text, columnName) {
        this.checkMatchedSearchResult(text, columnName)
    }

    canSeeFilterOverlay() {
        cy.get('#add-filter').eq(0)
    }

    checkValueFiledIsHidden() {
        cy.get('#values').should('not.exist');
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