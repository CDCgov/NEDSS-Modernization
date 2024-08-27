class EventsTabPage {
  get table() {
    return "table[data-testid=table]";
  }

  selectMultipleInvestigations() {
    const conditionText = "Acanthamoeba Disease (Excluding Keratitis)";
    const conditionColumnIndex = 3;

    cy.get(this.table)
      .eq(0)
      .find("tbody tr")
      .each(($row) => {
        cy.wrap($row)
          .find("td")
          .eq(conditionColumnIndex)
          .invoke('text')
          .then(text => {

            if (text.includes(conditionText)) {
              cy.wrap($row)
                .find("input")
                .check({ force: true });
            }
          });
      });
}

  validateTableColumns(tableName, dataTable) {
    let tableIndex = 0;
    if (tableName === "Investigations") {
      tableIndex = 0;
    } else if (tableName === "Open investigations") {
      tableIndex = 0;
    } else if (tableName === "Documents requiring review") {
      tableIndex = 1;
    } else if (tableName === "Lab reports") {
      tableIndex = 1;
    } else if (tableName === "Morbidity reports") {
      tableIndex = 2;
    } else if (tableName === "Vaccinations") {
      tableIndex = 3;
    } else if (tableName === "Treatment") {
      tableIndex = 4;
    } else if (tableName === "Documents") {
      tableIndex = 5;
    } else if (tableName === "Contact records (contacts named by patient)") {
      tableIndex = 6;
    } else if (tableName === "Contact records (patient named by contacts)") {
      tableIndex = 7;
    }

    const myArray = [];
    cy.get(this.table)
      .eq(tableIndex)
      .find("th")
      .then((headerElements) => {
        const headers = Cypress.$.map(headerElements, (headerElement) => {
          return Cypress.$(headerElement).text().trim();
        });
        dataTable.rawTable.forEach((row) => {
          const label = row[0];
          if ((label == "Investigation #") & (tableName === "Investigations")) {
            myArray.push("");
          }
          myArray.push(label);
        });
        console.log("myArray", myArray);
        console.log("headers", headers);
        expect(headers).to.deep.equal(myArray);
      });
  }
}

export default new EventsTabPage();
