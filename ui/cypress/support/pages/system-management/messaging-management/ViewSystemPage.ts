import BasePage from '../../BasePage';

enum Selector {
    SYSTEM_DETAILS_TABLE_ROWS = 'table[id=subsec1] tbody tr'
}
export default class ViewSystemPage extends BasePage {
    constructor(systemId: string) {
        super(`/ReceivingSystem.do?method=viewRecFacility&exportReceivingFacilityUid=${systemId}#expAlg`);
    }

    private getRowContent(index: number): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.SYSTEM_DETAILS_TABLE_ROWS)
            .eq(index, this.defaultOptions)
            .find('td', this.defaultOptions)
            .eq(1, this.defaultOptions);
    }

    getReportType(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(1);
    }

    getDisplayName(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(2);
    }

    getApplicationName(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(3);
    }

    getApplicationOid(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(4);
    }

    getFacilityName(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(5);
    }

    getFacilityOid(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(6);
    }

    getFacilityDescription(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(7);
    }

    getSendingSystem(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(8);
    }

    getReceivingSystem(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(9);
    }

    getAllowsTransfer(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(10);
    }

    getUseSystemDerivedJursidiction(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(11);
    }

    getAdministrativeComments(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getRowContent(13);
    }
}
