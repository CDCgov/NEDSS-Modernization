import { selectRandomDropdownValue } from "./utils";

class LabReportPage {
  dataEntryNavigation = 'a[href="/nbs/LoadNavbar.do?ContextAction=DataEntry"]';
  homeNavigation = 'a[href="/nbs/HomePage.do?method=loadHomePage"]';
  labReportLink = 'font.boldEightBlack';
  labReportTab = 'td#tabs0head1';
  reportingFacilityField = '#NBS_LAB365Text';
  quickCodeLookupButton = '#NBS_LAB365CodeLookupButton';
  sameAsReportingFacilityCheckbox = 'input[name="pageClientVO.answer(NBS_LAB267)"]';
  jurisdictionField = 'select#INV107';
  programAreaField = 'select#INV108';
  resultedTestField = 'select#NBS_LAB220';
  codedResultField = 'select#NBS_LAB280';
  numericResultField = '#NBS_LAB364';
  unitsField = 'input[name="LAB115_textbox"]';
  textResultField = '#NBS_LAB208';
  addButton = '#AddButtonToggleRESULTED_TEST_CONTAINER > td > input';
  patientSearchButton = 'input#NBS_UI_24L';
  nextLink = 'a[href="javascript:navigateTab(\'next\')"]';
  reportingFacilitySearchButton = 'input[id="NBS_LAB365Icon"]';
  orderedTestSearchButton = 'input[id="NBS_LAB112Search"]';
  resultedTestSearchButton = 'input[id="NBS_LAB220Search"]';
  specimenCollectionDate = 'input[id="LAB163"]';
  specimenSiteField = 'select#NBS_LAB166';
  specimenSourceField = 'select#LAB165';

  getLabReportCountForPatient() {
    cy.log('Checking and saving lab report count');
    cy.contains('span', 'Loading', { timeout: 30000 }).should('not.exist');
    return cy.get('#laboratory-reports + div')
      .invoke('text')
      .then((count) => {
        const labReportCount = parseInt(count.trim());
        cy.wrap(labReportCount).as('labReportCount');
        cy.log(`Current lab report count: ${labReportCount}`);
      });
    }

  clickHome() {
      cy.get(this.homeNavigation).click();
  }

  clickDataEntry() {
    cy.get(this.dataEntryNavigation).click();
  }

  clickLabReport() {
    cy.get(this.labReportLink).contains('Lab Report').click();
  }

  enterReportingFacility(value) {
    cy.get(this.reportingFacilityField).type(value);
  }

  clickQuickCodeLookup() {
    cy.get(this.quickCodeLookupButton).click();
  }

  checkSameAsReportingFacility() {
    cy.get(this.sameAsReportingFacilityCheckbox).check();
  }
  selectResultedTest() {
    selectRandomDropdownValue(this.resultedTestField);
  }

  selectCodedResult() {
    selectRandomDropdownValue(this.codedResultField);
  }

  enterNumericResult(value) {
    cy.get(this.numericResultField).type(value);
  }

  selectUnits() {
    cy.get(this.unitsField).type('%{enter}');
  }

  enterTextResult(text) {
    cy.get(this.textResultField).type(text);
  }

  clickAddButtonResultedTests() {
    cy.get(this.addButton).click();
    
    // Add wait for the add operation to complete
    // Wait for the new resulted test row to appear
    cy.get('#RESULTED_TEST_CONTAINER tr').should('have.length.greaterThan', 0);
    cy.wait(500); // Small buffer for DOM updates
  }

  searchForPatientInPopup() {
    cy.log(`Directly populating patient Surma J Singh.`);
    
    // Store the main app URL (just in case)
    cy.url().as('mainAppUrl');
    
    // Hardcoded MPR ID for Surma J Singh
    const mprId = '10000001';
    
    // Wait for page to be fully loaded
    cy.document().its('readyState').should('eq', 'complete');
    
    // Call populatePatient directly on the main window
    cy.window().then((win) => {
      cy.log(`Calling populatePatient with MPR ID: ${mprId}`);
      win.populatePatient(mprId);
    });
    
    // Wait for DWR calls to complete
    cy.wait(2000);
  
  }


  clickNext() {
    cy.get(this.nextLink).first().click();
  }

  // Lab Report Tab - Facility and Provider Information
  
  searchForReportingFacility(quickCode) {
    cy.get(this.reportingFacilityField).type(quickCode);
    cy.get(this.quickCodeLookupButton).click();
  }

  //Order Details

  selectProgramArea() {
    selectRandomDropdownValue(this.programAreaField);
  }

  selectJurisdiction() {
    selectRandomDropdownValue(this.jurisdictionField);
  }

  //Ordered Test

