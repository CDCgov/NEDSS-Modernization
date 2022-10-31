import BasePage from './BasePage';

enum Selector {
    RESULTS_TABLE = 'table[class=dtTable]'
}
export default class DocumentRequiringReviewPage extends BasePage {
    constructor() {
        super('/MyTaskList1.do?ContextAction=Review&initLoad=true&labReportsCount=1');
    }

    getResultsTable(): Cypress.Chainable {
        return this.getElement(Selector.RESULTS_TABLE);
    }
}
