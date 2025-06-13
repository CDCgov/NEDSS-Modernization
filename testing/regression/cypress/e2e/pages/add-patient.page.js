
import { faker } from "@faker-js/faker";

class AddPatientPage {
  clickViewPatientLink() {
    cy.get("a").contains("View patient").click();
    // cy.get(".usa-modal__main button").eq(1).click();
  }

  clickSumbitSaveButton() {
    cy.get(".add-patient-button").eq(2).click();
  }

  enterPaxName() {
    const randomFirstName = faker.person.firstName();
    const randomLastName = faker.person.lastName();
    const randomMiddleName = faker.person.middleName();

    cy.get("#lastName").type(randomLastName);
    cy.get("#firstName").type(randomFirstName);
    cy.get("#middleName").type(randomMiddleName);    
  }

  addSimplePatient() {
    this.enterPaxName();
    this.clickSumbitSaveButton();
  }

  addPatient() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");

    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  clearInformationAsOfDate() {
    cy.get('#asOf').clear();
  }

  addPatientBlank() {
    cy.get('button[class="usa-button add-patient-button"]').click();
    cy.get('#form-error').should('exist').and('have.text', 'You have some invalid inputs. Please correct the invalid inputs before moving forward.');
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

  addPatientOtherInformation() {    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");    
    cy.get("select[name=maritalStatus]").select("Married");    
    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  addPatientAddress() {
    const randomFirstStreet = faker.location.streetAddress();
    const randomLastStreet = faker.location.secondaryAddress();
    const randomCity = faker.location.city();

    cy.get("#streetAddress1").type(randomFirstStreet);
    cy.get("#streetAddress2").type(randomLastStreet);
    cy.get('input[id="location.city"]').type(randomCity);    
    cy.get("select[name=state]").select("California");
    cy.get("#zip").type("93501");    
    cy.get("select[name=country]").select("United States");

    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  viewPatientProfile() {
    cy.get('header h1')
    .should('be.visible')
    .and('contain.text', 'Patient profile');
  }

  viewPatientID(patientIDString) {
    cy.get('.common-card.patient-summary .border-bottom span')
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

    cy.get("select[name=suffix]").select("Esquire");    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();

    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  addPatientselectRace() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();

    this.clickSumbitSaveButton();

    this.clickViewPatientLink();
  }

  addPatientSelectTwoRace() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();

    this.clickSumbitSaveButton();
    this.clickViewPatientLink();
  }

  addPatientId_Identificatione() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");

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

    cy.get("select[name=suffix]").select("Esquire");    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");

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

    this.clickSumbitSaveButton();
    this.clickViewPatientLink();    
  }

  addPatientAddAnotherID() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");    
    cy.get("select[name=currentGender]").select("Female");    
    cy.get("select[name=birthGender]").select("Female");    
    cy.get("select[name=deceased]").select("No");

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
