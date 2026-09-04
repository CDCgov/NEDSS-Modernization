class ClassicOrganizationPage {
    // Navigation methods
    navigateToClassicOrganizationSearchPane() {
        cy.contains('Data Entry').eq(0).click();
        cy.contains('Organization').eq(0).click();
    }

    // Search methods
    enterOrganizationNameInSearch(name: string) {
        cy.get('input[id="organizationSearch.nmTxt"]').type(name);
    }

    enterOrganizationAddressInSearch(address: string) {
        cy.get('input[id="organizationSearch.streetAddr1"]').type(address);
    }

    enterUniqueOrganizationNameInSearch() {
        const timestamp = Date.now();
        const uniqueName = `random string ${timestamp}`;
        cy.log(`Using unique organization name: ${uniqueName}`);
        cy.wrap(uniqueName).as('orgName');
        cy.get('input[id="organizationSearch.nmTxt"]').type(uniqueName);
        cy.wrap(uniqueName).as('createdOrgName');
    }

    enterSameOrganizationNameInSearchPane() {
        // Retrieve the name from the alias and use it
        cy.get<string>('@createdOrgName').then((orgName) => {
            cy.log(`Using stored organization name in search pane: ${orgName}`);
            cy.enterInput('input[id="organizationSearch.nmTxt"]', orgName);
        });
    }

    saveSearchResultsCount() {
        cy.log('Saving search results count');

        // Get the text from the red result message
        cy.get('div.boldTenDkRed', { timeout: 10000 })
            .should('be.visible')
            .invoke('text')
            .then((text) => {
                // Extract the number from text like "resulted in 3 possible matches"
                const match = text.match(/resulted in (\d+) possible matches/);
                const count = match ? parseInt(match[1]) : 0;

                cy.log(`Found ${count} search results`);

                // Save to alias for later use
                cy.wrap(count).as('searchResultsCount');
            });
    }

    // Add/Edit methods

    clickEditButton() {
        cy.get('input[name="Edit"]').eq(0).click();
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

    // Verification methods
    verifySearchResultsContain(expectedText: string) {
        cy.log(`Verifying search results include: ${expectedText}`);
        cy.get('#searchResultsTable', { timeout: 10000 }).should('be.visible').and('contain', expectedText);
    }

    verifySearchResultContainsGeneratedQuickCode() {
        cy.log('Verifying search result contains generated quick code');
        cy.get<string>('@generatedQuickCode').then((quickCode) => {
            cy.log(`Looking for quick code: ${quickCode}`);

            cy.get('#searchResultsTable', { timeout: 10000 }).should('be.visible').and('contain', quickCode);
        });
    }

    verifySearchResultsContainAddress(expectedAddress: string) {
        cy.log(`Verifying search results include address: ${expectedAddress}`);
        cy.get('#searchResultsTable', { timeout: 10000 }).should('be.visible').and('contain', expectedAddress);
    }

    verifySearchResultsContainTelephone(expectedTelephone: string) {
        cy.log(`Verifying search results include telephone: ${expectedTelephone}`);
        cy.get('#searchResultsTable', { timeout: 10000 }).should('be.visible').and('contain', expectedTelephone);
    }

    verifySearchResultsContainIdTypeAndValue(expectedType: string, expectedValue: string) {
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
        cy.get('div.boldTenDkRed a').should('be.visible').and('contain', 'refine your search?');
        cy.get('#searchResultsTable').should('contain', 'There is no information to display');
    }

    verifyOrganizationWasCreated() {
        cy.get<string>('@createdOrgName').then((orgName) => {
            cy.log(`Verifying search results include: ${orgName}`);
            cy.get('#searchResultsTable', { timeout: 10000 }).should('be.visible').and('contain', orgName);
        });
    }
    verifySuccessMessage() {
        cy.url().should('include', 'ViewOrganization');
        (cy.get('body').should('contain', 'created') as any).or('contain', 'added').or('contain', 'successful');
    }

    verifySearchResultsCountIncreasedBy(additionalCount: number) {
        cy.log(`Verifying search results count increased by ${additionalCount}`);

        // Get the previously saved count
        cy.get<number>('@searchResultsCount').then((savedCount) => {
            const expectedCount = savedCount + additionalCount;

            cy.log(
                `Saved count: ${savedCount}, Expected: ${expectedCount} (${additionalCount > 0 ? '+' : ''}${additionalCount})`
            );

            // Check the current count from the red text
            cy.get('div.boldTenDkRed', { timeout: 10000 })
                .should('be.visible')
                .invoke('text')
                .then((text) => {
                    const match = text.match(/resulted in (\d+) possible matches/);
                    const currentCount = match ? parseInt(match[1]) : 0;

                    cy.log(`Current count: ${currentCount}`);
                    expect(currentCount).to.equal(expectedCount);
                });
        });
    }

    // Administrative Information verification
    verifyQuickCode(expectedValue: string) {
        cy.log(`Verifying Quick Code: ${expectedValue}`);
        cy.get('#test').should('contain', expectedValue);
    }

    verifyStandardIndustryClass(expectedValue: string) {
        cy.log(`Verifying Standard Industry Class: ${expectedValue}`);
        cy.get('[id="organization.theOrganizationDT.standardIndustryClassCd"]').should('contain', expectedValue);
    }

    verifyRole(expectedRole: string) {
        cy.log(`Verifying Role: ${expectedRole}`);
        cy.get('#rolesList').should('contain', expectedRole);
    }

    // Name verification
    verifyOrganizationName(expectedName: string) {
        cy.log(`Verifying Organization Name: ${expectedName}`);
        cy.get('#name\\.nmTxt').should('contain', expectedName);
    }

    // Identification Information verification
    verifyIdentificationType(expectedType: string) {
        cy.log(`Verifying Identification Type: ${expectedType}`);
        cy.get('#nestedElementsHistoryBox\\|Identification tr')
            .first()
            .find('td')
            .eq(1)
            .should('contain', expectedType);
    }

    verifyIdentificationAuthority(expectedAuthority: string) {
        cy.log(`Verifying Identification Authority: ${expectedAuthority}`);
        cy.get('#nestedElementsHistoryBox\\|Identification tr')
            .first()
            .find('td')
            .eq(2)
            .should('contain', expectedAuthority);
    }

    verifyIdentificationValue(expectedValue: string) {
        cy.log(`Verifying Identification Value: ${expectedValue}`);
        cy.get('#nestedElementsHistoryBox\\|Identification tr')
            .first()
            .find('td')
            .eq(3)
            .should('contain', expectedValue);
    }

    // Address Information verification
    verifyAddressUse(expectedUse: string) {
        cy.log(`Verifying Address Use: ${expectedUse}`);
        cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(1).should('contain', expectedUse);
    }

    verifyStreetAddress(expectedAddress: string) {
        cy.log(`Verifying Street Address: ${expectedAddress}`);
        cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(2).should('contain', expectedAddress);
    }

    verifyCity(expectedCity: string) {
        cy.log(`Verifying City: ${expectedCity}`);
        cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(3).should('contain', expectedCity);
    }

    verifyState(expectedState: string) {
        cy.log(`Verifying State: ${expectedState}`);
        cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(4).should('contain', expectedState);
    }

    verifyZip(expectedZip: string) {
        cy.log(`Verifying Zip: ${expectedZip}`);
        cy.get('#nestedElementsHistoryBox\\|Address tr').first().find('td').eq(5).should('contain', expectedZip);
    }

    // Telephone Information verification
    verifyTelephoneUse(expectedUse: string) {
        cy.log(`Verifying Telephone Use: ${expectedUse}`);
        cy.get('#nestedElementsHistoryBox\\|Telephone tr').first().find('td').eq(1).should('contain', expectedUse);
    }

    verifyTelephoneType(expectedType: string) {
        cy.log(`Verifying Telephone Type: ${expectedType}`);
        cy.get('#nestedElementsHistoryBox\\|Telephone tr').first().find('td').eq(2).should('contain', expectedType);
    }

    verifyTelephoneNumber(expectedNumber: string) {
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
