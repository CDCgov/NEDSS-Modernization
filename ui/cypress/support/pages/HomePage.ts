import BasePage from './BasePage';
import DocumentRequiringReviewPage from './DocumentsRequiringReviewPage';

enum Selector {
    MY_QUEUES = 'div[id=myQueues]'
}
export enum Queue {
    OPEN_INVESTIGATIONS = 'Open Investigations',
    REJECTED_NOTIFICATIONS_QUEUE = 'Rejected Notifications Queue',
    DOCUMENTS_REQUIRING_REVIEW = 'Documents Requiring Review'
}
export default class HomePage extends BasePage {
    constructor() {
        super('/HomePage.do?method=loadHomePage');
    }

    clickDocumentsRequiringReview(): Cypress.Chainable<DocumentRequiringReviewPage> {
        return this.getElement(Selector.MY_QUEUES)
            .contains(Queue.DOCUMENTS_REQUIRING_REVIEW)
            .click(this.defaultOptions)
            .then(() => {
                return new DocumentRequiringReviewPage();
            });
    }
}
