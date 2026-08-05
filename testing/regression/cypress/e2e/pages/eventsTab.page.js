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
  morbidityReportLinks = 'a[href="/nbs/api/profile/10000001/report/morbidity/10009436"]'

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

  verifyReportCountUnchanged(ReportType) {
    const reportIndex = this.reportTypeIndexMap[ReportType];

    // Wait for spinner to disappear before getting the count
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    // Now get the count and verify it remains the same
    const initialCount = Cypress.env('reportCount');
    
    cy.get('._title_r9t1e_16 ._default_rfc4h_1._small_rfc4h_64._regular_rfc4h_76')
      .eq(reportIndex)
      .invoke('text')
      .then(text => {
        const newCount = parseInt(text.trim(), 10);
        cy.log(`Initial count: ${initialCount}, New count: ${newCount}`);
        expect(newCount, `${ReportType} count should remain the same`)
          .to.equal(initialCount);
      });
  }

  saveInitialTreatmentCount() {
    // Wait for spinner to disappear
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    // Get the first morbidity report row and find the treatment count
    cy.get('#morbidity-reports-table tbody tr')
      .first()
      .find('td:nth-child(6)') // Treatment info column
      .then($td => {
        // Check if the ul exists, if not, count is 0
        const $list = $td.find('ul._treatments_t5nhh_1 li');
        const count = $list.length || 0;
        Cypress.env('initialTreatmentCount', count);
        cy.log(`Initial treatment count in first morbidity report: ${count}`);
      });
    }

  verifyTreatmentCountIncreased() {
    // Wait for spinner to disappear
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    const initialCount = Cypress.env('initialTreatmentCount');
    const eventId = Cypress.env('morbidityEventId');
    
    // Find the row containing the specific event ID and check its treatment count
    cy.get('#morbidity-reports-table tbody tr')
      .contains('td:first-child a', eventId)
      .parent() // Get the td
      .parent() // Get the tr
      .find('td:nth-child(6)') // Treatment info column
      .find('ul._treatments_t5nhh_1 li')
      .its('length')
      .then(newCount => {
        cy.log(`Initial treatment count: ${initialCount}, New count: ${newCount}`);
        expect(newCount, `Treatment count for ${eventId} should increase by 1`)
          .to.equal(initialCount + 1);
      });
  }

  clickFirstMorbidityReportLinkStoreEventID() {
    // Wait for spinner to disappear
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    // Get the first morbidity report row
    cy.get('#morbidity-reports-table tbody tr:first-child')
      .scrollIntoView()
      .should('be.visible')
      .within(() => {
        // Get the link element
        cy.get('td:first-child a')
          .scrollIntoView()
          .should('be.visible')
          .then($link => {
            // Extract and store the event ID
            const eventId = $link.text().trim();
            Cypress.env('morbidityEventId', eventId);
            cy.log(`Stored morbidity event ID: ${eventId}`);
            
            // Click the link
            cy.wrap($link).click();
          });
      });
  }
  verifySavedMorbidityReportJurisdiction(expectedJurisdiction) {
    // Wait for spinner to disappear
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    const eventId = Cypress.env('morbidityEventId');
    
    cy.log(`Looking for morbidity report with Event ID: ${eventId}`);
    
    // Find the row with the specific event ID and check the jurisdiction column
    cy.get('#morbidity-reports-table tbody tr')
      .filter((index, row) => {
        return Cypress.$(row).find('td:first-child a').text().trim() === eventId;
      })
      .should('have.length', 1, `Should find exactly one row with Event ID: ${eventId}`)
      .find('td:nth-child(7)') // Jurisdiction is the 7th column (1-based index)
      .should('be.visible')
      .invoke('text')
      .then(text => {
        const actualJurisdiction = text.trim();
        cy.log(`Jurisdiction for ${eventId}: "${actualJurisdiction}" (expected: "${expectedJurisdiction}")`);
        expect(actualJurisdiction).to.equal(expectedJurisdiction);
      });
  }
}

export default new EventsTabPage();
