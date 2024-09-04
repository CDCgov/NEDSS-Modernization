class ClassicHomePage {

  navigateToPatientSearchPane() {
    cy.get('#homePageAdvancedSearch').click()
  }

  enterLastName(text) {
    cy.get("#lastName").type(text);
  }

  clickSearchBtnInPatientSearchPane() {
    cy.contains('button', 'Search').eq(0).click()
  }

  enterFirstName(text) {
    cy.get("#firstName").type(text)
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
        cy.get("#lastName").type("Simpson");
        cy.get("#firstName").type("Martin");
        cy.wait(1000);
        cy.contains('button', 'Search').eq(0).click()
        cy.wait(2000)
        cy.contains('button', 'Add new').eq(0).click()
        cy.contains('button', 'Add new patient').eq(0).click()
        cy.wait(2000)
        cy.get("input[data-testid=date-picker-external-input]")
           .eq(0)
           .clear()
        cy.get("input[data-testid=date-picker-external-input]")
          .eq(0)
          .type('03/04/2024');
        cy.contains('button', 'Save changes').eq(0).click()
        cy.wait(3000)
        cy.get('body').then(($body) => {
            if($body.find('button:contains("View patient")').length > 0) {
                cy.contains('button', 'View patient').click()
            } else {
                cy.contains('button', 'Continue anyways').click()
                cy.contains('button', 'View patient').click()
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
}
export default new ClassicHomePage();
