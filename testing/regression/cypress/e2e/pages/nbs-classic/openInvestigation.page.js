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
  }
  
  export const openInvestigationPage = new OpenInvestigationPage();
  