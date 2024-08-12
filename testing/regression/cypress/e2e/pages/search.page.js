class SearchPage {

  enterLastName(lastName) {
    cy.get("#lastName").type(lastName);
  }

  enterFirstName(firstName) {
    cy.get("#firstName").type(firstName);
  }

  enterPatiendID(id) {
    cy.get("#id").type(id);
  }

  enterStreetAdreess(address) {
    cy.get("#address").type(address);
  }

  enterCity(city) {
    cy.get("#city").type(city,{force: true});
  }

  enterZipCode(zip) {
    cy.get("#zip").type(zip,{force: true});
    cy.get("#city").click({force: true});
  }

  enterStreetAddress(address) {
    cy.get("#address").type(address,{force: true});
  }

  enterPhone(phone) {
    cy.get("#homePhone").type(phone,{force: true});
  }

  enterEmail(email) {
    cy.get("#email").type(email, {force: true});
  }

  selectId() {
    cy.wait(500);
    let elem = "button[data-testid=accordionButton_5]";
    cy.get(elem).scrollIntoView();
    elem = "button[data-testid=accordionButton_4]";
    cy.get(elem).click();
    cy.wait(1000);
  }

  enterIdType(type) {
    const elem = "div[data-testid=accordionItem_4] select";
    cy.get(elem).scrollIntoView().select(type);
  }

  enterId(id) {
    if (id.length !== 0) {
      cy.get('input[id*=identificationNumber]').type(id);
    }
  }

  selectName() {
    cy.wait(500);
    let name = "label[for='lastName']";
    cy.get(name).click();
    cy.wait(1000);
  }

  selectRace() {
    cy.get('button[data-testid="accordionButton_5"]').click();
    cy.wait(1000);
  }

  enterEthnicity(type) {
    cy.get('div[data-testid="accordionItem_5"] label[for="ethnicity"]+select')
      .scrollIntoView()
      .select(type);
  }

  enterRace(type) {
    cy.get('div[data-testid="accordionItem_5"] label[for="race"]+select')
      .scrollIntoView()
      .select(type);
  }

  search() {
    cy.get('button').contains("Search").click();
    // cy.get('div.bottom-search button[type="submit"]').click();
    cy.wait(100);
  }

  verifySearchPage() {
    cy.get('div.bottom-search button[type="submit"]').should("be.visible");
  }

  closeErrorMsg() {
    cy.get(".usa-alert--error svg").click();
  }

  // selectState() {
  //   cy.wait(500);
  //   let elemt = "div[id='2'] select[placeholder='-Select-']";
  //   cy.get(elemt).scrollIntoView();
  //   elemt = "option[value='15']";
  //   cy.get(elemt).click();
  //   cy.wait(1000);
  // }

  selectState() {
    cy.get("div[id='2'] select[placeholder='-Select-']");
    cy.get("option[value='15']");
    cy.wait(500);
  }

  selectGender(gender) {
    cy.get("#gender");
    cy.get("#gender").select(gender);
    cy.wait(500);
  }

  selectDob() {
    cy.get("#dateOfBirth");
  }

  enterDob(dateOfBirth) {
    cy.get("#dateOfBirth").focus().clear();
    const cleanedDateOfBirth = dateOfBirth.replace(/\//g, "");
    cy.get("#dateOfBirth").type(cleanedDateOfBirth);
  }

  clearAll() {
    cy.contains('button', 'Clear all').click();
  }

  selectDelete() {
    cy.get('#record-status-active').focus().click( { force: true})
    cy.get('#record-status-deleted').focus().click({ force: true})
  }

  selectSuperseded() {
    cy.get('#record-status-active').focus().click( { force: true})
    cy.get('#record-status-deleted').focus().click({ force: true})
  }
}

export const searchPage = new SearchPage();
