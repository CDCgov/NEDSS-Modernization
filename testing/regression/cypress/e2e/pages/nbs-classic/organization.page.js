
class ClassicOrganizationPage {

  // Navigation methods
  navigateToClassicOrganizationSearchPane() {
    cy.contains('Data Entry').eq(0).click()
    cy.contains('Organization').eq(0).click()
  }

  // Search methods
  enterOrganizationNameInSearch(name) {
    cy.get('input[id="organizationSearch.nmTxt"]').type(name)
  }

  enterOrganizationAddressInSearch(address) {
    cy.get('input[id="organizationSearch.streetAddr1"]').type(address)
  }

  enterUniqueOrganizationNameInSearch() {
    const timestamp = Date.now();
    const uniqueName = `random string ${timestamp}`;
    cy.log(`Using unique organization name: ${uniqueName}`);
    cy.wrap(uniqueName).as('orgName');
    cy.get('input[id="organizationSearch.nmTxt"]').type(uniqueName)
    cy.wrap(uniqueName).as('createdOrgName');
  }

  enterSameOrganizationNameInSearchPane() {
  // Retrieve the name from the alias and use it
    cy.get('@createdOrgName').then((orgName) => {
      cy.log(`Using stored organization name in search pane: ${orgName}`);
      cy.get('input[id="organizationSearch.nmTxt"]')
        .should('be.visible')
        .clear()
        .type(orgName);
    });
  }
  // Add/Edit methods

  clickEditButton() {
    cy.get('input[name="Edit"]').eq(0).click()
  }

    /**
   * Click the Inactivate button on the View Organization page
   */
  clickInactivateButton() {
    cy.log('Clicking Inactivate button');
    cy.get('#Inactivate').eq(0).click();
  }
  acceptConfirmationPopup() {
    cy.log('Accepting confirmation popup');
    
    // Cypress automatically handles native browser dialogs
    // This event listener will click OK on the confirm popup
    cy.on('window:confirm', () => {
      cy.log('Confirmation popup accepted');
      return true; // true clicks OK, false would click Cancel
    });
  }

  // Navigation from search results to view
  clickViewOnSearchResults() {
    cy.log('Clicking View on search results');
    cy.contains('View').eq(0).click();
  }

  verifySearchResultsCount(expectedCount) {
    cy.log(`Verifying search results message shows ${expectedCount} possible matches`);
    
    // Check the red text div for the expected count
    cy.get('div.boldTenDkRed', { timeout: 10000 })
      .should('be.visible')
      .and('contain', `resulted in ${expectedCount} possible matches`);
  }
  
  // Verification methods
  verifySearchResultsContain(expectedText) {
    cy.log(`Verifying search results include: ${expectedText}`);
    cy.get('#searchResultsTable', { timeout: 10000 })
      .should('be.visible')
      .and('contain', expectedText);
  }

  verifySearchResultContainsGeneratedQuickCode() {
    cy.log('Verifying search result contains generated quick code');
    
    cy.get('@generatedQuickCode').then((quickCode) => {
      cy.log(`Looking for quick code: ${quickCode}`);
      
      cy.get('#searchResultsTable', { timeout: 10000 })
        .should('be.visible')
        .and('contain', quickCode);
    });
  }

  verifySearchResultsContainAddress(expectedAddress) {
    cy.log(`Verifying search results include address: ${expectedAddress}`);
    cy.get('#searchResultsTable', { timeout: 10000 })
      .should('be.visible')
      .and('contain', expectedAddress);
  }

  verifySearchResultsContainTelephone(expectedTelephone) {
    cy.log(`Verifying search results include telephone: ${expectedTelephone}`);
    cy.get('#searchResultsTable', { timeout: 10000 })
      .should('be.visible')
      .and('contain', expectedTelephone);
  }

  verifySearchResultsContainIdTypeAndValue(expectedType, expectedValue) {
    cy.log(`Verifying search results include ID Type: ${expectedType} with ID Value: ${expectedValue}`);
    cy.get('#searchResultsTable', { timeout: 10000 })
      .should('be.visible')
      .and('contain', expectedType)
      .and('contain', expectedValue);
  }

  verifyNoResultsFound() {
    cy.log('Verifying no results message');
    cy.get('div.boldTenDkRed', { timeout: 10000 })
      .should('be.visible')
      .and('contain', 'resulted in 0 possible matches');
    cy.get('div.boldTenDkRed a')
      .should('be.visible')
      .and('contain', 'refine your search?');
    cy.get('#searchResultsTable')
      .should('contain', 'There is no information to display');
  }

