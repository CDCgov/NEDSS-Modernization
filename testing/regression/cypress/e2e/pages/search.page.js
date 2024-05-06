class SearchPage {
  selectBasicInfo() {
    cy.get('button[data-testid="accordionButton_1"]').click();
  }  

  clickEventInvestigation() {
    cy.get('a[href*="advanced-search/event"]').click()
    cy.wait(500);
    let elm = cy.get("div[data-testid='accordionItem_event-type-section'] select");
    elm.select('Investigation');
    cy.wait(500);
  }  

  selectEventInvestigationCondition() {    
    let elm = cy.get("#conditionInput").click();
    let elm2 = cy.get("#react-select-3-option-1").click();
    cy.get('.multi-select__control--is-focused ').click();
  }

  eventSearch() {
    let elm = cy.get('button[data-testid="search"]').click(({force: true}));
    cy.wait(500);
    
  }

  enterLastName(lastName) {
    cy.get("#lastName").type(lastName);
  }

  enterFirstName(firstName) {
    cy.get("#firstName").type(firstName);
  }

  enterPatiendID(id) {
    cy.get("#id").type(id);
  }

  selectAddress() {
    cy.get('button[data-testid="accordionButton_2"]').click();
  }

  enterStreetAdreess(address) {
    cy.get("#address").type(address);
  }

  enterCity(city) {
    cy.get("#city").type(city);
  }

  enterZipCode(zip) {
    cy.get("#zip").type(zip);
    cy.get("#city").click();
  }

  enterStreetAddress(address) {
    cy.get("#address").type(address);
  }

  selectContact() {
    cy.get("button[data-testid=accordionButton_3]").click();
  }

  enterPhone(phone) {
    cy.get("#phoneNumber").type(phone);
  }

  enterEmail(email) {
    cy.get("#email").type(email);
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
    cy.get('div.bottom-search button[type="submit"]').click();
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

  selectState() {
    cy.get("div[id='2'] select[placeholder='-Select-']");
    cy.get("option[value='15']");
    cy.wait(500);
  }

  selectGender() {
    cy.get("#gender");
    cy.get("#gender").select("Male");
    cy.wait(500);
  }

  selectDob() {
    cy.get("#dateOfBirth");
  }

  enterDob(dateOfBirth) {
    const cleanedDateOfBirth = dateOfBirth.replace(/\//g, "");
    cy.get("#dateOfBirth").type(cleanedDateOfBirth);
  }
}

export const searchPage = new SearchPage();
