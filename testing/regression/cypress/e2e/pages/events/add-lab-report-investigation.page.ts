class AddLabReportInvestigation {
    conditionSelect = '#ccd';
    processingDecisionField = '#ProcessingDecision';
    investigationTypeField = '#investigationType';
    fieldFollowUpInvestigatorTextbox = '#NBS161Text';
    fieldFollowUpInvestigatorSelected = '#NBS161';
    fieldFollowUpDateAssignedField = '#NBS162';
    investigationStartDateField = '#INV147';
    notificationEligibilityField = '#NBS143';
    submitTopButton = '#SubmitTop';

    selectCondition(conditionText: string) {
        cy.get(this.conditionSelect).select(conditionText, { force: true });
    }

    // The classic app normally shows a "Processing Decision" pop-up here, but it
    // communicates its answer back via window.opener - which doesn't exist under
    // Cypress. We set the same hidden fields that pop-up would set (see
    // markProcessingDecisionSelectCondition in the app's own JS) and submit the
    // Select Condition form directly instead of ever opening that pop-up, mirroring
    // the same workaround already used elsewhere in this suite for other classic
    // pop-ups (e.g. searchForPatientInPopup, searchForOrderedTestInPopup).
    selectProcessingDecisionAndSubmit(decisionCode: any, investigationType: any) {
        cy.get(this.processingDecisionField).invoke('val', decisionCode);
        cy.get(this.investigationTypeField).invoke('val', investigationType);
        cy.window().then((win: any) => {
            win.document.getElementById('nedssForm').submit();
        });
    }

    fillFieldFollowUpInvestigator(quickCode: string) {
        cy.get(this.fieldFollowUpInvestigatorTextbox).invoke('val', quickCode);
        cy.window().then((win: any) => {
            win.getDWRProvider('NBS161');
        });
        cy.get(this.fieldFollowUpInvestigatorSelected).should('not.have.text', '');
    }

    // The server pre-fills Investigation Start Date using its own clock, which can
    // drift from the test runner's. Date Assigned must be >= Investigation Start
    // Date, so we copy the server's own value instead of computing "today"
    // independently, to avoid failing on any clock skew between the two.
    fillFieldFollowUpDateAssignedToMatchStartDate() {
        cy.get(this.investigationStartDateField)
            .invoke('val')
            .then((startDate) => {
                cy.get(this.fieldFollowUpDateAssignedField).invoke('val', startDate);
            });
    }

    selectNotificationEligibility(text: string) {
        cy.get(this.notificationEligibilityField).select(text, { force: true });
    }

    clickSubmit() {
        cy.get(this.submitTopButton).click();
    }

    verifyInvestigationSavedSuccessfully() {
        cy.contains('Investigation has been successfully saved in the system.').should('be.visible');
    }
}

export default new AddLabReportInvestigation();
