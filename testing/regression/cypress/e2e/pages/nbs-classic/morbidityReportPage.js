class MorbidityReportPage {
    morbidityReportLink = 'font.boldEightBlack';
    reportInformationTab = 'font.boldNineYellow';
    conditionField = 'input[name="conditionCd_textbox"]';
    jurisdictionField = 'input[name="morbidityReport.theObservationDT.jurisdictionCd_textbox"]';
    morbidityDateField = '#morbidityReport\\.theObservationDT\\.activityToTime_s';
    facilityProviderField = 'input[name="entity-codeLookupText-Org-ReportingOrganizationUID"]';
    codeLookupButton = 'input[value="Code Lookup"]';
    submitButton = '#Submit';
  
    clickMorbidityReport() {
      cy.get(this.morbidityReportLink).contains('Morbidity Report').click();
    }
  
    clickReportInformationTab() {
      cy.get(this.reportInformationTab).contains('Report Information').click();
    }
  
    selectCondition(condition) {
      cy.get(this.conditionField).type(condition + '{enter}');
    }
  
    selectJurisdiction(jurisdiction) {
      cy.get(this.jurisdictionField).type(jurisdiction + '{enter}');
    }

    enterMorbidityDate(date) {
      const [month, day, year] = date.split('/');
      const formattedDate = `${month.padStart(2, '0')}${day.padStart(2, '0')}${year}`;
      cy.get(this.morbidityDateField).type(formattedDate);
    }
    
    enterFacilityProvider(value) {
      cy.get(this.facilityProviderField).type(value);
    }
     
    clickCodeLookup() {
      cy.get(this.codeLookupButton).first().click();
    }
    
    clickSubmit() {
      cy.get(this.submitButton).click();
    }    

    confirmSubmission() {
      cy.on('window:confirm', () => true); // Automatically confirm the popup
    }    
}
export const morbidityReportPage = new MorbidityReportPage();
