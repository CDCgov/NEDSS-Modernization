import addAddressModule from "./profile/addAddress.module";
import addCommentModule from "./profile/addComment.module";
import addNameModule from "./profile/addName.module";
import addPhoneModule from "./profile/addPhone.module";
import addIdentificationModule from "./profile/addIdentification.module";
import addRaceModule from "./profile/addRace.module";
import editGeneralInfoModule from "./profile/editGeneralInfo.module";
import editEthnicityModule from "./profile/editEthnicity.module";
import editSexBirthModule from "./profile/editSexBirth.module";
import editMortalityModule from "./profile/editMortality.module";

class PatientProfilePage {
  isDisplayed() {
    cy.get(".patient-summary").should("be.visible");
  }

  print() {
    cy.get("button").contains("Delete patient").invoke("removeAttr", "target").click({ force: true });
    cy.wait(1000);
  }

  setPatientProfileENVID() {
    cy.url().then((url) => {      
      const urlParts = url.split('/');
      const patientId = urlParts[urlParts.length - 2];
      Cypress.env('patientId', patientId);
      cy.log(patientId);
    });
  }


  isDemographicPageDisplayed() {
    cy.wait(1000);

    // cy.window().then((win) => {
    //   cy.wait(1000);
    //   cy.window({ timeout: 10000 }).should((newWin) => {
    //     expect(newWin).to.not.equal(win);
    //     win = newWin;
    //   });
    // });
  }

  delete() {
    cy.get("button").contains("Delete patient").wait(2000).click({ force: true });
  }

  confirmDelete() {
    cy.get("footer[class=usa-modal__footer] button").eq(1).click();
  }

  cancelDelete() {
    cy.get("footer[class=usa-modal__footer] button").eq(0).click();
  }

  navigatePatinet() {
    cy.get("a.margin-0.font-sans-md").click();
  }

  backToTop() {
    cy.contains("button", "Back to top").scrollIntoView();
    cy.window().its("scrollY").should("be.greaterThan", 0);
    cy.contains("button", "Back to top").click();
  }

  clickOnTab(tabName) {
    cy.intercept("POST", "/graphql").as("graphqlRequest");
    cy.contains("button", tabName).click();
    cy.wait("@graphqlRequest");
  }

  clickOnButton(buttonName) {
    cy.contains("button", buttonName).scrollIntoView().click();
  }

  addComments() {
    addCommentModule.date().comment().add();
  }

  isCommentSuccessfullyAdded() {
    addCommentModule.isSuccessfullyAdded();
  }

  addName(fName) {
    addNameModule.date().type().first(fName).last().add();
    cy.wait("@graphqlRequest");
  }

  isNameAdded(fName) {
    addNameModule.isSuccessfullyAdded();
  }

  addAddress(address1) {
    addAddressModule
      .date()
      .type()
      .use()
      .street1(address1)
      .city()
      .state()
      .comment()
      .add();
  }

  isAddressAdded() {
    addAddressModule.isSuccessfullyAdded();
  }

  isPopupClosed() {
    addAddressModule.isPopupClosed();
  }

  addPhoneNumber(number) {
    addPhoneModule
      .date()
      .type()
      .use()
      .countryCode()
      .phoneNumber(number)
      .email()
      .comment()
      .add();
  }

  isPhoneNumberAdded() {
    addPhoneModule.isSuccessfullyAdded();
  }

  addIdentification(idNumber) {
    addIdentificationModule
      .date()
      .type()
      .id(idNumber)
      .issuedState()
      .add();
  }

  isIdAdded() {
    addIdentificationModule.isSuccessfullyAdded()
  }

  addRace(detRace) {
    addRaceModule.date().race().detailRace(detRace).add();
  }

  isRaceAdded(detRace) {
    addRaceModule.isRaceAdded(detRace);
  }

  clickOnEdit(section) {
    let len = "";
    switch (section) {
      case "General patient information":
        len = 0;
        break;
      case "Ethnicity":
        len = 2;
        break;
      case "Mortality":
        len = 1;
        break;
      case "Sex & birth":
        len = 3;
        break;
    }
    cy.contains(section).scrollIntoView();
    cy.wait("@graphqlRequest");
    cy.get("button.usa-button.grid-row").eq(len).click();
  }

  editGeneralInfo(info) {
    editGeneralInfoModule.date().motherName(info).save();
  }

