import BasePage from './BasePage';

enum Selector {
    RESULTS_TABLE = 'table[id=parent] tbody'
}
export default class DrugSearchResultsPage extends BasePage {
    constructor() {
        super('');
    }

    public navigateTo(): void {
        throw new Error('Unable to navigate directly to search results');
    }

    getResultsTable(): Cypress.Chainable {
        return this.getElement(Selector.RESULTS_TABLE);
    }
}
