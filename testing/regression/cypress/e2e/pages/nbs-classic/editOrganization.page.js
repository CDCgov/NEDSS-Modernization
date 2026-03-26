import { selectRandomDropdownValue } from "./utils";

class EditOrganizationPage {
  selectEditReason(reasonValue) {
    cy.get(`input[type="radio"][value="${reasonValue}"]`, { timeout: 10000 })
      .should('be.visible')
      .check({ force: true });
    cy.log(`Selected edit reason: ${reasonValue}`);
  }
  enterUniqueQuickCode() {
    cy.log('Entering unique quick code');
    
    // Generate a random 6-character alphanumeric string
    const quickCode = Math.random().toString(36).substring(2, 8).toUpperCase();
    cy.log(`Generated quick code: ${quickCode}`);
    
    // Find the Quick Code input field and type the generated code
    cy.get('input[name="quickCodeIdDT.rootExtensionTxt"]', { timeout: 10000 })
      .should('be.visible')
      .clear()
      .type(quickCode);
    
    // Store the generated quick code for later verification if needed
    cy.wrap(quickCode).as('generatedQuickCode');
  }
  selectRole(roleName) {
    cy.log(`Selecting role: ${roleName}`);
    cy.get('select#rolesList', { timeout: 10000 })
      .should('be.visible')
      .select(roleName);
  }

  selectRandomIdType() {
    selectRandomDropdownValue('select[fieldlabel="ID Type"]');
  }

  selectRandomAssigningAuthority() {
    selectRandomDropdownValue('select[header="Authority"]');
  }
  enterRandomIdValue() {
    cy.log('Entering random ID Value');
    
    // Generate a random string (8 characters alphanumeric)
    const randomId = Math.random().toString(36).substring(2, 10).toUpperCase();
    cy.log(`Random ID Value: ${randomId}`);
    
    // Find the ID Value textbox and type the random string
    cy.get('[id="organization.entityIdDT_s[i].rootExtensionTxt"]', { timeout: 10000 })
      .should('be.visible')
      .clear()
      .type(randomId);
    
    cy.wrap(randomId).as('generatedIdValue');
  }

  clickAddIdentificationButton() {
    cy.log('Clicking Add Identification button');
    
    // Click the Add Identification button
    cy.get('#BatchEntryAddButtonIdentification', { timeout: 10000 })
      .should('be.visible')
      .click();
    
    cy.log('Add Identification button clicked');
    
  }
  

  addNewIdEntry(idDetails) {
    cy.log(`Adding new ID entry with Type: ${idDetails.type}, Authority: ${idDetails.authority}`);
    
    // Scroll to Identification section
    cy.contains('Identification Information').scrollIntoView();
    
    // Click the Type dropdown arrow and select
    cy.get('[name="organization.entityIdDT_s[i].typeCd_button"]').click();
    cy.wait(500);
    cy.get('select[name="organization.entityIdDT_s[i].typeCd"]').select(idDetails.type, { force: true });
    
    // Click the Assigning Authority dropdown arrow and select
    cy.get('[name="organization.entityIdDT_s[i].assigningAuthorityCd_button"]').click()
    cy.wait(500);
    cy.get('select[name="organization.entityIdDT_s[i].assigningAuthorityCd"]').select(idDetails.authority, { force: true });
    
    cy.get('[id="organization.entityIdDT_s[i].rootExtensionTxt"]', { timeout: 10000 })
      .should('be.visible')
      .clear()
      .type(idDetails.idValue);
    this.clickAddIdentificationButton();
  }

  deleteExistingAddress() {
    cy.log('Deleting first address entry');
    
    // Scroll to Address section
    cy.contains('Address Information').scrollIntoView();
    
    // Find the first address entry in the history box and click its Delete link
    cy.get('#nestedElementsHistoryBox\\|Address tr', { timeout: 10000 })
      .first()
      .within(() => {
        cy.contains('Delete').click();
      });
    
    cy.wait(500); // Wait for the deletion to process
  }

