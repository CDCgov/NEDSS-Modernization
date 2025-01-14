class SummaryTabPage {
  get table() {
    return "table[data-testid=table]";
  }

  get openInvestigationTable() {
    return cy.get(this.table).eq(0);
  }

  get documentTable() {
    return cy.get(this.table).eq(1);
  }

  get returnToSummaryLink() {
    return cy.get(".returnToPageLink>a");
  }
  openFirstInvestigation() {
    this.openInvestigationTable.find("a").eq(0).click();
  }

  returnToSummary() {
    this.returnToSummaryLink.click();
    cy.wait(1000);
  }

  openLinkInDocumentTable(linkText) {
    this.documentTable.contains("a", linkText).click();
  }

  sort(columnName, sortBy) {
    this.openInvestigationTable
      .find("thead")
      .contains(columnName)
      .find("button")
      .click();
    if (sortBy === "descending") {
      this.openInvestigationTable
        .find("thead")
        .contains(columnName)
        .find("button")
        .click();
    }
  }

  checkIfSorted(columnName, sortedBy) {
    const list = [];
    const index = this.getColumnIndexByName(columnName);
    this.openInvestigationTable.find("tbody tr").each(($tr) => {
      list.push($tr.find("td").eq(index).text());
    });
    let flag = false;
    if (sortedBy === "ascending") {
      flag = this.isAscending(list);
    } else {
      flag = this.isDescending(list);
    }

    expect(flag).to.be.true;
  }
  
  documentTablesort(columnName, sortBy) {
    this.documentTable
      .find("thead")
      .contains(columnName)
      .find("button")
      .click({force: true});
    if (sortBy === "descending") {
      this.documentTable
        .find("thead")
        .contains(columnName)
        .find("button")
        .click({force: true});
    }
  }

  documentTableCheckIfSorted(columnName, sortedBy) {
    const list = [];
    const index = this.getColumnIndexByName(columnName);
    this.documentTable.find("tbody tr").each(($tr) => {
      list.push($tr.find("td").eq(index).text());
    });
    let flag = false;
    if (sortedBy === "ascending") {
      flag = this.isAscending(list);
    } else {
      flag = this.isDescending(list);
    }

    expect(flag).to.be.true;
  }

  getColumnIndexByName(columnName) {
    if (columnName === "Jurisdiction") {
      return 4;
    } else if (columnName === "Condition") {
      return 1;
    } else if (columnName === "Document type") {
      return 0;
    }
  }

  isAscending(list) {
    return list.every((value, index, array) => {
      if (index === 0) {
        return true; // Skip the first element
      }
      return value >= array[index - 1];
    });
  }

  isDescending(list) {
    return list.every((value, index, array) => {
      if (index === 0) {
        return true; // Skip the first element
      }
      return value <= array[index - 1];
    });
  }
}

export default new SummaryTabPage();
