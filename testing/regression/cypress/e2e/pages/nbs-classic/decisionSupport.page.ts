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

    selectCondition(condition: string) {
        cy.get('input[name="sCondCd_textbox"]').type(condition);
        cy.get('input[name="sCondCd_textbox"]').type('{enter}');
    }

    selectJurisdiction(jurisdiction: string) {
        cy.get('input[name="sJurisCd_textbox"]').type(jurisdiction);
        cy.get('input[name="sJurisCd_textbox"]').type('{enter}');
    }

    selectEventType(eventType: string) {
        cy.get('input[name="sEventCd_textbox"]').type(eventType);
        cy.get('input[name="sEventCd_textbox"]').type('{enter}');
    }

    clickSearchButton() {
        cy.get('input[value="Search"]').click();
    }

    selectSeverity(severity: string) {
        cy.get('img[name="aSevCd_button"]').click();
        cy.wait(1000);
        cy.contains(severity).click();
    }

    enterExtendedAlertMessage(message: string) {
        cy.get('textarea[name="alertClientVO.alertExtendedMessage"]').type(message);
    }

    clickAddAlertButton() {
        cy.get('input[value="Add Alert"]').click();
    }

    verifyErrorMessage(expectedError: string) {
        cy.get('div.infoBox.errors').should('contain.text', expectedError);
    }
}

export default new DecisionSupportPage();
