import BasePage from './BasePage';
import AddPatientPage from './AddPatientPage';
import { PatientFilePage } from './PatientFilePage';

enum Selector {
    RESULTS_TABLE = 'table[id=searchResultsTable] tbody',
    ADD_NEW_BTN = 'input[name=Submit]'
}
export default class PatientSearchResultsPage extends BasePage {
    constructor() {
        super('/FindPatient2.do?ContextAction=Submit');
    }

    public navigateTo(): void {
        throw new Error('Unable to navigate to search results directly');
    }

    clickAddNew(): AddPatientPage {
        this.clickFirst(Selector.ADD_NEW_BTN);
        return new AddPatientPage();
    }

    getResultsCount(): Cypress.Chainable<number> {
        return this.getElement(Selector.RESULTS_TABLE).then((table) => {
            const htmlTable = table[0] as HTMLTableElement;
            // get count of tr, if the table is empty there is a single tr with class of 'empty'
            return htmlTable.rows[0].classList.contains('empty') ? 0 : htmlTable.rows.length;
        });
    }

    getPatientFilePage(index: number): Cypress.Chainable<PatientFilePage> {
        return this.getElement(Selector.RESULTS_TABLE)
            .find('a', this.defaultOptions)
            .then((links) => {
                // the displayed patient Id text does not match the uid of the patient, we pull the uid from the href
                // example href: http://localhost:7001/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=10014284
                const href = (links[index] as HTMLAnchorElement).href;
                const uid = href.substring(href.indexOf('uid=') + 'uid='.length);
                return new PatientFilePage(uid);
            });
    }
}
