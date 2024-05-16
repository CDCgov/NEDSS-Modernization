class DataElementsPage {

    userViewsColumnAndSeeList(columnName) {
        const list = [];
        const index = this.getColumnIndexByName(columnName);
        this.openInvestigationTable.find("tbody tr").each(($tr) => {
            list.push($tr.find("td").eq(index).text());
        });
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
}

export const pageLibraryDataElementsPage = new DataElementsPage()