class EventsTabPage {
  // Constants
  static SPINNER_SELECTOR = '._indicator_1vvtd_1';
  static COUNT_BADGE_SELECTOR = '._title_r9t1e_16 ._default_rfc4h_1._small_rfc4h_64._regular_rfc4h_76';
  static MORBIDITY_TABLE_SELECTOR = '#morbidity-reports-table tbody tr';
  static TREATMENT_COLUMN = 6;
  static JURISDICTION_COLUMN = 7;
  static ASSOCIATED_COLUMN = 8;

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
  
  waitForSpinner(timeout = 10000) {
    cy.get(EventsTabPage.SPINNER_SELECTOR, { timeout })
      .should('not.exist');
  }

  getCountBadge(reportIndex) {
    return cy.get(EventsTabPage.COUNT_BADGE_SELECTOR).eq(reportIndex);
  }

  extractAndStoreEventId($link) {
    const eventId = $link.text().trim();
    Cypress.env('morbidityEventId', eventId);
    cy.log('Stored morbidity event ID: ' + eventId);
    return eventId;
  }

  findRowByEventId(eventId) {
    return cy.get(EventsTabPage.MORBIDITY_TABLE_SELECTOR)
      .filter((index, row) => {
        return Cypress.$(row).find('td:first-child a').text().trim() === eventId;
      })
      .should('have.length', 1, 'Should find exactly one row with Event ID: ' + eventId);
  }

  getTreatmentCount($td) {
    const $list = $td.find('ul._treatments_t5nhh_1 li');
    return $list.length || 0;
  }

  clickLink($link) {
    cy.wrap($link).click();
  }

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
    });
  }

  clickAddButton(buttonValue) {
    cy.get('button').contains(buttonValue).click();
  }

  getReportCount(ReportType) {
    const reportIndex = this.reportTypeIndexMap[ReportType];
    this.waitForSpinner();
    
    this.getCountBadge(reportIndex)
      .invoke('text')
      .then(text => {
        const count = parseInt(text.trim());
        Cypress.env('reportCount', count);
        cy.log('Saved ' + ReportType + ' count: ' + count);
      });
  }

  verifyReportCountIncreased(ReportType) {
    const reportIndex = this.reportTypeIndexMap[ReportType];
    this.waitForSpinner();
    
    const initialCount = Cypress.env('reportCount');
    
    this.getCountBadge(reportIndex)
      .invoke('text')
      .then(text => {
        const newCount = parseInt(text.trim(), 10);
        cy.log(ReportType + ' - Initial: ' + initialCount + ', New: ' + newCount);
        expect(newCount, ReportType + ' count should increase by 1')
          .to.equal(initialCount + 1);
      });
  }

  verifyReportCountUnchanged(ReportType) {
    const reportIndex = this.reportTypeIndexMap[ReportType];
    this.waitForSpinner();
    
    const initialCount = Cypress.env('reportCount');
    
    this.getCountBadge(reportIndex)
      .invoke('text')
      .then(text => {
        const newCount = parseInt(text.trim(), 10);
        cy.log(ReportType + ' - Initial: ' + initialCount + ', New: ' + newCount);
        expect(newCount, ReportType + ' count should remain the same')
          .to.equal(initialCount);
      });
  }

  saveInitialTreatmentCount() {
    this.waitForSpinner();
    
    cy.get(EventsTabPage.MORBIDITY_TABLE_SELECTOR)
      .first()
      .find('td:nth-child(' + EventsTabPage.TREATMENT_COLUMN + ')')
      .then($td => {
        const count = this.getTreatmentCount($td);
        Cypress.env('initialTreatmentCount', count);
        cy.log('Initial treatment count in first morbidity report: ' + count);
      });
  }

  verifyTreatmentCountIncreased() {
    this.waitForSpinner();
    
    const initialCount = Cypress.env('initialTreatmentCount');
    const eventId = Cypress.env('morbidityEventId');
    
    cy.log('Looking for morbidity report with Event ID: ' + eventId);
    
    this.findRowByEventId(eventId)
      .find('td:nth-child(' + EventsTabPage.TREATMENT_COLUMN + ')')
      .find('ul._treatments_t5nhh_1 li')
      .its('length')
      .then(newCount => {
        cy.log('Initial treatment count: ' + initialCount + ', New count: ' + newCount);
        expect(newCount, 'Treatment count for ' + eventId + ' should increase by 1')
          .to.equal(initialCount + 1);
      });
  }

  clickFirstMorbidityReportLinkStoreEventID() {
    this.waitForSpinner();
    
    cy.get(EventsTabPage.MORBIDITY_TABLE_SELECTOR)
      .first()
      .scrollIntoView()
      .should('be.visible')
      .within(() => {
        cy.get('td:first-child a')
          .scrollIntoView()
          .should('be.visible')
          .then($link => {
            this.extractAndStoreEventId($link);
            this.clickLink($link);
          });
      });
  }

  clickStoredMorbidityReport() {
    this.waitForSpinner();
    
    const eventId = Cypress.env('morbidityEventId');
    
    cy.log('Looking for morbidity report with Event ID: ' + eventId);
    
    this.findRowByEventId(eventId)
      .find('td:first-child a')
      .scrollIntoView()
      .should('be.visible')
      .click();
  }


  verifySavedMorbidityReportJurisdiction(expectedJurisdiction) {
    this.waitForSpinner();
    
    const eventId = Cypress.env('morbidityEventId');
    
    cy.log('Looking for morbidity report with Event ID: ' + eventId);
    
    this.findRowByEventId(eventId)
      .find('td:nth-child(' + EventsTabPage.JURISDICTION_COLUMN + ')')
      .should('be.visible')
      .invoke('text')
      .then(text => {
        const actualJurisdiction = text.trim();
        cy.log('Jurisdiction for ' + eventId + ': "' + actualJurisdiction + '" (expected: "' + expectedJurisdiction + '")');
        expect(actualJurisdiction).to.equal(expectedJurisdiction);
      });
  }

  verifyStoredMorbidityReportHasAssociation() {
    this.waitForSpinner();
    
    const eventId = Cypress.env('morbidityEventId');
    
    expect(eventId, 'Morbidity event ID should exist').to.not.be.empty;
    
    cy.log('Looking for morbidity report with Event ID: ' + eventId);
    
    this.findRowByEventId(eventId)
      .find('td:nth-child(' + EventsTabPage.ASSOCIATED_COLUMN + ')')
      .should('be.visible')
      .invoke('text')
      .then(text => {
        const associatedText = text.trim();
        cy.log('Associated with for ' + eventId + ': "' + associatedText + '"');
        expect(associatedText, 'Associated with should have an investigation ID')
          .to.match(/CAS\d+/);
      });
  }

  clickStoredInvestigationId() {
    // Get the stored investigation ID
    const investigationId = Cypress.env('investigationId');
    
    // Wait for page to load
    cy.get('._indicator_1vvtd_1', { timeout: 10000 })
      .should('not.exist');
    
    // Find and click the investigation link in the Investigations table
    cy.get('#investigations-table tbody tr')
      .contains('td a', investigationId)
      .should('be.visible')
      .click();
  }
}

export default new EventsTabPage();