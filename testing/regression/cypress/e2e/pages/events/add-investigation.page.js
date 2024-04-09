class AddInvestigationPage {
  add() {
    cy.get("input[name=ccd_textbox]").type("Diphtheria");
    cy.get("#Submit").first().click();
    cy.wait(500);
    cy.get(".boldNineYellow").eq(1).click();
    cy.get("input[name*=jurisdictionCd_textbox]").type("Fulton County");
    cy.get("#Submit").first().click();
  }
}

export default new AddInvestigationPage();
