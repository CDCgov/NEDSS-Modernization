import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

// Navigation steps

When('I navigate to report with reportUid: {string} and dataSourceUid: {string}', (reportUid, dataSourceUid) => {

    cy.visit('/nbs/ManageReports.do');

    // Fill out the hidden fields on the page's master form
    cy.get('form[name="frm"]').within(() => {
        cy.get('input[name="mode"]').invoke('val', 'edit');
        cy.get('input[name="ObjectType"]').invoke('val', '7');
        cy.get('input[name="OperationType"]').invoke('val', '117');
        cy.get('input[name="DataSourceUID"]').invoke('val', dataSourceUid);
        cy.get('input[name="ReportUID"]').invoke('val', reportUid);
    });

    // Target the form attributes to match the app's signature, then submit
    cy.get('form[name="frm"]').then(($form) => {
        $form.attr('action', '/nbs/nfc');
        $form.attr('method', 'post');
        $form.attr('target', '_self');
    }).submit();
});

// Component input steps

When('I enter {string} to the From date', (date) => {
    // Matches any ID ending with "-from", bypassing the React dynamic prefix
    cy.get('input[id$="-from"]').type(date);
});

When('I enter {string} to the To date', (date) => {
    // Matches any ID ending with "-to", bypassing the React dynamic prefix
    cy.get('input[id$="-to"]').type(date);
});

When('I select {string} from the {string} dropdown menu', (value, label) => {
    cy.selectByLabel(label, value);
});

When('I select the column {string}', (columnName) => {
    cy.get('input[name="column-search"]').type(columnName);
    cy.contains('label', columnName)
        .find('input[type="checkbox"]')
        .check({ force: true });
});

When('I click on the {string} button', (buttonText) => {
    cy.contains('button', buttonText).click();
});

// Confirmation steps

Then('I see confirmation the report has run', () => {
    cy.contains('main', 'Your report is opening in a new tab').should('be.visible');
});
