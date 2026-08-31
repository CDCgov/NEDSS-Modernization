// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })
import '@testing-library/cypress/add-commands';

Cypress.Commands.add('selectDropdownByLabel', (labelText, value, selectIndex = 0) => {
    // Get the ID string instead of holding a live element reference
    cy.findAllByLabelText(labelText)
        .eq(selectIndex)
        .invoke('attr', 'id')
        .then((id) => {
            const escapedId = CSS.escape(id);

            // Check the live DOM state using the ID
            cy.get('body').then(($body) => {
                if ($body.find(`select#${escapedId}`).length > 0) {
                    cy.get(`select#${escapedId}`).select(value);
                } else {
                    // React Select / Custom Checkbox Input
                    const input = `input#${escapedId}`;
                    cy.get(input).click({ force: true });
                    cy.get(input).clear({ force: true });
                    cy.get(`input#${escapedId}`).type(value);
                    cy.contains('[class*="__option"], [class*="-option"]', value)
                        .should('be.visible')
                        .click({ force: true });
                    cy.get(`input#${escapedId}`).type('{esc}');
                }
            });
        });
});

Cypress.Commands.add(
    'eqOrLast',
    {
        prevSubject: true,
    },
    ($subject, index) => {
        cy.log($subject, index);
        const i = index < $subject.length ? index : $subject.length - 1;

        return cy.wrap($subject).eq(i);
    }
);

/**
 * Enter a value for a specified input selector
 * @param {string} inputSelector - Selector for the input element
 * @param {string} value - Value to enter for the input
 * @param {number} [index = 0] - Index of the element, default is first element
 * @param {Object} [options = {}] - Options to pass to get method
 * @returns {Cypress.Chainable<JQuery<HTMLElement>>} - The selected input
 */
Cypress.Commands.add('enterInput', (inputSelector, value, index = 0, options = {}) => {
    cy.get(inputSelector, options).eqOrLast(index).clear();
    return cy.get(inputSelector).eqOrLast(index).type(value);
});
