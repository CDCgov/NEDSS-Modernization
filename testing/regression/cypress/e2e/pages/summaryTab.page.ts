class SummaryTabPage {
    get table() {
        return 'table[data-testid=table]';
    }

    get openInvestigationTable() {
        return cy.get(this.table).eq(0);
    }

    get documentTable() {
        return cy.get(this.table).eq(1);
    }

    get returnToSummaryLink() {
        return cy.get('.returnToPageLink>a');
    }
    openFirstInvestigation() {
        this.openInvestigationTable.find('a').eq(0).click();
    }

    returnToSummary() {
        this.returnToSummaryLink.click();
        cy.wait(1000);
    }

    openLinkInDocumentTable(linkText: string) {
        this.documentTable.contains('a', linkText).click();
    }

    sort(columnName: string, sortBy: string) {
        this.openInvestigationTable.find('thead').contains(columnName).find('button').click();
        if (sortBy === 'descending') {
            this.openInvestigationTable.find('thead').contains(columnName).find('button').click();
        }
    }

    checkIfSorted(columnName: string, sortedBy: string) {
        const list = [];
        const index = this.getColumnIndexByName(columnName);
        this.openInvestigationTable.find('tbody tr').each(($tr) => {
            list.push($tr.find('td').eq(index).text());
        });
        let flag;
        if (sortedBy === 'ascending') {
            flag = this.isAscending(list);
        } else {
            flag = this.isDescending(list);
        }

        expect(flag).to.be.true;
    }

    documentTablesort(columnName: string, sortBy: string) {
        this.documentTable.find('thead').contains(columnName).find('button').click({ force: true });
        if (sortBy === 'descending') {
            this.documentTable.find('thead').contains(columnName).find('button').click({ force: true });
        }
    }

    documentTableCheckIfSorted(columnName: string, sortedBy: string) {
        const list = [];
        const index = this.getColumnIndexByName(columnName);
        this.documentTable.find('tbody tr').each(($tr) => {
            list.push($tr.find('td').eq(index).text());
        });
        let flag;
        if (sortedBy === 'ascending') {
            flag = this.isAscending(list);
        } else {
            flag = this.isDescending(list);
        }

        expect(flag).to.be.true;
    }

    getColumnIndexByName(columnName: string) {
        if (columnName === 'Jurisdiction') {
            return 4;
        } else if (columnName === 'Condition') {
            return 1;
        } else if (columnName === 'Document type') {
            return 0;
        }
    }

    isAscending(list: string[]) {
        return list.every((value: string, index: number, array: string[]) => {
            if (index === 0) {
                return true; // Skip the first element
            }
            return value >= array[index - 1];
        });
    }

    isDescending(list: string[]) {
        return list.every((value: string, index: number, array: string[]) => {
            if (index === 0) {
                return true; // Skip the first element
            }
            return value <= array[index - 1];
        });
    }
}

export default new SummaryTabPage();