  isGeneralInformationAdded(momName) {
    editGeneralInfoModule.isGeneralInformationAdded(momName);
  }

  editEthnicity(ethnicity) {
    editEthnicityModule.date().ethnicity(ethnicity).save();
  }

  isEthnicityAdded(ethnicity) {
    editEthnicityModule.isEthnicityAdded(ethnicity);
  }

  editSexBirth(currentSex) {
    editSexBirthModule.date().currentSex(currentSex).save();
  }

  isCurrentSexAdded(currentSex) {
    editSexBirthModule.isCurrentSexAdded(currentSex);
  }

  editMortality(mortality) {
    editMortalityModule.date().mortality(mortality).save();
  }

  isMortalityAdded(mortality) {
    editMortalityModule.isMortalityAdded(mortality);
  }

  addPatientInvestigations() {
    cy.get("button[aria-labelledby=events-tabpanel]").click();
    cy.get('button', { timeout: 10000 }).contains('Add investigation').click();
    cy.get("input[name='ccd_textbox']").type('Acute flaccid myelitis');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
    cy.get("#tabs0head1").click();
    cy.get("img[name='INV107_button']").type('Cobb County');
    cy.get("#SubmitTop").click();
  }

  addPatientInvestigations_NovelCoronavirus() {
    cy.get("button[aria-labelledby=events-tabpanel]").click();
    cy.get('button', { timeout: 10000 }).contains('Add investigation').click();
    cy.get("input[name='ccd_textbox']").type('2019 Novel Coronavirus');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
    cy.get("#tabs0head1").click();
    cy.get("img[name='INV107_button']").type('Cobb County');
    cy.get("#SubmitTop").click();
  }

  addPatientInvestigations_AcanthamoebaDisease() {
    cy.get("h6.false.padding-bottom-1.type.text-normal.margin-y-3.font-sans-md.margin-x-3").click();
    cy.get("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > button:nth-child(2)").click();
    cy.get("input[name='ccd_textbox']").type('Acanthamoeba Disease (Excluding Keratitis)');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
    cy.get("#tabs0head1").click();
    cy.get("img[name='INV107_button']").type('Cobb County');
    cy.get("#SubmitTop").click();
  }

  addPatientInvestigations_AfricanTickBite() {
    cy.get("h6.false.padding-bottom-1.type.text-normal.margin-y-3.font-sans-md.margin-x-3").click();
    cy.get("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > button:nth-child(2)").click();
    cy.get("input[name='ccd_textbox']").type('African Tick Bite Fever');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
    cy.get("img[name='proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd_button']").type('Cobb County');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
  }

  addPatientInvestigations_AIDS() {
    cy.get("h6.false.padding-bottom-1.type.text-normal.margin-y-3.font-sans-md.margin-x-3").click();
    cy.get("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > button:nth-child(2)").click();
    cy.get("input[name='ccd_textbox']").type('AIDS');
    cy.get("div[id='botProcessingDecisionId'] input[value='Submit']").click();
    cy.get("img[name='referralBasis_button']").type('A1 - Associate 1');
    cy.get("img[name='reviewReason_button']").type('Deceased');
    cy.get("div[id='botProcessingDecisionId'] input[value='Submit']").click();
    cy.get("#tabs0head1").click();
    cy.get("img[name='INV107_button']").type('Cobb County');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
  }

  addPatientInvestigations_Amebiassis() {
    cy.get("h6.false.padding-bottom-1.type.text-normal.margin-y-3.font-sans-md.margin-x-3").click();
    cy.get("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > button:nth-child(2)").click();
    cy.get("input[name='ccd_textbox']").type('Amebiassis');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
    cy.get("img[name='proxy.publicHealthCaseVO_s.thePublicHealthCaseDT.jurisdictionCd_button']").type('Cobb County');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
  }

  addPatientInvestigations_AnaplasmaPhagocytophilum() {
    cy.get("h6.false.padding-bottom-1.type.text-normal.margin-y-3.font-sans-md.margin-x-3").click();
    cy.get("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > button:nth-child(2)").click();
    cy.get("input[name='ccd_textbox']").type('Anaplasma phagocytophilum');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
    cy.get("#tabs0head1").click();
    cy.get("img[name='INV107_button']").type('Cobb County');
    cy.get("#SubmitTop").click();
  }
}

export default new PatientProfilePage();
