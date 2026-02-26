class LoadManageSummaryPage {
  openDataEntryLink = 'a[href="/nbs/LoadNavbar.do?ContextAction=DataEntry"]'
  summaryLink = 'a[href="/nbs/LoadManageSummary1.do?ContextAction=GlobalSummaryData"]'

  clickOpenDataEntry() {
    cy.get(this.openDataEntryLink).first().click();
  }

  verifySummaryLink() {
    cy.get(this.summaryLink).contains('Summary Data').should('be.visible');
  }

  clickSummaryLink() {
    cy.get(this.summaryLink).first().click();
  }

  verifySummaryPage() {
    cy.contains('Manage Summary Notifications').should('be.visible');
  }

  selectMMWRYear(year) {
    cy.get('input[name="MMWRYear_textbox"]').clear().type(year);
    cy.get('input[value="Get Summary Reports"]').click();
  }

  verifyMMWRWeekOptions(count) {
    cy.get('select[name="MMWRWeek"]').find('option').should('have.length', count);
  }
}

export const loadManageSummaryPage = new LoadManageSummaryPage();
