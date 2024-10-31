class DecisionSupportPage {
    navigateToSystemManagement() {
      cy.get('a[href="/nbs/SystemAdmin.do"]').click();
    }
  
    expandDecisionSupportManagement() {
        cy.get('table#systemAdmin1 th a.toggleIconHref').click();
    }
  
    goToManageAlerts() {
      cy.get('td a[href="/nbs/AlertUser.do?method=alertAdminUser"]').click();
    }
  
    selectCondition(condition) {
      cy.get('input[name="sCondCd_textbox"]').type(condition).type('{enter}');
    }
  
    selectJurisdiction(jurisdiction) {
      cy.get('input[name="sJurisCd_textbox"]').type(jurisdiction).type('{enter}');
    }
  
    selectEventType(eventType) {
      cy.get('input[name="sEventCd_textbox"]').type(eventType).type('{enter}');
    }
  
    clickSearchButton() {
      cy.get('input[value="Search"]').click();
    }
  
    selectSeverity(severity) {
      cy.get('img[name="aSevCd_button"]').click();
      cy.wait(1000)
      cy.contains(severity).click();
    }
  
    enterExtendedAlertMessage(message) {
      cy.get('textarea[name="alertClientVO.alertExtendedMessage"]').type(message);
    }
  
    clickAddAlertButton() {
      cy.get('input[value="Add Alert"]').click();
    }
  
    verifyErrorMessage(expectedError) {
      cy.get('div.infoBox.errors').should('contain.text', expectedError);
    }
  }
  
  export default new DecisionSupportPage();
  