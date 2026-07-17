import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

// Navigation steps

When('I navigate to list reports', () => {
    cy.visit('/nbs/ManageReports.do');
    cy.contains('Private Reports').should('be.visible');
});

const GROUP_LOOKUP = {
    Public: 'S',
    Private: 'P',
    Template: 'T',
    'Reporting Facility': 'R',
};

When(
    'I navigate to {string} report with reportUid: {int} and dataSourceUid: {int}',
    (group, reportUid, dataSourceUid) => {
        // click somewhere or else NBS6 may get angry later about no clicks having happened
        cy.contains('a', 'Collapse Sections').click();
        // mimic clicking a "Run" link
        const resolvedGroup = GROUP_LOOKUP[group] ?? group;
        cy.window().then((win) => {
            cy.log(
                `Calling runReport with Group: ${resolvedGroup}, Report ID: ${reportUid}, and Data Source ID: ${dataSourceUid}`
            );
            win.runReport(resolvedGroup, reportUid, dataSourceUid);
        });
    }
);

// Component input steps

When('I enter {string} to the From date', (date) => {
    // Matches any ID ending with "-from", bypassing the React dynamic prefix
    cy.get('input[id$="-from"]').type(date);
});

When('I enter {string} to the To date', (date) => {
    // Matches any ID ending with "-to", bypassing the React dynamic prefix
    cy.get('input[id$="-to"]').type(date);
});

When('I enter From Month: {string} and From Year: {string}', (month, year) => {
    cy.selectDropdownByLabel(0, 'From month', month);
    cy.selectDropdownByLabel(0, 'From year', year);
});

When('I enter To Month: {string} and To Year: {string}', (month, year) => {
    cy.selectDropdownByLabel(0, 'To month', month);
    cy.selectDropdownByLabel(0, 'To year', year);
});

When('I select {string} from the {string} dropdown menu', (value, label) => {
    cy.selectDropdownByLabel(0, label, value);
});

When('I select the column {string}', (columnName) => {
    cy.get('input[name="column-search"]').type(columnName);
    cy.contains('label', columnName).find('input[type="checkbox"]').check({ force: true });
});

When('I fill out all filters with {int}', (index) => {
    // dates and text filters
    cy.findAllByRole('textbox').each(($input) => cy.wrap($input).type(`01/01/202${index}`));
    // number inputs
    cy.findAllByRole('spinbutton').each($input => cy.wrap($input).type(`${index}`))
    // allow nulls
    cy.findAllByRole('checkbox', { name: /Include nulls/ }).each(($input) => cy.wrap($input).click({ force: true }));

    // select columns (needs to happen for the sort single-selects to be happy)
    cy.findByRole('checkbox', { name: 'Select all' }).click({ force: true });

    // single selects
    cy.get('select').each(($select) => cy.wrap($select).select(index));

    // multi-selects
    cy.get('.multi-select')
      // reverse the array to select the state first so counties do not get cleared
      .then(($elements) => $elements.toArray().reverse())
      .each(($select) => {
        cy
          .wrap($select)
          .then(($multiselect) => {
              if ($multiselect.find('.multi-select__clear-indicator').length > 0) {
                  cy.wrap($multiselect).find('.multi-select__clear-indicator').click({force: true});
              }
          })

        cy
          .wrap($select)
          .click()
          .then(() => cy.get('.multi-select__option').eqOrLast(index).click().then(() => cy.get('body').type('{esc}')))
    });

    // finish advanced filter
    cy.findByRole('combobox', { name: 'Logic' }).select('Is Null');
});

Then('All filters should be filled out with {int}', (index) => {
    // dates and text filters
    cy.findAllByRole('textbox').each(($input) => cy.wrap($input).should('have.value', `01/01/202${index}`));
    // number inputs
    cy.findAllByRole('spinbutton').each($input => cy.wrap($input).should('have.value', index))
    // allow nulls
    cy.findAllByRole('checkbox', { name: /Include nulls/ }).each(($input) => cy.wrap($input).should('be.checked'));

    // select columns (needs to happen for the sort single-selects to be happy)
    cy.findByRole('checkbox', { name: 'Deselect all' }).should('not.be.checked');

    // single selects
    cy.get('select').each(($select) => cy.wrap($select).select(index));

    // multi-selects
    cy.get('.multi-select').each(($select) => {
      // open dropdown
      cy.wrap($select).find('input.multi-select__input').click();

      cy.wrap($select)
        .find('.multi-select__option')
        .eqOrLast(index)
        .invoke('text') // get the dropdown's first index value
        .then((optionText) => {
          cy.wrap($select)
            .find('.multi-select__multi-value__label')
            .invoke('text')
            .then((displayedText) => {
              expect(displayedText).to.equal(optionText);
            });
        });

      // close dropdown without clearing next input
      cy.wrap($select).find('input.multi-select__input').blur();
    });

    // finish advanced filter
    cy.findByRole('combobox', { name: 'Logic' }).select('Is Null');
});

// Confirmation steps

Then('I see the {string} report', (title) => {
    cy.contains(title).should('be.visible');
});