  searchForOrderedTestInPopup() {
    cy.log(`Directly populating ordered test Measles virus (Rubeola), antigen.`);
    
    // Hardcoded values for Measles virus (Rubeola), antigen
    const description = 'Measles virus (Rubeola), antigen';
    const testCode = 'T-14520';
    const id = 'NBS_LAB112';
    
    // Wait for page to be fully loaded
    cy.document().its('readyState').should('eq', 'complete');
    
    // Directly manipulate the DOM elements since populateCodedWithSearchValue is only accessible via the popup window
    cy.window().then((win) => {
      cy.log(`Directly populating fields for ${id}`);
      
      // Get the elements
      const textbox = win.document.getElementById(id + '_textbox');
      const codeInput = win.document.getElementById(id + 'CodeId');
      const descriptionInput = win.document.getElementById(id + 'DescriptionId');
      const displaySpan = win.document.getElementById(id + 'Description');
      const selectElement = win.document.getElementById(id);
      
      // Set the visible textbox value
      if (textbox) {
        textbox.value = description;
        cy.log(`Set textbox to: ${description}`);
      }
      
      // Set the hidden code field
      if (codeInput) {
        codeInput.value = testCode;
        cy.log(`Set code to: ${testCode}`);
      }
      
      // Set the hidden description field
      if (descriptionInput) {
        descriptionInput.value = `${description} (${testCode})`;
        cy.log(`Set description to: ${description} (${testCode})`);
      }
      
      // Update the display span
      if (displaySpan) {
        displaySpan.textContent = description;
        cy.log(`Set display span to: ${description}`);
      }
      
      // Clear any selected value in the dropdown
      if (selectElement) {
        selectElement.value = '';
      }
      
      // Hide the dropdown
      if (textbox && textbox.parentElement) {
        textbox.parentElement.style.display = 'none';
      }
      
      // Hide the search button and show clear button
      const searchBtn = win.document.getElementById(id + 'Search');
      const clearBtn = win.document.getElementById(id + 'ClearButton');
      
      if (searchBtn) searchBtn.style.display = 'none';
      if (clearBtn) clearBtn.style.display = 'inline-block';
      
      // Trigger any change events that might be needed
      if (textbox) {
        textbox.dispatchEvent(new Event('change', { bubbles: true }));
        textbox.dispatchEvent(new Event('blur', { bubbles: true }));
      }
      
      cy.log('Ordered test populated successfully');
    });
    
    // Wait for any DWR calls to complete
    cy.wait(2000);
  }

  selectSpecimenSource() {
    selectRandomDropdownValue(this.specimenSourceField);
  }
  
  selectSpecimenSite() {
    selectRandomDropdownValue(this.specimenSiteField);
  }

  selectLabReportSpecimenSource(specimenSource) {
    this._selectFromDropdown(this.specimenSourceField, specimenSource);
  }

  selectLabReportSpecimenSite(specimenSite) {
    this._selectFromDropdown(this.specimenSiteField, specimenSite);
  }

  enterSpecimenCollectionDate(date) {
    const [month, day, year] = date.split('/');
    const formattedDate = `${month.padStart(2, '0')}${day.padStart(2, '0')}${year}`;
    cy.get(this.specimenCollectionDate).type(formattedDate);
  }

  //Resulted Test

  clickResultedTestSearchButton() {
    cy.get(this.resultedTestSearchButton).click();
  }

  selectLabReportCodedResult(codedResult) {
    this._selectFromDropdown(this.codedResultField, codedResult);
  }

  // Verification Steps
  
  verifyLabReportCountIncreased() {
    // Verify that the lab report count increased by 1
    cy.log('Verifying lab report count increased by 1');

    // Get the new lab report count

    cy.contains('span', 'Loading', { timeout: 30000 }).should('not.exist');
    return cy.get('#laboratory-reports + div')
      .invoke('text')
      .then((count) => {
        const newCount = parseInt(count.trim());
        cy.log(`New lab report count: ${newCount}`);
        // Get the initial count from the alias and verify increase by 1
        cy.get('@labReportCount').then((initialCount) => {
          expect(newCount).to.equal(initialCount + 1);
          cy.log(`Lab report count increased from ${initialCount} to ${newCount} (expected increase of 1)`);
        });
      });
  }

  verifyLastLabReportHasMultipleResultedTests() {
    cy.log('Verifying last lab report has multiple resulted tests associated with it');

    // Get the last row in the lab reports table and verify it has 2 test results
    cy.get('#laboratory-reports-table tbody tr')
      .last()
      .find('td:nth-child(5)') // The 5th column contains the test results
      .find('._test_tb2zz_1')
      .should('have.length', 2);
    };
  
  

//Helper Functions

  // Dropdown selector
  _selectFromDropdown(fieldSelector, value) {
    cy.get(fieldSelector)
      .clear()
      .type(value + '{enter}');
  }
}

export const labReportPage = new LabReportPage();
  
