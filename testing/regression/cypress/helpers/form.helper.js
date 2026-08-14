/**
 * Helper functions for form inputs
 */

/**
 * Enter a value for a specified input selector
 * @param {string} inputSelector - Selector for the input element
 * @param {string} value - Value to enter for the input
 * @param {number} index - Index of the element, default is first element
 * @returns {Cypress.Chainable<JQuery<HTMLElement>>} - The selected input
 */
export function enterInput(inputSelector, value, index = 0) {
  cy.get(inputSelector).eqOrLast(index).clear();
  return cy.get(inputSelector).eqOrLast(index).type(value);
}