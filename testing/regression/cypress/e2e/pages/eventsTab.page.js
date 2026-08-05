class EventsTabPage {
  get table() {
    return "section";
  }

  reportTypeIndexMap = {
    "Investigations": 0,
    "Lab reports": 1,
    "Morbidity reports": 2,
    "Vaccinations": 3,
    "Birth records": 4,
    "Treatments": 5,
    "Documents": 6,
    "Contacts named by patient": 7,
    "Patient named by contacts": 8,
  };

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

  clickAddButton(buttonValue) {
    cy.get('button').contains(buttonValue).click();
  }

  getReportCount(ReportType) {
    const reportIndex = this.reportTypeIndexMap[ReportType];

    // Wait for spinner to disappear
    cy.get('._indicator_1vvtd_1', { timeout: 15000 })
      .should('not.exist');
      
    cy.get('div._default_rfc4h_1._small_rfc4h_64._regular_rfc4h_76')
      .eq(reportIndex)
      .invoke('text')
      .then(text => {
        const count = parseInt(text.trim());
        Cypress.env('reportCount', count);
        cy.log(`Saved report count: ${count}`);
      });
  }

  verifyReportCountIncreased(ReportType) {
    const reportIndex = this.reportTypeIndexMap[ReportType];

    // Wait for spinner to disappear before getting the count
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    // Now get the count and verify it increased
    const initialCount = Cypress.env('reportCount');
    
    cy.get('._title_r9t1e_16 ._default_rfc4h_1._small_rfc4h_64._regular_rfc4h_76')
      .eq(reportIndex)
      .invoke('text')
      .then(text => {
        const newCount = parseInt(text.trim(), 10);
        cy.log(`Initial count: ${initialCount}, New count: ${newCount}`);
        expect(newCount, `${ReportType} count should increase by 1`)
          .to.equal(initialCount + 1);
      });
  }

}

export default new EventsTabPage();
