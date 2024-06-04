class SearchResultsPage {
  get result() {
    return ".border-base-light.radius-md";
  }

  openProfile() {
    cy.get(this.result).find('a').click();
  }

  naviageToAddNewPatient() {
    cy.visit('/add-patient');
    cy.wait(500);
  }
}

export default new SearchResultsPage();
