class ViewPHDCPage {
  navigateToDRSAQueue() {
    cy.get("a").contains("Documents Requiring Security Assignment").eq(0).click();
  }

  runPHCRImporter() {
    cy.exec("npm run PHCRImporter")
  }

  openPatientCaseReport(patientFirstName) {
    cy.get("table#parent th img#queueIcon").eq(3).click();
    cy.get("input#SearchText1").first().type(patientFirstName);
    cy.get("#b1SearchText1").click({force: true});
    cy.get("a").contains("Case Report").first().click();
  }

  viewPHDC(patientName) {
    cy.contains(patientName);
    cy.contains("Public Health Case Report - Data from Legacy System to CDA");
  }

  openECRViewer() {
    let ecrViewerURL = "/nbs/LoadViewDocument2.do?method=originalDocumentView&eventType=eICR";
    cy.window().then(win => {
      cy.stub(win, 'open').as('open')
    })
    cy.get("a").contains("View eICR Document |").click();
    cy.get("@open").should("have.been.calledWith", ecrViewerURL);
    cy.visit(ecrViewerURL);
    cy.contains("Initial Public Health Case Report");
  }

  getPatientWithPHDC({patientLastName, patientFirstName}) {
    cy.get("#DEM102").type(patientLastName);
    cy.get("#DEM104").type(patientFirstName);
    cy.get("input[value='Search']").click();
    cy.get('div#patient-search-results table tbody tr td a').eq(0).click();

    cy.contains('Events').click()
    cy.contains('section', 'Documents').within(() => {
      cy.get('a[href*="/nbs/api/profile/"]').eq(0).click();
    })
    
  }

  eCRViewButtonNotVisible() {
    cy.get("a").contains("View eICR Document |").should("not.exist");
  }
}

export const viewPHDCPage = new ViewPHDCPage();
