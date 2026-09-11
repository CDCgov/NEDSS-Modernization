class SearchEventPage {
    clickEventInvestigation() {
        cy.get('a[href*="search/investigations"]').click();
        cy.wait(500);
    }

    expandInvestigationCriteria() {
        cy.get('svg[aria-label="Collapse General search"]').first().click();
        cy.get('svg[aria-label="Expand Investigation criteria"]').first().click();
        cy.wait(500);
    }

    expandLabReportCriteria() {
        cy.get('svg[aria-label="Collapse General search"]').first().click();
        cy.get('svg[aria-label="Expand Lab report criteria"]').first().click();
        cy.wait(500);
    }

    clickEventLabReport() {
        cy.get('a[href*="search/lab-reports"]').click();
        cy.wait(500);
    }

    selectEventInvestigationCondition() {
        cy.get('#conditions').click();
        cy.get('#react-select-2-option-1').click();
        cy.get('.multi-select__control--is-focused ').click();
    }

    selectEventLabReportProgramArea() {
        cy.get('#react-select-2-placeholder').click({ force: true });
        cy.get('#react-select-2-option-2').click();
        cy.get('.multi-select__control--is-focused ').click();
    }

    selectEventInvestigationProgramArea() {
        cy.get('#react-select-3-placeholder').click({ force: true });
        cy.get('#react-select-3-option-2').click({ force: true });
    }

    selectEventInvestigationJurisdiction() {
        cy.get('#react-select-4-placeholder').click({ force: true });
        cy.get('#react-select-4-option-2').click({ force: true });
    }

    selectEventLabReportJurisdiction() {
        cy.get('#react-select-3-placeholder').click({ force: true });
        cy.get('#react-select-3-option-3').click({ force: true });
    }

    selectPregnancy() {
        cy.get('select[name="pregnancyStatus"]').select('No');
    }

    selectInvestigationCreatedBy() {
        cy.get('input[id="createdBy"]').type('super');
        cy.get('li[class="usa-combo-box__list-option"]').first().click({ multiple: true });
    }

    selectInvestigationEventDate() {
        cy.get('select[name="eventDate.type"]').select('Last update date');
        cy.wait(500);
        cy.get('input[id="eventDate.from"]').type('090920022');
        cy.get('input[id="eventDate.to"]').type('08142024');
    }

    selectLabReportEventDate() {
        cy.get('select[name="eventDate.type"]').select('Last Update Date');
        cy.wait(500);
        cy.get('input[data-testid="date-picker-external-input"]').first().type('090920022');
        cy.get('input[data-testid="date-picker-external-input"]').last().type('08142024');
    }

    selectInvestigationEventType() {
        cy.get("select[name='identification.type']").select('Investigation ID');
        cy.get('input[id="identification.value"]').type('CAS10000000GA01');
    }

    selectLabReportEventType() {
        cy.get('select[name="identification.type"]').select('Accession Number');
        cy.get('input[id="identification.value"]').type('2');
        cy.get('label[for="eventStatus__checkbox__NEW"]').click();
    }

    selectLabReportEntryMethod() {
        cy.get('label[for="entryMethods__checkbox__ELECTRONIC"]').click();
    }

    selectLabReportEnteredByMethod() {
        cy.get('label[for="enteredBy__checkbox__EXTERNAL"]').click();
    }

    selectLabReportEventStatus() {
        cy.get('label[for="eventStatus__checkbox__NEW"]').click();
    }

    selectLabReportProcessStatus() {
        cy.get('label[for="processingStatus__checkbox__UNPROCESSED"]').click();
    }

    search() {
        cy.get('button').contains('Search').click();
        cy.wait(100);
        cy.get('header button').eq(2).click();
    }

    selectInvestigationUpdatedBy() {
        cy.get('input[id="updatedBy"]').type('super');
        cy.get('li[class="usa-combo-box__list-option"]').first().click({ multiple: true });
    }

    selectInvestigationFacility() {
        cy.get('#reportingFacilityId').type('p');
        cy.get('li[class="usa-combo-box__list-option"]').first().click({ multiple: true });
    }

    selectInvestigationProvider() {
        cy.get('#reportingProviderId').type('c');
        cy.get('li[class="usa-combo-box__list-option"]').first().click({ multiple: true });
    }

    selectLabReportProvider() {
        cy.get('input[name="orderingProvider"]').type('Alex');
    }

    selectLabReportFacility() {
        cy.get('input[name="orderingFacility"]').type('c');
        cy.get('li[class="usa-combo-box__list-option"]').first().click({ multiple: true });
    }

    selectInvestigationStatus() {
        cy.get('select[name="investigationStatus"]').select('Closed');
    }

    selectInvestigationOutbreakName() {
        cy.get('div[id="react-select-5-placeholder"]').click({ force: true });
        cy.get('div[id="react-select-5-option-0"]').click({ force: true });
    }

    selectInvestigationCaseStudy() {
        cy.get('div[id="react-select-6-placeholder"]').click({ force: true });
        cy.get('div[id="react-select-6-option-0"]').click({ force: true });
    }

    selectInvestigationCurrentProcessingStatus() {
        cy.get('div[id="react-select-7-placeholder"]').click({ force: true });
        cy.get('div[id="react-select-7-option-6"]').click({ force: true });
    }

    selectInvestigationNotificationStatus() {
        cy.get('div[id="react-select-8-placeholder"]').click({ force: true });
        cy.get('div[id="react-select-8-option-4"]').click({ force: true });
    }

    selectLabReportResultTest() {
        this.setLabReportNormalSettings();
    }

    setLabReportNormalSettings() {
        cy.get('label[for=processingStatus__checkbox__UNPROCESSED]').click();
        cy.get('label[for=eventStatus__checkbox__NEW]').click();
        cy.get('label[for=entryMethods__checkbox__ELECTRONIC]').click();
        cy.get('label[for=enteredBy__checkbox__INTERNAL]').click();
        cy.get('label[for=enteredBy__checkbox__EXTERNAL]').click();
    }

    selectLabReportCodedResult() {
        this.setLabReportNormalSettings();
    }
}

export const searchEventPage = new SearchEventPage();