  verifyOrganizationWasCreated() {
    cy.get('@createdOrgName').then((orgName) => {
      cy.log(`Verifying search results include: ${orgName}`);
      cy.get('#searchResultsTable', { timeout: 10000 })
        .should('be.visible')
        .and('contain', orgName);
    });

  }
  verifySuccessMessage() {
    cy.url().should('include', 'ViewOrganization');
    cy.get('body').should('contain', 'created')
      .or('contain', 'added')
      .or('contain', 'successful');
  }

  // Administrative Information verification
  verifyQuickCode(expectedValue) {
    cy.log(`Verifying Quick Code: ${expectedValue}`);
    cy.get('#test').should('contain', expectedValue);
  }

  verifyStandardIndustryClass(expectedValue) {
    cy.log(`Verifying Standard Industry Class: ${expectedValue}`);
    cy.get('[id="organization.theOrganizationDT.standardIndustryClassCd"]').should('contain', expectedValue);
  }

  verifyRole(expectedRole) {
    cy.log(`Verifying Role: ${expectedRole}`);
    cy.get('#rolesList').should('contain', expectedRole);
  }

  // Name verification
  verifyOrganizationName(expectedName) {
    cy.log(`Verifying Organization Name: ${expectedName}`);
    cy.get('#name\\.nmTxt').should('contain', expectedName);
  }

  // Identification Information verification
  verifyIdentificationType(expectedType) {
    cy.log(`Verifying Identification Type: ${expectedType}`);
    cy.get('#nestedElementsHistoryBox\\|Identification tr').first().find('td').eq(1).should('contain', expectedType);
  }

  verifyIdentificationAuthority(expectedAuthority) {
    cy.log(`Verifying Identification Authority: ${expectedAuthority}`);
    cy.get('#nestedElementsHistoryBox\\|Identification tr').first().find('td').eq(2).should('contain', expectedAuthority);
  }

  verifyIdentificationValue(expectedValue) {
    cy.log(`Verifying Identification Value: ${expectedValue}`);
    cy.get('#nestedElementsHistoryBox\\|Identification tr').first().find('td').eq(3).should('contain', expectedValue);
  }

  // Address Information verification
  verifyAddressUse(expectedUse) {
    cy.log(`Verifying Address Use: ${expectedUse}`);
    cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(1).should('contain', expectedUse);
  }

  verifyStreetAddress(expectedAddress) {
    cy.log(`Verifying Street Address: ${expectedAddress}`);
    cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(2).should('contain', expectedAddress);
  }

  verifyCity(expectedCity) {
    cy.log(`Verifying City: ${expectedCity}`);
    cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(3).should('contain', expectedCity);
  }

  verifyState(expectedState) {
    cy.log(`Verifying State: ${expectedState}`);
    cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(4).should('contain', expectedState);
  }

  verifyZip(expectedZip) {
    cy.log(`Verifying Zip: ${expectedZip}`);
    cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(5).should('contain', expectedZip);
  }

  // Telephone Information verification
  verifyTelephoneUse(expectedUse) {
    cy.log(`Verifying Telephone Use: ${expectedUse}`);
    cy.get('#nestedElementsHistoryBox\\|Telephone tr').first().find('td').eq(1).should('contain', expectedUse);
  }

  verifyTelephoneType(expectedType) {
    cy.log(`Verifying Telephone Type: ${expectedType}`);
    cy.get('#nestedElementsHistoryBox\\|Telephone tr').first().find('td').eq(2).should('contain', expectedType);
  }

  verifyTelephoneNumber(expectedNumber) {
    cy.log(`Verifying Telephone Number: ${expectedNumber}`);
    cy.get('#nestedElementsHistoryBox\\|Telephone tr').first().find('td').eq(3).should('contain', expectedNumber);
  }

  // Combined verification method for all organization details
  verifyAllOrganizationDetails() {
    cy.log('Verifying all organization details');
    
    // Administrative Information
    this.verifyQuickCode('1');
    this.verifyStandardIndustryClass('Health Care and Social Assistance');
    this.verifyRole('Hospital');
    
    // Name
    this.verifyOrganizationName('Piedmont Hospital');
    
    // Identification Information
    this.verifyIdentificationType('Organization identifier');
    this.verifyIdentificationAuthority('CMS Provider');
    this.verifyIdentificationValue('CMS1234');
    
    // Address Information
    this.verifyAddressUse('Primary Work Place');
    this.verifyStreetAddress('1968 Peachtree Road NW');
    this.verifyCity('Atlanta');
    this.verifyState('Georgia');
    this.verifyZip('30056');
    
    // Telephone Information
    this.verifyTelephoneUse('Primary Work Place');
    this.verifyTelephoneType('Phone');
    this.verifyTelephoneNumber('404-605-5000');
  }

}

export default new ClassicOrganizationPage();