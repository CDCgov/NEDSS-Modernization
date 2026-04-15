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

}



export const patientEntitySearch = new PatientEntitySearch();
