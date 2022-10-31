import BasePage from '../../BasePage';
import AddAlgorithmPage from './AddAlgorithmPage';
import EditAlgorithmPage from './EditAlgorithmPage';
import ViewAlgorithmPage from './ViewAlgorithmPage';

export interface AlgorithmEntry {
    viewLink: HTMLAnchorElement;
    editLink: HTMLAnchorElement;
    eventType: string;
    algorithmName: string;
    relationConditions: string;
    action: string;
    lsatUpdated: string;
    status: string;
}
enum Selector {
    ADD_NEW_ALGORITHM_BUTTON = 'input[name=Submit]',
    ALGORITHMS_TABLE = 'table[class=dtTable] tbody'
}
export default class ManageAlgorithmsPage extends BasePage {
    constructor() {
        super('/ManageDecisionSupport.do?method=loadqueue&initLoad=true');
    }

    getAlgorithmsTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ALGORITHMS_TABLE);
    }

    clickAddNewAlgorithm(): AddAlgorithmPage {
        this.click(Selector.ADD_NEW_ALGORITHM_BUTTON);
        return new AddAlgorithmPage();
    }

    // searches the algorithms displayed for the algorithmName, then clicks the view algorithm button
    clickViewAlgorithm(algorithmName: string): Cypress.Chainable<ViewAlgorithmPage> {
        return this.getAlgorithmEntryByDisplayName(algorithmName).then((algorithm) => {
            algorithm.viewLink.click();
            return new ViewAlgorithmPage(this.getAlgorithmId(algorithm.viewLink.href));
        });
    }

    clickEditAlgorithm(displayName: string): Cypress.Chainable<EditAlgorithmPage> {
        return this.getAlgorithmEntryByDisplayName(displayName).then((algorithm) => {
            algorithm.editLink.click();
            return new EditAlgorithmPage(this.getAlgorithmId(algorithm.editLink.href));
        });
    }

    getAlgorithmEntryByDisplayName(algorithmName: string): Cypress.Chainable<AlgorithmEntry> {
        return this.getDisplayedAlgorithms().then((algorithmList) => {
            const algorithm = algorithmList.find((s) => s.algorithmName === algorithmName);
            if (!algorithm) {
                throw new Error('Failed to find algorithm entry with algorithmName: ' + algorithmName);
            }
            return algorithm;
        });
    }

    // creates an AlgorithmEntry object for each row of the algorithms table
    getDisplayedAlgorithms(): Cypress.Chainable<AlgorithmEntry[]> {
        return this.getAlgorithmsTable().then((jquery) => {
            const table = jquery[0] as HTMLTableElement;
            const entries: AlgorithmEntry[] = [];
            if (table.rows.length === 1 && table.rows[0].classList.contains('empty')) {
                return entries;
            }
            for (let i = 0; i < table.rows.length; i++) {
                entries.push(this.convertRowToAlgorithmEntry(table.rows[i]));
            }
            return entries;
        });
    }

    private convertRowToAlgorithmEntry(row: HTMLTableRowElement): AlgorithmEntry {
        const columns = row.getElementsByTagName('td');
        return {
            viewLink: columns[0].getElementsByTagName('a')[0],
            editLink: columns[1].getElementsByTagName('a')[0],
            eventType: columns[2].innerHTML,
            algorithmName: columns[3].innerHTML,
            relationConditions: columns[4].innerHTML,
            action: columns[5].innerHTML,
            lsatUpdated: columns[6].innerHTML,
            status: columns[7].innerHTML
        };
    }

    private getAlgorithmId(href: string): string {
        return href
            .substring(href.indexOf('algorithmUid=') + 'algorithmUid='.length)
            .substring(0, href.indexOf('&method='));
    }
}
