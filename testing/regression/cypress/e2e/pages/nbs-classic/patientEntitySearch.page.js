class PatientEntitySearch {

getPatientByName({patientLastName, patientFirstName}) {
  cy.log(`Searching for patient with name: ${patientFirstName} ${patientLastName}`);
    cy.get("#DEM102").type(patientLastName);
    cy.get("#DEM104").type(patientFirstName);
    cy.get("input[value='Search']").click();

    cy.log('Clicking first patient ID hyperlink');
  
    // Click the patient ID link in the first row of the search results table
    cy.get('tbody tr:first-child td:first-child a')
      .should('be.visible')
      .first()
      .click();
    
    cy.log('First patient ID hyperlink clicked');
  }

// Function to safely navigate to Events tab
navigateToEventsTab() {
  // Click first patient ID
  cy.get('tbody tr:first-child td:first-child a')
    .should('be.visible')
    .click();
  
  // Wait for patient page to stabilize
  cy.url().should('include', '/patient/');
  cy.get('._tab-navigation_10y0t_1', { timeout: 10000 }).should('be.visible');
  
  // Force a small wait for React to hydrate
  cy.wait(500); 
  
  // Click Events tab with retry logic
  cy.get('._tab_10y0t_1')
    .contains('events')
    .should('be.visible')
    .click({ force: true }); // force: true can help if element is covered
  
  // Wait for URL to update
  cy.url({ timeout: 10000 }).should('include', '/events');
  
  // Wait for content to load - look for specific elements
  cy.get('body', { timeout: 15000 }).should($body => {
    // Either events table or no results message should appear
    const hasTable = $body.find('table.usa-table').length > 0;
    expect(hasTable).to.be.true;
  });
  }


}



export const patientEntitySearch = new PatientEntitySearch();