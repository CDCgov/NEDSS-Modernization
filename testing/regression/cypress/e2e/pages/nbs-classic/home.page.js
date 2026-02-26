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
    cy.get(`button[aria-label="${string}"]`).click();
  }

  verifyTopAfterSortSearch(string) {
    cy.get("#patient-search-results tbody tr").eq(0).contains(string);
  }

  verifyNoTopAfterSortSearch(string) {
    cy.get("#patient-search-results tbody tr").eq(0).should('not.contain', string);
  }
  
  searchArray(selector, values, field = "value") {
        if (Array.isArray(values)) {
            values.forEach(item => {
                const value = typeof item === "object" ? item[field] || item.value : item;
                if (value) {
                    cy.get(selector).contains(value);
                }
            });
        }
    }

  patientVerifySearchTableInfo() {
    const patientData = Cypress.env("patientSearchRowData");            
    cy.get("div#patient-search-results").contains(patientData.dob);    
    this.searchArray("div#patient-search-results", patientData.names);
    this.searchArray("div#patient-search-results", patientData.ids, "value");    
    this.searchArray("div#patient-search-results", patientData.addresses, "city");
    this.searchArray("div#patient-search-results", patientData.addresses, "state");
    this.searchArray("div#patient-search-results", patientData.addresses, "zipcode");
    this.searchArray("div#patient-search-results", patientData.emails);
    this.searchArray("div#patient-search-results", patientData.phones);
    cy.get("div#patient-search-results a").eq(0).click();
    this.searchArray("p.patient-summary-item-value", patientData.ids, "value");
    this.searchArray("p.patient-summary-item-value", patientData.addresses, "city");
    this.searchArray("p.patient-summary-item-value", patientData.addresses, "state");
    this.searchArray("p.patient-summary-item-value", patientData.addresses, "zipcode");    
    this.searchArray("p.patient-summary-item-value", patientData.phones);
    cy.get("a").contains("Demographics").click();
    this.searchArray("main", patientData.emails)    
  }

  copySearchRowInfo() {
    cy.wait(1000);
    cy.get("body").then((body) => {            
      if (body.find("div#patient-search-results").length > 0) {
        cy.get('div#patient-search-results tbody tr td').then(($tds) => {
          const tdTexts = $tds.toArray().map(td => td.innerText.trim());


          function categorizeAddresses(text) {
              let categorized = [];
              let lines = text.split(/\n+/).map(line => line.trim()).filter(line => line); // Remove blank lines
              let currentType = null;
              let currentValue = [];

              lines.forEach(line => {
                  if (/^[A-Za-z\s]+$/.test(line) && line.length < 30) {                      
                      if (currentType && currentValue.length > 0) {
                          categorized.push(formatAddress(currentType, currentValue));
                      }
                      currentType = line.toLowerCase();
                      currentValue = [];
                  } else {
                      currentValue.push(line);
                  }
              });
              
              if (currentType && currentValue.length > 0) {
                  categorized.push(formatAddress(currentType, currentValue));
              }

              return categorized;
          }

          function formatAddress(type, addressLines) {
              if (addressLines.length < 2) {
                  return { type, fullAddress: addressLines.join(", ") };
              }

              let cityStateZip = addressLines.pop();
              let match = cityStateZip.match(/^(.+),\s([A-Z]{2})\s(\d{5})$/);

              if (match) {
                  return {
                      type,
                      street: addressLines.join(", "),
                      city: match[1],
                      state: match[2],
                      zipcode: match[3],
                      fullAddress: addressLines.join(", ") + ", " + cityStateZip
                  };
              } else {                  
                  return { type, fullAddress: addressLines.join(", ") + ", " + cityStateZip };
              }
          }

          function categorizeEntries(text) {
              let categorized = [];
              let lines = text.split(/\n+/).map(line => line.trim()).filter(line => line); // Remove blank lines
              let currentType = null;
              let currentValue = [];

              lines.forEach(line => {
                if (/^[A-Za-z\s]+$/.test(line) && line.length < 30) {                      
                    if (currentType && currentValue.length > 0) {                          
                      categorized.push({ type: currentType, value: currentValue.join(", ") });
                    }
                    currentType = line.toLowerCase();
                    currentValue = [];
                } else {                      
                    currentValue.push(line);
                }
              });
              
              if (currentType && currentValue.length > 0) {
                  categorized.push({ type: currentType, value: currentValue.join(", ") });
              }

              return categorized;
          }

          const parsedDataTdTexts = {
            patientId: tdTexts[0],
            names: categorizeEntries(tdTexts[1]),
            dob: tdTexts[2].split(/\n+/)[0], 
            age: tdTexts[2].split(/\n+/)[1] || null, 
            gender: tdTexts[3],
            addresses: categorizeAddresses(tdTexts[4]),
            phones: categorizeEntries(tdTexts[5]),
            ids: categorizeEntries(tdTexts[6]),
            emails: tdTexts[7].split(/\n+/).map(email => ({ type: "email", value: email }))
          };

          cy.log(parsedDataTdTexts);
          Cypress.env("patientSearchRowData", parsedDataTdTexts);
          this.patientVerifySearchTableInfo();
        });
      }
    });
  }

}

export default new ClassicHomePage();
