import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

const GROUP_LOOKUP = {
  Public: 'S',
  Private: 'P',
  Template: 'T',
  'Reporting Facility': 'R',
};

const YEARS_BACK = 20;
const getThisYear = () => new Date().getFullYear();

// Navigation steps

When('I navigate to list reports', () => {
    cy.visit('/nbs/ManageReports.do');
    cy.contains('Private Reports').should('be.visible');
});

When('I click the {string} report', (reportTitle) => {
    cy.contains('td', reportTitle)
      .parent('tr')
      .contains('a', 'Run')
      .click();
})

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
    // add extra advanced filter rule before so steps below adds value
    cy.findByRole('button', { name: 'Add rule' }).click();

    // select columns (needs to happen for the sort single-selects to be happy)
    checkSelectAll();

    // single selects
    cy.get('select').each(($select) => cy.wrap($select).select(index));

    // finish advanced filter
    cy.findAllByRole('combobox', { name: 'Logic' }).each(($combobox) => cy.wrap($combobox).select(index));

    // dates and text filters
    cy.findAllByRole('textbox').each(($input) => cy.wrap($input).type(`01/01/202${index}`));
    // number inputs
    cy.findAllByRole('spinbutton').each($input => cy.wrap($input).type(`${index}`))
    // allow nulls
    cy.findAllByRole('checkbox', { name: /Include nulls/ }).each(($input) => cy.wrap($input).click({ force: true }));

    // multi-selects
    cy.get('.multi-select')
      .each(($select) => {
        cy
          .wrap($select)
          .click()
          .then(() => cy.get('.multi-select__option').eqOrLast(index).click().then(() => cy.get('body').type('{esc}')))
    });
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
    cy.get('select').each(($select) => {
      cy.wrap($select).should('have.prop', 'selectedIndex', index);
    });

    // multi-selects
    cy.get('.multi-select').each(($select) => {
      // open dropdown
      cy.wrap($select).find('input.multi-select__input').click();

      cy.wrap($select)
        .find('.multi-select__option')
        .eqOrLast(index)
        .invoke('text') // get the dropdown's value at the index
        .then((optionText) => {
          cy.wrap($select)
            .find('.multi-select__multi-value__label')
            .invoke('text')
            .then((displayedText) => {
              expect(displayedText).contains(optionText);
            });
        });

      // close dropdown without clearing next input
      cy.wrap($select).find('input.multi-select__input').blur();
    });
});

Then('All filters should be empty or the default value', () => {
    // dates and text filters
    cy.findAllByRole('textbox').each(($input) => cy.wrap($input).should('have.value', ''));
    // number inputs
    cy.findAllByRole('spinbutton').each($input => cy.wrap($input).should('have.value', ''))
    // allow nulls
    cy.findAllByRole('checkbox', { name: /Include nulls/ }).each(($input) => cy.wrap($input).should('not.be.checked'));

    cy.get('body').then(($body) => {
      const selectAllExists = $body.find('[label="Select all"]').length > 0;
      if (selectAllExists) {
        cy.findByRole('checkbox', { name: 'Select all' }).should('not.be.checked');
      }
    });

    // single selects
    cy.get('select').each(($select) => {
      const selectId = $select.attr('id');

      // check value is default or empty
      cy.get(`label[for="${selectId}"]`).invoke('text').then((labelText) => {
        if (labelText === 'From') {
          cy.wrap($select).should('have.value', getThisYear() - YEARS_BACK);
        } else if (labelText === 'To') {
          cy.wrap($select).should('have.value', getThisYear());
        }  else if (labelText === 'Sort order') {
          cy.wrap($select).should('have.value', 'ASC');
        } else if (labelText === 'Combinator') {
          cy.wrap($select).should('have.value', 'and');
        }  else if (labelText === 'Field') {
          cy.wrap($select).should('have.value', '~');
        } else {
          cy.wrap($select).should('have.value', '');
        }
      });
    });

    // multi-selects
    cy.get('.multi-select').each(($select) => {
      const selectId = $select.find('input.multi-select__input').attr('id');

      cy.get(`span[id="${selectId}-hint"]`).invoke('text').then((hintText) => {
        // this basic filter has a default
        if (hintText === 'States') {
          cy.wrap($select)
            .find('.multi-select__multi-value__label')
            .should('have.text', 'Georgia');
        } else {
          cy.wrap($select).find('.multi-select__multi-value__label').should('not.exist');
        }
      });
    });
});

Then('I click all include nulls', () => {
  // select columns (needs to happen for the sort single-selects to be happy)
  checkSelectAll();
  cy.findAllByRole('checkbox', { name: /Include nulls/ }).each(($input) => cy.wrap($input).click({ force: true }));
})

Then('All include nulls checkboxes should be checked', () => {
  cy.findAllByRole('checkbox', { name: /Include nulls/ }).each(($input) => cy.wrap($input).should('be.checked'));
})

// Confirmation steps

Then('I see the {string} report', (title) => {
    cy.contains(title).should('be.visible');
});

const checkSelectAll = () => {
  cy.get('body').then(($body) => {
    const selectAllExists = $body.find('[label="Select all"]').length > 0;
    if (selectAllExists) {
      cy.findByRole('checkbox', { name: 'Select all' }).click({ force: true });
    }
  });
}
