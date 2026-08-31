class SearchPage {
    enterLastName(lastName) {
        cy.get('input[name="name.last"]').type(lastName);
    }

    enterFirstName(firstName) {
        cy.get('input[name="name.first"]').type(firstName);
    }

    enterPatiendID(id) {
        cy.get('#id').type(id);
    }

    enterCity(city) {
        cy.get('input[id="location.city"]').type(city, { force: true });
    }

    enterZipCode(zip) {
        cy.get('#zip').type(zip, { force: true });
        cy.get('input[id="location.city"]').click({ force: true });
    }

    enterStreetAddress(address) {
        cy.get('input[name="location.street"]').type(address, { force: true });
    }

    enterPhone(phone) {
        cy.get('#homePhone').type(phone, { force: true });
    }

    enterEmail(email) {
        cy.get('#email').type(email, { force: true });
    }

    selectId() {
        cy.get('svg').eq(4).click();
    }

    clickPatientId(patientId) {
        cy.get('a[href="/patient/' + patientId + '"]').click();
        cy.url().should('include', '/' + patientId + '/summary');
    }

    enterIdType(type) {
        cy.get('#identificationType').select(type);
    }

    enterId(id) {
        if (id.length !== 0) {
            cy.get('input[name*=identification]').type(id);
        }
    }

    selectName() {
        cy.wait(500);
        const name = "label[for='lastName']";
        cy.get(name).click();
        cy.wait(1000);
    }

    selectRace() {
        cy.get('svg').eq(5).click();
        cy.wait(500);
    }

    enterEthnicity(type) {
        cy.get('#ethnicity').select(type);
    }

    enterRace(type) {
        cy.get('#race').select(type);
    }

    search() {
        cy.get('button').contains('Search').click();
        cy.wait(100);
    }

    closeErrorMsg() {
        cy.get('.usa-alert--error svg').click();
    }

    selectState(string) {
        cy.get("select[name='state']").select(string);
        cy.wait(500);
    }

    selectGender(gender) {
        cy.get('#gender').select(gender);
        cy.wait(500);
    }

    enterDob(dateOfBirth) {
        const [month, day, year] = dateOfBirth.split('/');
        cy.enterInput('#bornOn-exact-date-month', month);
        cy.enterInput('#bornOn-exact-date-day', day);
        cy.enterInput('#bornOn-exact-date-year', year);
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
        cy.wait(500);
        cy.get('svg').eq(2).click();
    }

    verifyTableColumns() {
        const expectedColumns = [
            'Patient ID',
            'Patient name',
            'DOB/Age',
            'Current sex',
            'Address',
            'Phone',
            'ID',
            'Email',
        ];

        expectedColumns.forEach((column) => {
            cy.contains('#patient-search-results thead th', column);
        });
    }
}

export const searchPage = new SearchPage();
