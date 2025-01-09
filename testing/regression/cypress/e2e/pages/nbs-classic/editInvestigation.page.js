class ClassicEditOpenInvestigationPage {

  openInvestigationLink = 'a[href="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true"]'
  queueTitle = 'a[name="pageTop"]'

  clickOpenInvestigation() {
    cy.get(this.openInvestigationLink).first().click();
  }

  verifyQueuePage() {
    cy.get(this.queueTitle).contains('Open Investigations Queue').should('be.visible');
  }

  verifyInvestigation() {
    cy.get("table#parent td>a").eq(3).click();
    cy.get("a").contains("View Investigation:");
  }

  clickEditBtnInOpenInvestigation() {
    cy.contains("Edit").click()
  }

  editCommentInOpenInvestigation() {
    cy.get("#DEM196").type("Comments")
  }

  clickSubmitBtnOpenInvestigation() {
    cy.get("#SubmitTop").click()
  }
}
export default new ClassicEditOpenInvestigationPage();

