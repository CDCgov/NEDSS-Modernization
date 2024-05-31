import { faker } from "@faker-js/faker";

class AddPatientPage {
  enterPaxName() {
    const randomFirstName = faker.person.firstName();
    const randomLastName = faker.person.lastName();
    const randomMiddleName = faker.person.middleName();

    cy.get("#lastName").type(randomLastName);
    cy.get("#firstName").type(randomFirstName);
    cy.get("#middleName").type(randomMiddleName);
    cy.wait(1000);
  }

  addSimplePatient() {
    this.enterPaxName();

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatient() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .eq(1)
    //   .type('10/23/1980');
    cy.wait(1500);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientBlank() {
    cy.get('button[class="usa-button add-patient-button"]').click();
    cy.get(".warning").should("be.visible");
  }

  addPatientSingleDetail() {
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .first()
    //   .type(this.getCurrentDate());
    cy.get(".add-patient-button").click();
    cy.wait(500)
    cy.get(".usa-modal__main button").eq(1).click();
    cy.wait(500)
  }

  addPatientSingleDeteNextYear() {
    cy.get("input[data-testid=date-picker-external-input]")
      .first()
      .clear()
      .type(this.getNextYearDate());
    cy.get(".add-patient-button").click();
    cy.get(".usa-modal__main button").eq(1).click();
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

    cy.get(".add-patient-button").click();
    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientAndDelete() {
    cy.get(".usa-button.delete-btn.display-inline-flex").click();
    cy.get(".usa-button.padding-105.text-center").click();
  }

  addPatientOtherInformation() {
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .first()
    //   .type(this.getCurrentDate());
    // cy.get("input[data-testid=date-picker-external-input]")
    // .eq(1)
    // .type('10/23/1980');
    cy.wait(1000);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");
    cy.wait(1000);
    cy.get("select[name=maritalStatus]").select("Married");
    cy.wait(1000);
    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientAddress() {
    const randomFirstStreet = faker.location.streetAddress();
    const randomLastStreet = faker.location.secondaryAddress();
    const randomCity = faker.location.city();

    // cy.get("input[data-testid=date-picker-external-input]")
    //   .first()
    //   .type(this.getCurrentDate());
    cy.get("#streetAddress1").type(randomFirstStreet);
    cy.get("#streetAddress2").type(randomLastStreet);
    cy.get("#city").type(randomCity);
    cy.wait(1000);
    cy.get("select[name=state]").select("California");
    cy.get("#zip").type("93501");
    cy.wait(1000);
    cy.get("select[name=country]").select("United States");

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  viewPatientProfile() {
    cy.get("button[class*=successModal]").click();
  }

  addAnotherPatient() {
    cy.get(".usa-button.usa-button--outline").click();
  }

  addPatientselectEthnicity() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .eq(1)
    //   .type('10/23/1980');
    cy.wait(1000);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientselectRace() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .eq(1)
    //   .type('10/23/1980');
    cy.wait(1000);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientSelectTwoRace() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .eq(1)
    //   .type('10/23/1980');
    cy.wait(1000);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientId_Identificatione() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .eq(1)
    //   .type('10/23/1980');
    cy.wait(1000);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();

    cy.wait(1000);
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Medicare number");

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientAssigningAuthority_Identificatione() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .eq(1)
    //   .type('10/23/1980');
    cy.wait(1000);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();
    cy.wait(1000);
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Medicare number");
    cy.wait(1000);
    cy.get(
      "select[placeholder='-Select-'][name='identification[0].authority']"
    ).select("CO");

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }

  addPatientAddAnotherID() {
    this.enterPaxName();

    cy.get("select[name=suffix]").select("Esquire");
    // cy.get("input[data-testid=date-picker-external-input]")
    //   .eq(1)
    //   .type('10/23/1980');
    cy.wait(1000);
    cy.get("select[name=currentGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=birthGender]").select("Female");
    cy.wait(1000);
    cy.get("select[name=deceased]").select("No");

    cy.get("label[for='2135-2']").click();
    cy.get("label[for='1002-5']").click();
    cy.get("label[for='2028-9']").click();
    cy.get("label[for='2106-3']").click();

    cy.wait(1000);
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Medicare number");
    cy.wait(1000);
    cy.get(
      "select[placeholder='-Select-'][name='identification[0].authority']"
    ).select("CO");
    cy.wait(1000);
    cy.get(
      "div[class='grid-col-12 padding-x-3 padding-bottom-3 padding-top-0'] button[type='button']"
    ).click();
    cy.wait(1000);
    cy.get(
      "body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > section:nth-child(8) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > select:nth-child(2)"
    ).select("Social Security");

    cy.get(".add-patient-button").click();

    cy.get(".usa-modal__main button").eq(1).click();
  }
}
export default new AddPatientPage();
