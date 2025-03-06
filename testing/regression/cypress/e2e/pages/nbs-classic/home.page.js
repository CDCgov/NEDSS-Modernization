class ClassicHomePage {

  navigateToPatientSearchPane() {
    cy.get('#homePageAdvancedSearch').click()
  }

  enterLastName(text) {
    cy.get('[id="name.last"]').type(text);
  }

  clickSearchBtnInPatientSearchPane() {
    cy.contains('button', 'Search').eq(0).click()
  }

  enterFirstName(text) {
    cy.get('[id="name.last"]').type(text)
  }

  clickAddNewBtnInPatientSearchPane() {
    cy.contains('button', 'Add new').eq(0).click()
  }

  clickAddNewLabReportBtnInPatientSearchPane() {
    cy.contains('button', 'Add new lab report').eq(0).click()
  }

  verifyAddLabReport() {
    cy.contains('Add Lab Report')
  }

  clickDefaultQueue(queueName) {
    cy.get('.content ul li').then(($element) => {
        const text = $element.text().trim()
        if(!(text.includes(`${queueName} (0)`))) {
            cy.get($element).contains(queueName).eq(0).click()
        }
    })
  }

  createTwoPatients() {
    const createPatient = () => {
        cy.get('#homePageAdvancedSearch').click()
        cy.get('[id="name.last"]').type("Simpson");
        cy.get('[id="name.first"]').type("Martin");
        cy.wait(1000);
        cy.contains('button', 'Search').eq(0).click()
        cy.wait(2000)
        cy.contains('button', 'Add new').eq(0).click()
        cy.contains('button', 'Add new patient').eq(0).click()
        cy.wait(2000)
        cy.get('input[id="administrative.asOf"]').eq(0).clear()
        cy.get('input[id="administrative.asOf"]').eq(0).type('03/04/2024');
        cy.contains('button', 'Save').eq(0).click()
        cy.wait(3000)
        cy.get('body').then(($body) => {
            if($body.find('a:contains("View patient")').length > 0) {
                cy.contains('a', 'View patient').click()
            } else {
                cy.contains('a', 'Continue anyways').click()
                cy.contains('a', 'View patient').click()
            }
        })
        cy.contains('Home').eq(0).click()
    }
    createPatient()
    cy.wait(3000)
    createPatient()
  }

  clickMergePatientTab() {
    cy.contains("Merge Patients").eq(0).click()
  }

  clickOnManualSearch() {
    cy.contains("Manual Search").eq(0).click()
  }

  verifyFindPatientPage() {
    cy.contains("Search Criteria").eq(0)
  }

  searchUser() {
    cy.wait(2000)
    cy.get('#DEM102').type('Simpson')
    cy.get('#DEM104').type('Martin')
    cy.get('input[type="button"][value="Submit"]').eq(0).click()
  }

  selectUsersToMerge() {
    cy.get('.selectCheckBoxMerge').click({ multiple: true })
    cy.get('input[type="button"][value="Merge"]').eq(0).click()
    cy.on('window:confirm', (text) => {
        return true
    })
  }

  clickSystemIdentifiedTab() {
    cy.contains("System Identified").eq(0).click()
  }

  verifyMergeCandidateListDisplayed() {
    cy.contains("Merge Candidate List").eq(0)
  }

  clickReportsTab() {
    cy.contains("Reports").eq(0).click()
  }

  verifyReportsPageDisplayed() {
    cy.contains("Private Reports").eq(0)
  }

  createRunReports() {
    cy.get("#Public a").contains("Expand Subsections").eq(0).click()
    cy.get("table#Public2 a").contains("Run").eq(0).click()
    cy.get("#id_C_D01").select("AIDS", {force: true})
    cy.get("td").contains("Run").eq(0).click()
  }

  verifyDocumentsRequiringSecurityAssignment() {
    cy.get("a").contains("Documents Requiring Security Assignment").eq(0).click()
    cy.get("table#parent th img#queueIcon").eq(3).click()
    cy.get("input#SearchText1").first().type("jaja")
    cy.get("#b1SearchText1").click({force: true})
  }

  verifyDocumentsRequiringReview() {
    cy.get("a").contains("Documents Requiring Review").eq(0).click()
    // cy.get("a").contains("Lab Report").eq(0).click()
    cy.get("th.sortable").eq(1).find("img#queueIcon").click()
    cy.get("label.selectAll").eq(1).click()
    cy.get("label").contains("Last 14 Days").click()
    cy.get("#b1").click({force: true})
  }

  verifyOpenInvestigations() {
    cy.get("a").contains("Open Investigations").eq(0).click();
  } 

  clickSortTableOption(string) {
    cy.get(`svg[aria-label="${string}"]`).click();
  }

  verifyTopAfterSortSearch(string) {
    cy.get("#patient-search-results tbody tr").eq(0).contains(string);
  }

  verifyNoTopAfterSortSearch(string) {
    cy.get("#patient-search-results tbody tr").eq(0).should('not.contain', string);
  }

  clickResultIdLink() {
    cy.wait(3000);
    this.copySearchRowInfo();

    // this.patientVerifySearchTableInfo();
    // cy.get("div#patient-search-results a").eq(0).click();
  }

  returnPatientData() {
    return {
      ssn: "986-37-4923",
      dob: "05/16/1961",
      address: "7135 Emmet Spur",
      phone: "732-721-2970",
      zip: "74146",
      email: "CadenRatkeyklkb79@hotmail.com"
    };
  }
  
  patientVerifySearchTableInfo() {
    let patientData = this.returnPatientData();    
    cy.get("div#patient-search-results").contains(patientData.ssn);
    cy.get("div#patient-search-results").contains(patientData.dob);    
    cy.get("div#patient-search-results").contains(patientData.address);
    cy.get("div#patient-search-results").contains(patientData.phone);  
    cy.get("div#patient-search-results").contains(patientData.zip);
    cy.get("div#patient-search-results").contains(patientData.email);
  }

  patientProfileSummaryVerify() {
    let patientData = this.returnPatientData();
    cy.get("p.patient-summary-item-value").contains(patientData.ssn);
    cy.get("p.patient-summary-item-value").contains(patientData.dob);    
    cy.get("p.patient-summary-item-value").contains(patientData.address);
    cy.get("p.patient-summary-item-value").contains(patientData.phone);  
    cy.get("p.patient-summary").contains(patientData.zip);
    cy.get("a").contains("Demographics").click();
    cy.get("div#demographics-tabpanel").contains(patientData.email);
  }

  copySearchRowInfo() {
    cy.get("body").then((body) => {            
      if (body.find("div#patient-search-results").length > 0) {        
        cy.get('div#patient-search-results tbody tr td').then(($tds) => {
          const tdTexts = $tds.toArray().map(td => td.innerText.trim());
          cy.log(tdTexts);  
          Cypress.env("tdTexts", tdTexts);
        });
      }
    });
  }

}

export default new ClassicHomePage();
