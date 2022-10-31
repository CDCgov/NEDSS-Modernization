import Patient from '../models/Patient';
import AddLabReportPage from './AddLabReportPage';
import BasePage from './BasePage';

enum Selector {
    PATIENT_SUMMARY_TABLE = 'div[id=subsect_basicInfo] table[id=Summary_summary]',
    DELETE_BUTTON = 'input[name=delete]',
    SUMMARY_TAB = 'td[id=tabs0head0]',
    EVENTS_TAB = 'td[id=tabs0head1]',
    DEMOGRAPHICS_TAB = 'td[id=tabs0head2]',
    ADD_LAB_REPORT_BUTTON = 'div[id=subsect_Lab] input[name=Add]',
    DOCUMENTS_REQUIRING_REVIEW_DIV = 'div[id=subsect_Reports]'
}
export class PatientFilePage extends BasePage {
    constructor(patientId: string) {
        super(`/PatientSearchResults1.do?ContextAction=ViewFile&uid=${patientId}`);
    }

    assertPatientSummaryExists(patient: Patient): void {
        const summaryTable = this.getElement(Selector.PATIENT_SUMMARY_TABLE);
        summaryTable.should('contain', patient.state);
        summaryTable.should('contain', patient.county);
        summaryTable.should('contain', patient.cellPhone);
        summaryTable.should('contain', patient.email);
        patient.races.forEach((race) => {
            summaryTable.should('contain', race);
        });
        summaryTable.should('contain', patient.ethnicitiy);
        patient.identifications.forEach((id) => {
            summaryTable.should('contain', id.identificationType);
            summaryTable.should('contain', id.idNumber);
        });
    }

    clickAddLabReport(): AddLabReportPage {
        this.clickFirst(Selector.EVENTS_TAB);
        this.getElement(Selector.ADD_LAB_REPORT_BUTTON).should('be.visible');
        this.click(Selector.ADD_LAB_REPORT_BUTTON);
        const addLabReportPage = new AddLabReportPage();
        addLabReportPage.setActiveTab('LabReport');
        return addLabReportPage;
    }

    clickDelete(): void {
        this.clickFirst(Selector.DELETE_BUTTON);
    }

    getAllLabReports(): Cypress.Chainable<HTMLAnchorElement[]> {
        return this.getElement(Selector.DOCUMENTS_REQUIRING_REVIEW_DIV).then((div) => {
            // this table may or may not exist depending on if the patient has lab reports
            const table = div[0].getElementsByClassName('bluebardtTable')[0]?.getElementsByTagName('tbody')[0];
            const labReportLinks: HTMLAnchorElement[] = [];
            if (table !== undefined) {
                const links = table.getElementsByTagName('a');
                for (let i = 0; i < links.length; i++) {
                    if (links[i].text === 'Lab Report') {
                        labReportLinks.push(links[i]);
                    }
                }
            }
            return cy.wrap(labReportLinks, this.defaultOptions);
        });
    }
}
