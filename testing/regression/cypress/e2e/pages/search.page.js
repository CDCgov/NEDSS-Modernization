class SearchPage {

  enterLastName(lastName) {
    cy.get('input[name="name.last"]').type(lastName);
  }

  enterFirstName(firstName) {
    cy.get('input[name="name.first"]').type(firstName);
  }

  enterPatiendID(id) {
    cy.get("#id").type(id);
  }

  enterCity(city) {
    cy.get('input[id="location.city"]').type(city,{force: true});
  }

  enterZipCode(zip) {
    cy.get("#zip").type(zip,{force: true});
    cy.get('input[id="location.city"]').click({force: true});
  }

  enterStreetAddress(address) {
    cy.get('input[name="location.street"]').type(address,{force: true});
  }

  enterPhone(phone) {
    cy.get("#homePhone").type(phone,{force: true});
  }

  enterEmail(email) {
    cy.get("#email").type(email, {force: true});
  }

  selectId() {
    cy.get('svg[aria-label="Collapse Basic information"]').first().click()
    cy.get('svg[aria-label="Expand ID"]').first().click()
    cy.wait(500);
  }

  enterIdType(type) {
    const elem = "#identificationType";
    cy.get(elem).scrollIntoView().select(type);
  }

  enterId(id) {
    if (id.length !== 0) {
      cy.get('input[name*=identification]').type(id);
    }
  }

  selectName() {
    cy.wait(500);
    let name = "label[for='lastName']";
    cy.get(name).click();
    cy.wait(1000);
  }

  selectRace() {
    cy.get('svg[aria-label="Collapse Basic information"]').first().click()
    cy.get('svg[aria-label="Expand Race/Ethnicity"]').first().click()
    cy.wait(500);
    cy.wait(1000);
  }

  enterEthnicity(type) {
    cy.get('#ethnicity')
      .scrollIntoView()
      .select(type);
  }

  enterRace(type) {
    cy.get('#race')
      .scrollIntoView()
      .select(type);
  }

  search() {
    cy.get('button').contains("Search").click();    
    cy.wait(100);
    cy.get('button').contains("List").click();
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

  selectState(string) {
    cy.get("select[name='state']").select(string);    
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
    const [month, day, year] = dateOfBirth.split('/');
    cy.get("#bornOn-exact-date-month").focus().clear();
    cy.get("#bornOn-exact-date-day").focus().clear();
    cy.get("#bornOn-exact-date-year").focus().clear();
    cy.get("#bornOn-exact-date-month").type(month);
    cy.get("#bornOn-exact-date-day").type(day);
    cy.get("#bornOn-exact-date-year").type(year);
  }

  clearAll() {
    cy.contains('button', 'Clear all').click();
  }

  selectDelete() {
    cy.get('label[for="status__checkbox__ACTIVE"]').click({ force: true });
    cy.get('label[for="status__checkbox__LOG_DEL"]').click({ force: true });
    
  }

  selectSuperseded() {
    cy.get('label[for="status__checkbox__ACTIVE"]').click({ force: true });
    cy.get('label[for="status__checkbox__SUPERCEDED"]').click({ force: true });

  }

  clickAddressTab() {    
    cy.get('summary').contains("Address").click();
  }
}

export const searchPage = new SearchPage();
