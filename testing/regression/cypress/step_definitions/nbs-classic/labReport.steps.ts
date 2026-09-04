import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

Then('I can open the investigation for {string}', (condition: string) => {
    cy.get('a[name="pageTop"]').contains(`View Investigation: ${condition}`);
});

Then('I can print the lab report', () => {
    const printURL = '/nbs/PageAction.do?method=viewGenericLoad&mode=print';
    cy.window().then((win) => {
        cy.stub(win, 'open').as('open');
    });
    cy.findAllByRole('button', { name: 'Print' }).first().click();
    cy.get('@open').should('have.been.calledWith', printURL);
    cy.visit(printURL);
    cy.get('a').contains('Print Page');
});

When('I change the program area to {string}', (programArea: string) => {
    cy.enterInput('input[name="orderedTest.theObservationDT.progAreaCd_textbox"]', programArea);
});

When('I change the jurisdiction to {string}', (jurisdiction: string) => {
    cy.enterInput('input[name="orderedTest.theObservationDT.jurisdictionCd_textbox"]', jurisdiction);
});

Then(
    'I see a transfer confirmation message to program area {string} and jurisdiction {string}',
    (programArea: string, jurisdiction: string) => {
        cy.get('b').contains('This record has been successfully transferred to:');
        cy.get('tr[rowid="N10035"]').contains(programArea);
        cy.get('tr[rowid="N10048"]').contains(jurisdiction);
    }
);

Then('I see a pop up to mark the STD lab report as reviewed', () => {
    const markAsReviewedURL =
        '/nbs/LoadProcessingDecision.do?method=processingDecisionLoad&event=LabReport&PDLogic=STD_UNKCOND_PROC_DECISION&context=loadMAR';
    cy.window().then((win) => {
        cy.stub(win, 'open').as('open');
    });
    cy.get(`input[name="markReviewd"]`).first().click();
    cy.get('@open').should('have.been.calledWith', markAsReviewedURL);
    cy.visit(markAsReviewedURL);
    cy.get('a').contains('Reason for No Further Action');
    // can't submit it because of how popups work in NBS6
});
