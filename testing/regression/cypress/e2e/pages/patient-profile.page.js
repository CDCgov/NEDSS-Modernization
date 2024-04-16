import patientProfilePage from "./pages";

class patientProfilePage {
  addPatientInvestigations() {
    cy.get(".active.padding-bottom-1.type.text-normal.margin-y-3.font-sans-md.margin-x-3.cursor-pointer.margin-top-2.margin-bottom-0").click();
    cy.get("body > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > form:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > button:nth-child(2)").click();
    cy.get("input[name='ccd_textbox']").select('Acute flaccid myelitis');
    cy.get("body > table:nth-child(2) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(1) > table:nth-child(1) > thead:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(4) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > input:nth-child(1)").click();
    cy.get("#tabs0head1").click();
    cy.get("img[name='INV107_button']").select('Cobb County');
    cy.get("#SubmitTop").click();
  }

}
export default new patientProfilePage();
