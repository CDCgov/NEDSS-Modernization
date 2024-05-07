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

  search() {
    let elm = cy.get('button[data-testid="search"]').click(({force: true}));
    cy.wait(500);
  }
}

export const searchEventPage = new SearchEventPage();
