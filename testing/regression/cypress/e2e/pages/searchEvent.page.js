class SearchEventPage {
  clickEventInvestigation() {
    cy.get('a[href*="advanced-search/event"]').click();
    cy.wait(500);
    let elm = cy.get("div[data-testid='accordionItem_event-type-section'] select");
    elm.select('Investigation');
    cy.wait(500);
  }

  clickEventLabReport() {
    cy.get('a[href*="advanced-search/event"]').click();
    cy.wait(500);
    let elm = cy.get("div[data-testid='accordionItem_event-type-section'] select");
    elm.select('Laboratory report');
    cy.wait(500);
  }

  clickEventInvestigationCriteria() {
    cy.get('button[data-testid="accordionButton_investigation-criteria-section"]').click();
    cy.wait(500);
  }

  selectEventInvestigationCondition() {    
    let elm = cy.get("#conditionInput").click();
    let elm2 = cy.get("#react-select-2-option-1").click();
    cy.get('.multi-select__control--is-focused ').click();
  }

  selectEventLabReportProgramArea() {
    let elm = cy.get("#react-select-2-placeholder").click({force: true});
    let elm2 = cy.get("#react-select-2-option-2").click();
    cy.get('.multi-select__control--is-focused ').click();
  }

  selectEventInvestigationProgramArea() {
    let elm = cy.get("#react-select-3-placeholder").click({force: true});
    let elm2 = cy.get("#react-select-3-option-0").click({force: true});
  }

  selectEventInvestigationJurisdiction() {
    let elm = cy.get("#react-select-4-placeholder").click({force: true});
    let elm2 = cy.get("#react-select-4-option-1").click({force: true});
  }

  selectEventLabReportJurisdiction() {
    let elm = cy.get("#react-select-3-placeholder").click({force: true});
    let elm2 = cy.get("#react-select-3-option-3").click({force: true});
  }

  selectPregnancy() {
    let elm = cy.get('select[name="pregnancyStatus"]').select("YES");
  }

  selectInvestigationCreatedBy() {
    let elm = cy.get('input[id="createdBy"]').type("super");
    let elm2 = cy.get('li[class="usa-combo-box__list-option"]').click({multiple: true});
  }

  selectInvestigationEventDate() {
    let elm = cy.get('select[name="eventDate.type"]').select("Last Update Date");
    let elm2 = cy.get('input[id="eventDate.from"]').type("090920022");
    let elm3 = cy.get('input[id="eventDate.to"]').type("05052024");
  }
  
  selectLabReportEventDate() {
    let elm = cy.get('select[name="eventDate.type"]').select("Last Update Date");
    let elm2 = cy.get('input[data-testid="date-picker-external-input"]').first().type("090920022");
    let elm3 = cy.get('input[data-testid="date-picker-external-input"]').last().type("05052024");
  }

  selectInvestigationEventType() {
    let elm = cy.get('select[name="eventId.investigationEventType"]').select("State Case Id");
    let elm2 = cy.get('input[id="eventId.id"]').type("1");
  }  

  selectLabReportEventType() {
    let elm = cy.get('select[name="eventId.labEventType"]').select("Accession Number");
    let elm2 = cy.get('input[id="eventId.labEventId"]').type("1");
  }

  selectLabReportEntryMethod() {
    let elm = cy.get('label[for="ELECTRONIC"]').click();
  }

  selectLabReportEnteredByMethod() {
    let elm = cy.get('label[for="EXTERNAL"]').click();
  }

  selectLabReportEventStatus() {
    let elm = cy.get('label[for="NEW"]').click();
  }

  selectLabReportProcessStatus() {
    let elm = cy.get('label[for="UNPROCESSED"]').click();
  }

  search() {
    let elm = cy.get('button[data-testid="search"]').click(({force: true}));
  }

  selectInvestigationUpdatedBy() {
    let elm = cy.get('input[id="lastUpdatedBy"]').type("super");
    let elm2 = cy.get('li[class="usa-combo-box__list-option"]').click({multiple: true});
  }

  selectInvestigationFacility() {
    let elm = cy.get('select[name="providerFacilitySearch.entityType"]').select("Facility");
    let elm2 = cy.get('input[name="providerFacilitySearch.id"]').scrollIntoView().type("a");
    let elm3 = cy.get('li[class="usa-combo-box__list-option"]').first().click({multiple: true});
  }

  selectInvestigationProvider() {
    let elm = cy.get('select[name="providerFacilitySearch.entityType"]').select("Provider");
    let elm2 = cy.get('input[name="providerFacilitySearch.id"]').type("a");
    let elm3 = cy.get('li[class="usa-combo-box__list-option"]').first().click({multiple: true});
  }

  selectLabReportProvider() {
    let elm = cy.get('select[name="providerSearch.providerType"]').select("Ordering Provider");
    let elm2 = cy.get('input[name="providerSearch.providerId"]').type("a");
    let elm3 = cy.get('li[class="usa-combo-box__list-option"]').first().click({multiple: true});
  }
  
  selectLabReportFacility() {
    let elm = cy.get('select[name="providerSearch.providerType"]').select("Ordering Facility");
    let elm2 = cy.get('input[name="providerSearch.providerId"]').type("a");
    let elm3 = cy.get('li[class="usa-combo-box__list-option"]').first().click({multiple: true});
  }

  selectInvestigationStatus() {
    let elm = cy.get('select[name="investigationStatus"]').select("Closed");
  }

  selectInvestigationOutbreakName() {
    let elm = cy.get('div[id="react-select-5-placeholder"]').click({force: true});
    let elm2 = cy.get('div[id="react-select-5-option-0"]').click({force: true});
  }

  selectInvestigationCaseStudy() {
    let elm = cy.get('div[id="react-select-6-placeholder"]').click({force: true});
    let elm2 = cy.get('div[id="react-select-6-option-0"]').click({force: true});
  }

  selectInvestigationCurrentProcessingStatus() {
    let elm = cy.get('div[id="react-select-7-placeholder"]').click({force: true});
    let elm2 = cy.get('div[id="react-select-7-option-2"]').click({force: true});
  }

  selectInvestigationNotificationStatus() {
    let elm = cy.get('div[id="react-select-8-placeholder"]').click({force: true});
    let elm2 = cy.get('div[id="react-select-8-option-0"]').click({force: true});
  }
}

export const searchEventPage = new SearchEventPage();
