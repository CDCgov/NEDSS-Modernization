class SearchEventPage {
  clickEventInvestigation() {
    cy.get('a[href*="advanced-search/event"]').click();
    cy.wait(500);
    let elm = cy.get("div[data-testid='accordionItem_event-type-section'] select");
    elm.select('Investigation');
    cy.wait(500);
  }  

  selectEventInvestigationCondition() {    
    let elm = cy.get("#conditionInput").click();
    let elm2 = cy.get("#react-select-3-option-50").click();
    cy.get('.multi-select__control--is-focused ').click();
    cy.wait(500);
  }

  selectEventInvestigationProgramArea() {
    let elm = cy.get("#react-select-5-placeholder").click({force: true});
    let elm2 = cy.get("#react-select-5-option-0").click({force: true});
    cy.wait(500);
  }

  selectEventInvestigationJurisdiction() {
    let elm = cy.get("#react-select-7-placeholder").click({force: true});
    let elm2 = cy.get("#react-select-7-option-1").click({force: true});
    cy.wait(500);
  }

  selectPregnancy() {
    let elm = cy.get('select[name="pregnancyStatus"]').select("YES");
    cy.wait(500);
  }

  selectInvestigationEventType() {
    let elm = cy.get('select[name="eventId.investigationEventType"]').select("State Case Id");
    cy.wait(500);
    let elm2 = cy.get('input[id="eventId.id"]').type("1");
    cy.wait(500);
  }

  search() {
    let elm = cy.get('button[data-testid="search"]').click(({force: true}));
    cy.wait(500);
  }

  selectInvestigationUpdatedBy() {
    let elm = cy.get('input[id="updatedBy"]').type("u");
    let elm2 = cy.get('li[class="usa-combo-box__list-option"]').click();
    cy.wait(500);
  }
}

export const searchEventPage = new SearchEventPage();
