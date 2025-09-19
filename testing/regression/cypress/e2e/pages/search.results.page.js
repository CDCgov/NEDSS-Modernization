class SearchResultsPage {
  get result() {
    return ".border-base-light.radius-md";
  }

  openProfile() {
    cy.get(this.result).find('a').click();
  }

  naviageToAddNewPatient() {
    cy.visit('/patient/add');
    cy.wait(500);
  }
}

export default new SearchResultsPage();
