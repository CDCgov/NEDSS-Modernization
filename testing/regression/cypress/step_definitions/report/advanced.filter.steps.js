import { When, Then } from '@badeball/cypress-cucumber-preprocessor';

// used with multi selects
const BASE_LOGIC_VALUES = ['Equals', 'Not Equals', 'Is Null', 'Is Not Null'];

// used with texts
const TEXT_LOGIC_VALUES = [...BASE_LOGIC_VALUES, 'Contains', 'Starts With'];

// used with numbers/dates
const NUMBER_LOGIC_VALUES = [...BASE_LOGIC_VALUES, 'Between', 'Less Than', 'Greater Than', 'Less Or Equal', 'Greater Or Equal'];

const advancedFilterOptions = [
  {
    field: 'Age Reported',
    logic: NUMBER_LOGIC_VALUES,
    type: 'number',
    firstVal: '1',
    secondVal: '2',
  },
  {
    field: 'Confirmation Method Time',
    logic: NUMBER_LOGIC_VALUES,
    type: 'date',
    firstVal: '01/01/2025',
    secondVal: '02/02/2026',
  },
  {
    field: 'Birth Gender Code',
    logic: BASE_LOGIC_VALUES,
    type: 'multiselect',
    firstVal: 1
  },
  {
    field: 'Investigator Name',
    logic: TEXT_LOGIC_VALUES,
    type: 'text',
    firstVal: '1',
    secondVal: '2',
  },
  {
    field: 'Outcome Code',
    logic: BASE_LOGIC_VALUES,
    type: 'multiselect',
  }
]

When('I add incomplete or incorrect advanced filters', () => {
  advancedFilterOptions.forEach((advFilter, index) => {
    if (index > 0) {
      cy.findAllByRole('button', {name: 'Add rule group'}).last().click();
    }
    advFilter.logic.forEach((logicVal, index) => {
      if (index > 0) {
        cy.findAllByRole('button', {name: 'Add rule'}).last().click();
      }
      cy.get('select[name="Field"]').last().select(advFilter.field);
      if (index !== 0) {
        // leave some logic values intentionally blank to trigger error
        cy.get('select[name="Logic"]').last().select(logicVal);

        // enter invalid date
        if (advFilter.field === 'Confirmation Method Time' && logicVal === 'Not Equals') {
          cy.enterInput('name="Value"', 2);
        }
      }

      if (logicVal === 'Between') {
        if (advFilter.field === 'Age Reported') {
          enterBetweenInput('2', '1');
        }

        if (advFilter.field === 'Confirmation Method Time') {
          enterBetweenInput('1', '32');
        }
      }
    })
  })
})

  When('I enter complete and accurate advanced filter values', () => {
    // remove last option since that rule group was removed
    const fields = advancedFilterOptions.slice(0, -1);

    fields.forEach((field) => {
      cy.get('select[name="Field"] option:selected')
        .filter((index, optionElement) => {
          return optionElement.innerText === field.field;
        }).each(($select, index) => {
          cy.wrap($select).closest('[data-testid="rule"]')
            .within(() => {
              enterFilterValue(field, index)
            })
        })
    })
    cy.findByRole('checkbox', { name: 'Select all' }).click({ force: true });
  })

const BASE_ERROR_MESSAGE = [
  'Enter a logic value for Age Reported.',
  'Enter a value for Age Reported.',
  'From value must be before To value for Age Reported.',
  'Enter a logic value for Confirmation Method Time.',
  'Enter a value for Confirmation Method Time.',
  'From date of "1" is not valid mm/dd/yyyy formatted date for Confirmation Method Time.',
  'Enter a logic value for Birth Gender Code.',
  'Enter a value for Birth Gender Code.',
  'Enter a logic value for Investigator Name.',
  'Enter a value for Investigator Name.',
  'Enter a logic value for Outcome Code.',
  'Enter a value for Outcome Code.',
  'Date of "2" is not a valid mm/dd/yyyy formatted date for Confirmation Method Time.'
]

Then('I see errors related to incomplete or incorrect advanced filters', () => {
  BASE_ERROR_MESSAGE.forEach((error) => {
    checkAlertForErrorMsg(error);
  });
})

Then('I see an error to remove a blank rule group', () => {
  checkAlertForErrorMsg('Remove or add rules to the empty rule group.');
})

When('I remove all rules from an entire rule group', () => {
  cy.findAllByTestId("rule-group").last().within(() => {
    cy.findAllByTestId("rule").each(($rule) => {
      cy.wrap($rule).within(() => {
        cy.get('button[aria-label="Remove rule"]').click();
      })
    })
  })
})

When('I remove a rule group', () => {
  cy.findAllByTestId("rule-group").last().within(() => {
    cy.get('button[aria-label="Remove group"]').click();
  })
})

// Helpers
const enterFilterValue = (field, index) => {
  cy.get('select[name="Field"]').select(field.field);
  const logic = field.logic[index];
  cy.get('select[name="Logic"]').select(logic);

  if (field.type !== 'multiselect') {
    if (['Equals', 'Not Equals', 'Contains', 'Starts With', 'Less Than', 'Greater Than', 'Less Or Equal', 'Greater Or Equal'].includes(logic)) {
      enterInput('name="Value"', field.firstVal);
    }

    if (logic === 'Between') {
      enterBetweenInput(field.firstVal, field.secondVal);
    }
  } else {
    if (logic === 'Equals' || logic === 'Not Equals') {
      cy.get('.multi-select').click();
      cy.get('.multi-select')
        .then(() => cy.get('.multi-select__option').eqOrLast(field.firstVal).click())
      cy.get('input.multi-select__input').blur();
    }
  }
}

const enterInput = (inputSelector, value) => {
  cy.get(`input[${inputSelector}]`).last().clear();
  cy.get(`input[${inputSelector}]`).last().type(value);
}

const enterBetweenInput = (from, to) => {
  enterInput('id$="-from"', from);
  enterInput('id$="-to"', to);
}

const checkAlertForErrorMsg = (error) => {
  cy.findAllByRole('alert')
    .contains(error)
    .should('be.visible');
}