
import { faker } from "@faker-js/faker";

class AddPatientPage {
  clickViewPatientLink() {
    cy.get("a").contains("View patient").click();
    // cy.get(".usa-modal__main button").eq(1).click();
  }

  clickSumbitSaveButton() {
    cy.get("button").contains("Save").click();
  }

  enterPaxName() {
    const randomFirstName = faker.person.firstName();
    const randomLastName = faker.person.lastName();
    const randomMiddleName = faker.person.middleName();

    cy.get('input[id="name.last"]').type(randomLastName);
    cy.get('input[id="name.first"]').type(randomFirstName);
    cy.get('input[id="name.middle"]').type(randomMiddleName);    
  }

  addSimplePatient() {
    this.enterPaxName();
    this.clickSumbitSaveButton();
  }

  addPatient() {
    this.enterPaxName();

    cy.get('select[id="name.suffix"]').select("Esquire");    
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");        
    
    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  clearInformationAsOfDate() {
    // cy.get('#asOf').clear();
    cy.get('input[id="administrative.asOf"]').clear();
    
  }

  addPatientBlank() {
    // cy.get('button[class="usa-button add-patient-button"]').click();

    this.clickSumbitSaveButton();
    cy.get('span[id="administrative.asOf-error"]').should('exist').and('have.text', 'The Information as of date is required.');
    // cy.get('#form-error').should('exist').and('have.text', 'You have some invalid inputs. Please correct the invalid inputs before moving forward.');
  }

  addPatientSingleDetail() {
    this.clickSumbitSaveButton();
    cy.wait(500)
    this.clickViewPatientLink();
    cy.wait(500)
  }

  addPatientSingleDeteNextYear() {
    cy.get("input[data-testid=date-picker-external-input]")
      .first()
      .clear()
      .type(this.getNextYearDate());
    this.clickSumbitSaveButton();
    this.clickViewPatientLink();
  }

  getCurrentDate() {
    const today = new Date();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate()).padStart(2, "0");
    const year = today.getFullYear();
    return `${month}/${day}/${year}`;
  }

  getNextYearDate() {
    const today = new Date();
    const month = String(today.getMonth() + 1).padStart(2, "0");
    const day = String(today.getDate() + 2).padStart(2, "0");
    const year = today.getFullYear() + 1;
    return `${month}/${day}/${year}`;
  }

  addPatientNameSpecial() {
    this.enterPaxName();

    this.clickSumbitSaveButton();
    this.clickViewPatientLink();
  }

  addPatientAndDelete() {
    cy.get(".usa-button.delete-btn.display-inline-flex").click();
    cy.get(".usa-button.padding-105.text-center").click();
  }

  addPatientOtherInformation() {    ;    
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");
    cy.get('select[id="personalDetails.maritalStatus"]').select("Married");
    
    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  addPatientAddress() {
    const randomFirstStreet = faker.location.streetAddress();
    const randomLastStreet = faker.location.secondaryAddress();
    const randomCity = faker.location.city();
    
    cy.get('input[id="address.address1"]').type(randomFirstStreet);
    cy.get('input[id="address.address2"]').type(randomLastStreet);
      
    cy.get('input[id="address.city"]').type(randomCity);    
    cy.get('select[id="address.state"]').select("California");
    cy.get('input[id="address.zipcode"]').type("93501");
    cy.get('select[id="address.county"]').select("United States");              
    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  viewPatientProfile() {
    cy.get('nav')
    .should('be.visible')
    .and('contain.text', 'Patient file');
  }

  viewPatientID(patientIDString) {
    cy.get('header span')
    .contains(patientIDString)
    .should('be.visible');
  }

  clickViewPatientProfile() {
    this.clickViewPatientLink();
  }

  addAnotherPatient() {
    cy.get(".usa-button.usa-button--outline").click();
  }

  addPatientselectEthnicity() {
    this.enterPaxName();
       
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");

    cy.get("label[for='2135-2']").click();

    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  addPatientselectRace() {
    this.enterPaxName();

    cy.get('select[id="name.suffix"]').select("Esquire");    
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();

    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  addPatientSelectTwoRace() {
    this.enterPaxName();

    cy.get('select[id="name.suffix"]').select("Esquire");    
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();

    this.clickSumbitSaveButton();
    this.clickViewPatientLink();
  }

  addPatientId_Identificatione() {
    this.enterPaxName();

    cy.get('select[id="name.suffix"]').select("Esquire");    
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();
    
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Medicare number");

    this.clickSumbitSaveButton();
    this.clickViewPatientLink();    
  }

  addPatientAssigningAuthority_Identificatione() {
    this.enterPaxName();

    cy.get('select[id="name.suffix"]').select("Esquire");    
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");

    
    cy.get('select[id="ethnicityRace.ethnicity"]').select("Hispanic or Latino");
    // cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();    
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Medicare number");    
    cy.get(
      "select[placeholder='-Select-'][name='identification[0].authority']"
    ).select("CO");

    this.clickSumbitSaveButton();
    this.clickViewPatientLink();    
  }

  addPatientAddAnotherID() {
    this.enterPaxName();

    cy.get('select[id="name.suffix"]').select("Esquire");    
    cy.get('select[id="personalDetails.currentSex"]').select("Female");    
    cy.get('select[id="personalDetails.birthSex"]').select("Female");        
    cy.get('select[id="personalDetails.deceased"]').select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();
    
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Medicare number");    
    cy.get(
      "select[placeholder='-Select-'][name='identification[0].authority']"
    ).select("CO");    
    cy.get(
      "div[class='grid-col-12 padding-x-3 padding-bottom-3 padding-top-0'] button[type='button']"
    ).click();    
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Social Security");

    this.clickSumbitSaveButton();
    this.clickViewPatientLink();    
  }
}
export default new AddPatientPage();
