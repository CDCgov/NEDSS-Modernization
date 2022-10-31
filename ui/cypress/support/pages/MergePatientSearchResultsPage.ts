import BasePage from './BasePage';
import PatientComparePage from './PatientComparePage';

enum Selector {
    SEARCH_RESULTS_TABLE = 'table[id=searchResultsTable] tbody',
    COMPARE_CHECKBOX = 'input[name=compare]',
    COMPARE_BUTTON = 'input[name=Compare]'
}
export default class MergePatientSearchResultsPage extends BasePage {
    constructor() {
        super('/FindPatient3.do?Mode1=ManualMerge');
    }

    public navigateTo(): void {
        throw new Error('Unable to navigate directly to search results page');
    }

    compareFirstTwo(): Cypress.Chainable<PatientComparePage> {
        return this.getElement(Selector.SEARCH_RESULTS_TABLE)
            .find(Selector.COMPARE_CHECKBOX, this.defaultOptions)
            .then((compareBoxes) => {
                compareBoxes[0].click();
                compareBoxes[1].click();
                // this opens in a new window. to prevent that we stub the window.open method
                return cy.window(this.defaultOptions).then((win) => {
                    cy.stub(win, 'open').as('open');

                    // trigger the method call
                    return this.clickFirst(Selector.COMPARE_BUTTON).then(() => {
                        // get the stubbed method
                        cy.get('@open', this.defaultOptions).then((open) => {
                            let stub = open as unknown as Cypress.Agent<sinon.SinonStub>;
                            // get the URL that was passed to window.open
                            const fullUrl = stub.getCalls()[0].args[0];
                            const relativeUrl = fullUrl.substring(fullUrl.indexOf('/ComparePatients2'));
                            return new PatientComparePage(relativeUrl);
                        });
                    });
                });
            });
    }
}
