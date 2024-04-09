class SearchResultsPage {
  get result() {
    return ".border-base-light.radius-md";
  }

  openProfile() {
    cy.get(this.result).find('a').click();
  }

  naviageToAddNewPatient() {
    cy.get('button.add-patient-button').click();
    cy.get('#basic-nav-section-one button').first().click();
  }
}

export default new SearchResultsPage();
