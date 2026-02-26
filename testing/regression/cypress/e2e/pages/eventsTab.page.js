class EventsTabPage {
  get table() {
    return "section";
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
    const myArray = [];    
    cy.contains("section", tableName).within(() => {
      cy.get("th")
      .then((headerElements) => {
        const headers = Cypress.$.map(headerElements, (headerElement) => {
          return Cypress.$(headerElement).text().trim();
        }).filter(Boolean);
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
    })
  }
}

export default new EventsTabPage();
