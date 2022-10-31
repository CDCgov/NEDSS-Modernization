import AddSystemPage from './AddSystemPage';
import BasePage from '../../BasePage';
import ViewSystemPage from './ViewSystemPage';
import EditSystemPage from './EditSystemPage';

export interface SystemEntry {
    viewLink: HTMLImageElement;
    editLink: HTMLImageElement;
    applicationName: string;
    displayName: string;
    facilityName: string;
    reportType: string;
    sender: string;
    recipient: string;
    transfer: string;
}
enum Selector {
    ADD_NEW_SYSTEM_BUTTON = 'input[id=submitCr]',
    SYSTEMS_TABLE = 'table[class=dtTable] tbody'
}
export default class ManageSystemsPage extends BasePage {
    constructor(realtiveUrl = '/ReceivingSystem.do?method=manageLoad&initLoad=true') {
        super(realtiveUrl);
    }

    getSystemsTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.SYSTEMS_TABLE);
    }

    clickAddNewSystem(): AddSystemPage {
        this.click(Selector.ADD_NEW_SYSTEM_BUTTON);
        return new AddSystemPage();
    }

    // searches the systems displayed for the applicationName, then clicks the view system button
    clickViewSystem(displayName: string): Cypress.Chainable<ViewSystemPage> {
        return this.getSystemEntryByDisplayName(displayName).then((system) => {
            system.viewLink.click();
            return new ViewSystemPage(this.getSystemId(system.viewLink.click.toString()));
        });
    }

    clickEditSystem(displayName: string): Cypress.Chainable<EditSystemPage> {
        return this.getSystemEntryByDisplayName(displayName).then((system) => {
            system.editLink.click();
            return new EditSystemPage(this.getSystemId(system.editLink.click.toString()));
        });
    }

    getSystemEntryByDisplayName(displayName: string): Cypress.Chainable<SystemEntry> {
        return this.getDisplayedSystems().then((systemsList) => {
            const system = systemsList.find((s) => s.displayName === displayName);
            if (!system) {
                throw new Error('Failed to find system entry with displayName: ' + displayName);
            }
            return system;
        });
    }

    // creates a SystemEntry object for each row of the systems table
    getDisplayedSystems(): Cypress.Chainable<SystemEntry[]> {
        return this.getSystemsTable().then((jquery) => {
            const table = jquery[0] as HTMLTableElement;
            const entries: SystemEntry[] = [];
            if (table.rows.length === 1 && table.rows[0].classList.contains('empty')) {
                return entries;
            }
            for (let i = 0; i < table.rows.length; i++) {
                entries.push(this.convertRowToSystemEntry(table.rows[i]));
            }
            return entries;
        });
    }

    private convertRowToSystemEntry(row: HTMLTableRowElement): SystemEntry {
        const columns = row.getElementsByTagName('td');
        return {
            viewLink: columns[0].getElementsByTagName('img')[0],
            editLink: columns[1].getElementsByTagName('img')[0],
            applicationName: columns[2].innerHTML,
            displayName: columns[3].innerHTML,
            facilityName: columns[4].innerHTML,
            reportType: columns[5].innerHTML,
            sender: columns[6].innerHTML,
            recipient: columns[7].innerHTML,
            transfer: columns[8].innerHTML
        };
    }

    private getSystemId(onclickMethod?: string): string {
        if (!onclickMethod) {
            throw new Error('Trying to get system id from undefined string');
        }
        let test = onclickMethod.replace(/\n/g, '');
        return test
            .substring(test.indexOf('exportReceivingFacilityUid=') + 'exportReceivingFacilityUid='.length)
            .replace("');}", '');
    }
}