  // Methods to add new contact information (telephone and address)

  addNewTelephoneEntry(telephoneDetails) {
    cy.log(`Adding new telephone: ${telephoneDetails.areaCode}-${telephoneDetails.prefix}-${telephoneDetails.lineNumber}`);
    
    // Scroll to Telephone section
    cy.contains('Telephone Information').scrollIntoView();
    
    // Click the Use dropdown arrow and select
    cy.get('[name="telephone[i].useCd_button"]').click();
    cy.wait(500);
    cy.get('select[name="telephone[i].useCd"]').select(telephoneDetails.use, { force: true });
    
    // Click the Type dropdown arrow and select
    cy.get('[name="telephone[i].cd_button"]').click();
    cy.wait(500);
    cy.get('select[name="telephone[i].cd"]').select(telephoneDetails.type, { force: true });
    
    // Find empty phone number fields and fill them
    cy.get('input[id*="phoneNbrTxt1"]').filter((i, el) => !el.value).first()
      .clear().type(telephoneDetails.areaCode);
    cy.get('input[id*="phoneNbrTxt2"]').filter((i, el) => !el.value).first()
      .clear().type(telephoneDetails.prefix);
    cy.get('input[id*="phoneNbrTxt3"]').filter((i, el) => !el.value).first()
      .clear().type(telephoneDetails.lineNumber);
    
    // Click Add Telephone button
    cy.get('#BatchEntryAddButtonTelephone').click();
    cy.wait(1000);
  }
  
  enterTelephoneUse(useValue) {
    cy.log(`Selecting telephone use: ${useValue}`);
    cy.get('select[name="telephone[i].useCd"]').select(useValue, { force: true });
  }

  enterTelephoneType(typeValue) {
    cy.log(`Selecting telephone type: ${typeValue}`);
    cy.get('select[name="telephone[i].cd"]').select(typeValue, { force: true });
  }

  enterTelephoneNumber(areaCode, prefix, lineNumber) {
    cy.log(`Entering telephone: ${areaCode}-${prefix}-${lineNumber}`);
    cy.get('input[id*="phoneNbrTxt1"]').clear().type(areaCode);
    cy.get('input[id*="phoneNbrTxt2"]').clear().type(prefix);
    cy.get('input[id*="phoneNbrTxt3"]').clear().type(lineNumber);
  }

  clickAddTelephoneButton() {
    cy.log('Clicking Add Telephone button');
    cy.get('#BatchEntryAddButtonTelephone', { timeout: 10000 })
      .should('be.visible')
      .click();
    cy.wait(500);
  }
 
  enterAddressUse(useValue) {
    cy.log(`Selecting address use: ${useValue}`);
    cy.get('select[name="address[i].useCd"]').select(useValue, { force: true });
  }

  enterAddressType(typeValue) {
    cy.log(`Selecting address type: ${typeValue}`);
    cy.get('select[name="address[i].cd"]').select(typeValue, { force: true });
  }

  enterStreetAddress1(address) {
    cy.log(`Entering street address: ${address}`);
    cy.get('input[name="address[i].thePostalLocatorDT_s.streetAddr1"]')
      .should('be.visible')
      .clear()
      .type(address);
  }

  enterCity(city) {
    cy.log(`Entering city: ${city}`);
    cy.get('input[name="address[i].thePostalLocatorDT_s.cityDescTxt"]')
      .should('be.visible')
      .clear()
      .type(city);
  }

  enterZipCode(zip) {
    cy.log(`Entering zip code: ${zip}`);
    cy.get('input[name="address[i].thePostalLocatorDT_s.zipCd"]')
      .should('be.visible')
      .clear()
      .type(zip);
  }

  clickAddAddressButton() {
    cy.log('Clicking Add Address button');
    cy.get('#BatchEntryAddButtonAddress', { timeout: 10000 })
      .should('be.visible')
      .click();
    cy.wait(500);
  }


}

export default new EditOrganizationPage();