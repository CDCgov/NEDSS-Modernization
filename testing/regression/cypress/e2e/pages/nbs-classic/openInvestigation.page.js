class OpenInvestigationPage {
    openInvestigationLink = 'a[href="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true"]';
    queueTitle = 'a[name="pageTop"]';
    nextLink = 'a[href*="d-446288-p=2"]';
    previousLink = 'a[href*="d-446288-p=1"]';
    sortMenu = 'img.multiSelect[title="Filter Select"]';
    selectAllCheckbox = '.selectAll';
    confirmedOption = 'input[name="answerArray(CASESTATUS)"][value="C"]';
    probableOption = 'input[name="answerArray(CASESTATUS)"][value="P"]';
    okButton = '#b1';
    cancelButton = '#b2';
    removeFiltersLink = 'font.hyperLink';
    sortedResult = '#parent > tbody > :nth-child(1) > :nth-child(7)';
    clickOpenInvestigationsQueue = 'a[href="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true"]';
    searchArrow = 'img#queueIcon';
    investigationIdField = 'input#SearchText2';
    okButton = 'input#b2SearchText2';
    conditionNameLink = 'a[onclick*="InvestigationID"]';
    manageAssociationsButton = 'input#manageAssociations';
    treatmentDateLink = 'a[href*="ViewTreatment"]';
    editButton = 'input#Edit';
    treatmentCommentsField = 'textarea#treatmentVO\\.theTreatmentDT\\.txt';
    submitButton = 'input#Submit';
    updatedComment = 'p';
  
    clickOpenInvestigation() {
      cy.get(this.openInvestigationLink).first().click();
    }
  
    verifyQueuePage() {
      cy.get(this.queueTitle).contains('Open Investigations Queue').should('be.visible');
    }
  
    clickNext() {
      cy.get(this.nextLink).first().click();
    }
  
    clickPrevious() {
      cy.get(this.previousLink).first().click();
    }
  
    openSortMenu() {
       cy.get(this.sortMenu).eq(5).click();
       cy.get('.multiSelectOptions').should('be.visible');
       cy.wait(1000)
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
      cy.get(this.okButton).click({force: true});
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
      cy.get("table#parent td>a").eq(1).click();
      cy.get("a").contains("View Investigation:");
    }
  
    openInvestigationsQueue() {
      cy.get(this.clickOpenInvestigationsQueue).eq(1).click();
    }
    
    searchForInvestigation(investigationId) {
      cy.get(this.searchArrow).eq(7).click();
      cy.get(this.investigationIdField).eq(0).type(investigationId);
      cy.get(this.okButton).click();
    }
  
    clickConditionName() {
      cy.get(this.conditionNameLink).click();
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
  
    enterTreatmentComments(comment) {
      cy.get(this.treatmentCommentsField).clear().type(comment);
    }
  
    clickSubmitButton() {
      cy.get(this.submitButton).eq(0).click();
    }
  
    verifyUpdatedComment(expectedComment) {
      cy.get(this.updatedComment).should('contain.text', expectedComment);
    }

    clickPatientName() {
        cy.get('#parent tbody tr td a').eq(2).click()
    }

    clickEventsTab() {
        cy.contains('Events').eq(0).click()
    }

    clickAddInvestigationBtn() {
        cy.contains('button', 'Add investigation').eq(0).click()
    }

    selectConditionFromDropdown() {
        cy.get('img[name="ccd_button"]').eq(0).click()
        cy.wait(1000)
        cy.get('#ccd').select(1, { force: true })
        cy.get('input[name="ccd_textbox"]').eq(0).click()
        cy.get('#ccd').select(1, { force: true })
    }

    clickSubmitBtnInSelectConditionPage() {
        cy.get('#Submit').eq(0).click()
    }

    clickCaseInfoTab() {
        cy.contains('Case Info').eq(0).click()
    }

    selectJurisdictionFromDropdown() {
        cy.get('img[name="INV107_button"]').eq(0).click()
        cy.get('#INV107').select(1, { force: true })
        cy.get('input[name="INV107_textbox"]').eq(0).click()
    }

    selectCaseStatusFromDropdown() {
        cy.get('img[name="INV163_button"]').eq(0).click()
        cy.get('#INV163').select(1, { force: true })
        cy.get('input[name="INV163_textbox"]').eq(0).click()
        cy.get('#INV886').eq(0).click()
    }

    clickSubmitBtnInAddInvestigationPage() {
        cy.get('#SubmitBottom').eq(0).click()
    }

    clickCreateInvestigationsBtn() {
        cy.window().then((win) => {
         cy.stub(win, 'open').callsFake((url) => {
           win.location.href = url;
         });
       });
       cy.get('#createNoti').eq(0).click()
    }

    clickSubmitBtnInCreateNotificationPage() {
        cy.get('#topcreatenotId input[type="button"][value="Submit"]').eq(0).click({ force: true })
    }
  }
  
  export const openInvestigationPage = new OpenInvestigationPage();
  