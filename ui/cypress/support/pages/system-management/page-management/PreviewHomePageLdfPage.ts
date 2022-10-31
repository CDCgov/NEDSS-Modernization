import BasePage from '../../BasePage';
import ManageHomePageLdfPage from './ManageHomePageLdfPage';

enum Selector {
    LDF_SECTION = 'table[id=ldfSubsection]'
}
export default class PreviewHomePageLdfPage extends BasePage {
    constructor() {
        super('/LocalFields.do?method=previewLDF');
    }

    public navigateTo(): void {
        const managePage = new ManageHomePageLdfPage();
        managePage.navigateTo();
        cy.visit(this.relativeUrl, this.defaultOptions);
    }

    getLdfSection(): Cypress.Chainable {
        return this.getElement(Selector.LDF_SECTION);
    }
}
