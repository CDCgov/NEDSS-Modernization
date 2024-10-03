class LabReportPage {
    dataEntryMenu = 'a[href="/nbs/LoadNavbar.do?ContextAction=DataEntry"]';
    labReportLink = 'font.boldEightBlack';
    labReportTab = 'td#tabs0head1';
    reportingFacilityField = '#NBS_LAB365Text';
    quickCodeLookupButton = '#NBS_LAB365CodeLookupButton';
    sameAsReportingFacilityCheckbox = 'input[name="pageClientVO.answer(NBS_LAB267)"]';
    jurisdictionField = 'input[name="INV107_textbox"]';
    resultedTestField = 'input[name="NBS_LAB220_textbox"]';
    codedResultField = 'input[name="NBS_LAB280_textbox"]';
    numericResultField = '#NBS_LAB364';
    unitsField = 'input[name="LAB115_textbox"]';
    textResultField = '#NBS_LAB208';
    addButton = '#AddButtonToggleRESULTED_TEST_CONTAINER > td > input';
    submitButton = 'input#SubmitTop';
    
    clickDataEntry() {
      cy.get(this.dataEntryMenu).click();
    }
  
    clickLabReport() {
      cy.get(this.labReportLink).contains('Lab Report').click();
    }
  
    clickLabReportTab() {
      cy.get(this.labReportTab).first().click();
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
  
    selectJurisdiction() {
      cy.get(this.jurisdictionField).type('{downarrow}{downarrow}{enter}');
    }

    selectResultedTest() {
      cy.get(this.resultedTestField).type('{downarrow}{downarrow}{downarrow}{downarrow}{downarrow}{enter}');
    }

    selectCodedResult() {
      cy.get(this.codedResultField).type('{downarrow}{enter}');
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

    clickAddButton() {
      cy.get(this.addButton).click();
  }

    clickSubmitButton() {
      cy.get(this.submitButton).first().click(); // Clicking the first submit button
  }
}
  export const labReportPage = new LabReportPage();
  