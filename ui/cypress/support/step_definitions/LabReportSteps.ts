import { Then, When } from 'cypress-cucumber-preprocessor/steps';
import LabReport from '../models/LabReport';
import HomePage, { Queue } from '../pages/HomePage';
import DateUtil from '../utils/DateUtil';
import LabReportMother from '../utils/LabReportMother';
import LabReportUtil from '../utils/LabReportUtil';

const detailedLogs = Cypress.env('detailedLogs');

When(/I create a lab report/, () => {
    const report = LabReportMother.acidFastStain();
    cy.wrap(report, { log: detailedLogs }).as('report');
    LabReportUtil.createLabReportForPatient(report);
});

Then(/I can view the report in the (.*) queue/, (queue: Queue) => {
    const homePage = new HomePage();
    homePage.navigateTo();
    switch (queue) {
        case Queue.DOCUMENTS_REQUIRING_REVIEW:
            homePage.clickDocumentsRequiringReview().then((queuePage) => {
                cy.get('@report', { log: detailedLogs }).then((wrappedReport) => {
                    const report = wrappedReport as unknown as LabReport;
                    const table = queuePage.getResultsTable();
                    table.should('contain', 'Lab Report');
                    table.should('contain', report.reportingFacility.name);
                    table.should('contain', report.patient.firstName);
                    table.should('contain', report.patient.lastName);
                    table.should('contain', DateUtil.getNBSFormattedDate(report.patient.dateOfBirth, true));
                    report.resultedTests.forEach((resultedTest) => {
                        table.should('contain', resultedTest.test.description);
                    });
                    table.should('contain', report.jurisdiction);
                });
            });
            break;
        default:
            throw new Error('NYI for queue: ' + queue);
    }
});
