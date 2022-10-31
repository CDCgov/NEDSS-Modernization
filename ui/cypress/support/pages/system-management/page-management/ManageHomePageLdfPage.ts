import BasePage from '../../BasePage';
import EditHomePageLdfPage from './EditHomePageLdfPage';

enum Selector {
    LOCAL_FIELDS_TABLE = 'table[class=dtTable] tbody'
}
export default class ManageHomePageLdfPage extends BasePage {
    constructor() {
        super('/LDFLoad.do?page=Home%20Page&PageID=300&businessObjectNm=HOME&conditionCd=');
    }

    getLocalFieldLabels(): Cypress.Chainable<string[]> {
        return this.getElement(Selector.LOCAL_FIELDS_TABLE).then((tableBodyJquery) => {
            const rows = tableBodyJquery[0].getElementsByTagName('tr');
            const rowLabels: string[] = [];
            if (rows.length === 1 && rows[0].classList.contains('empty')) {
                return rowLabels;
            }
            for (let row of rows) {
                const text = (row.children[3] as HTMLTableCellElement).textContent;
                rowLabels.push(text ?? '');
            }
            return rowLabels;
        });
    }

    clickEditWithMatchingLabel(label: string): Cypress.Chainable<EditHomePageLdfPage> {
        return this.getLocalFieldLabels().then((labels) => {
            // get matching index
            const index = labels.findIndex((l) => l === label);
            return this.getElement(Selector.LOCAL_FIELDS_TABLE).then((tableBodyJquery) => {
                const rows = tableBodyJquery[0].getElementsByTagName('tr');
                rows[index].children[1].getElementsByTagName('a')[0].click();
                return cy.url(this.defaultOptions).then((url) => {
                    const id = url.substring(
                        url.indexOf('uiMetadataUid=') + 'uiMetadataUid='.length,
                        url.indexOf('#localField')
                    );
                    return new EditHomePageLdfPage(id);
                });
            });
        });
    }

    deleteAllLdfs(): Cypress.Chainable {
        return this.getElement(Selector.LOCAL_FIELDS_TABLE).then((tableBodyJquery) => {
            const rows = tableBodyJquery[0].getElementsByTagName('tr');
            if (rows.length === 1 && rows[0].classList.contains('empty')) {
                return;
            }
            for (let row of rows) {
                row.children[2].getElementsByTagName('a')[0].click();
            }
            return;
        });
    }
}
