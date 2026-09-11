class OpenInvestigationPage {
    openInvestigationLink = 'a[href="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true"]';
    queueTitle = 'a[name="pageTop"]';
    nextLink = 'a[href*="d-446288-p=2"]';
    previousLink = 'a[href*="d-446288-p=1"]';
    sortMenu = 'img.multiSelect[title="Filter Select"]';
    confirmedOption = 'input[name="answerArray(CASESTATUS)"][value="C"]';
    probableOption = 'input[name="answerArray(CASESTATUS)"][value="P"]';
    cancelButton = '#b2';
    removeFiltersLink = 'font.hyperLink';
    sortedResult = '#parent > tbody > :nth-child(1) > :nth-child(7)';
    clickOpenInvestigationsQueue = 'a[href="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true"]';
    searchArrow = 'img#queueIcon';
    investigationIdField = 'input#SearchText2';
    okButton = 'input#b2SearchText2';
    conditionNameLink = 'a[onclick*="InvestigationID"]';
    patientNameLink = 'a[onclick*="MPRUid"]';
    manageAssociationsButton = 'input[id="manageAssociations"]';
    treatmentDateLink = 'a[href*="ViewTreatment"]';
    treatmentDateInput = 'input[id="NBS465"]';
    treatmentInput = 'input[name="NBS481_textbox"]';
    quickCodeLookupInput = 'input[name="pageClientVO.answer(NBS475)"]';
    providerQuickCodeLookupBtn = 'input[id="NBS475CodeLookupButton"]';
    addTreatmentBtn = 'input[value="Add Treatment"]';
    editButton = 'input#Edit';
    treatmentCommentsField = 'textarea#treatmentVO\\.theTreatmentDT\\.txt';
    submitButton = 'input#Submit';
    updatedComment = 'p';
    homeNavigation = 'a[href="/nbs/HomePage.do?method=loadHomePage"]';

    clickOpenInvestigation() {
        cy.contains('a', 'Open Investigations').click();
    }

    verifyQueuePage() {
        cy.get(this.queueTitle).contains('Open Investigations Queue').should('be.visible');
    }

    clickNext() {
        cy.get(this.nextLink).first().click();
    }

    clickHome() {
        cy.get(this.homeNavigation).click();
    }

    clickPrevious() {
        cy.get(this.previousLink).first().click();
    }

    openSortMenu() {
        cy.get(this.sortMenu).eq(5).click();
        cy.get('.multiSelectOptions').should('be.visible');
        cy.wait(1000);
    }

    clickSelectAll() {
        cy.get('label:contains("Select All")').eq(4).should('be.visible').click();
    }

    selectConfirmed() {
        cy.get(this.confirmedOption).check();
    }

    deselectConfirmed() {
        cy.get(this.confirmedOption).uncheck();
    }

    selectProbable() {
        cy.get(this.probableOption).check();
    }

    clickOk() {
        cy.get(this.okButton).click({ force: true });
    }

    clickCancel() {
        cy.get(this.cancelButton).click({ force: true });
    }

    clickRemoveFilters() {
        cy.get(this.removeFiltersLink).eq(0).click();
    }

    verifyNoSortingChanges() {
        cy.get(this.sortedResult).contains('Confirmed').should('be.visible');
    }

    verifInvestigation() {
        cy.get('table#parent td>a').eq(1).click();
        cy.get('a').contains('View Investigation:');
    }

    openInvestigationsQueue() {
        cy.get(this.clickOpenInvestigationsQueue).eq(1).click();
    }

    searchForInvestigation(investigationId: string) {
        cy.get(this.searchArrow).eq(7).click();
        cy.get(this.investigationIdField).eq(0).type(investigationId);
        cy.get(this.okButton).click();
    }

    clickConditionName() {
        cy.get(this.conditionNameLink).eq(0).click();
    }

    clickManageAssociations() {
        cy.get(this.manageAssociationsButton).eq(0).click();
    }

    clickTreatmentDate() {
        cy.get(this.treatmentDateLink).click();
    }

    clickEditButton() {
        cy.get(this.editButton).eq(0).click();
    }

    enterTreatmentComments(comment: string) {
        cy.enterInput(this.treatmentCommentsField, comment);
    }

    clickSubmitButton() {
        cy.get(this.submitButton).eq(0).click();
    }

    verifyUpdatedComment(expectedComment: string) {
        cy.get(this.updatedComment).should('contain.text', expectedComment);
    }

    clickPatientName() {
        cy.get(this.patientNameLink).eq(0).click();
    }

    clickEventsTab() {
        cy.contains('Events').click();
    }

    clickAddInvestigationBtn() {
        cy.get('button[type="button"]').contains('Add investigation').eq(0).click();
    }

    selectConditionFromDropdown(conditionName: string) {
        cy.get('input[name="ccd_textbox"]').type(conditionName);
    }

    clickSubmitBtnInSelectConditionPage() {
        /**
         * Waiting for the answers to load completely to ensure that any following steps
         * (i.e. selecting jurisdiction and case status) aren't interrupted from the rendering
         * following the response.  Previously, tests would fail because the `Case Info` tab would
         * be clicked prior to the response resolving; once the response did resolve, the user would
         * be abruptly brought back to the default `Patient` tab.
         */
        cy.intercept('POST', '/nbs/dwr/call/plaincall/JInvestigationForm.getMMWRFromDB.dwr').as('answersResponse');
        cy.get('#Submit').eq(0).click();
        cy.wait('@answersResponse', { timeout: 10000 });
    }

    clickCaseInfoTab() {
        cy.contains('Case Info').eq(0).click();
    }

    selectJurisdictionFromDropdown() {
        cy.get('input[name*=jurisdictionCd_textbox]').type('Fulton County');
    }

    enterTreatmentDate() {
        cy.get(this.treatmentDateInput).type('01/20/2020');
    }

    enterProviderQuickCodeLookup() {
        cy.get(this.quickCodeLookupInput).type('1');
    }

    selectTreatmentFromDropdown() {
        cy.get(this.treatmentInput).type('Valacyclovir, 500 mg, PO, QD');
    }

    clickProviderQuickCodeLookupBtn() {
        cy.get(this.providerQuickCodeLookupBtn).click();
    }

    clickAddTreatmentBtn() {
        cy.get(this.addTreatmentBtn).click();
    }

    selectCaseStatusFromDropdown() {
        cy.get('input[name="proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.caseClassCd_textbox"]').type('Confirmed');
    }

    clickSubmitBtnInAddInvestigationPage() {
        cy.get('#SubmitBottom').eq(0).click();
    }

    clickCreateInvestigationsBtn() {
        cy.window().then((win) => {
            cy.stub(win, 'open').callsFake((url) => {
                win.location.href = url;
            });
        });
        cy.get('input[name="Create Notifications"]').eq(0).click();
    }

    clickSubmitBtnInCreateNotificationPage() {
        cy.get('#topcreatenotId input[type="button"][value="Submit"]').eq(0).click({ force: true });
    }

    verifyMorbidityReportAssociation() {
        const eventId = Cypress.env('morbidityEventId');
        cy.log('event ID is {}', eventId);

        // Find the second table in the Botulism tab (the Morbidity Reports table)
        cy.get('#tabControlN108A0')
            .find('table.TableInner')
            .eq(1) // Second table (index 1)
            .contains(eventId)
            .should('exist');
    }
}

export const openInvestigationPage = new OpenInvestigationPage();
