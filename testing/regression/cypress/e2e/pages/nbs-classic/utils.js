  
/**
 * Utility functions for handling dropdowns
 */

/**
 * Select a random value from any dropdown
 * @param {string} dropdownSelector - Selector for the dropdown element
 * @returns {Cypress.Chainable<string>} - The selected value
 */

export function selectRandomDropdownValue(dropdownSelector) {
    cy.log(`Selecting random value from dropdown: ${dropdownSelector}`);

    // Get all non-empty option values from the dropdown
    cy.get(`${dropdownSelector} option:not([value=""])`).then(($options) => {
        const optionCount = $options.length;
        
        if (optionCount === 0) {
        cy.log('No options found in dropdown');
        return;
        }
        
        cy.log(`Dropdown has ${optionCount} options`);
        
        // Create array of values and pick a random one
        const optionValues = $options.toArray().map(opt => opt.value);
        const randomValue = optionValues[Math.floor(Math.random() * optionCount)];
        
        cy.log(`Selected value: ${randomValue}`);
        
        // Select the random value (using force: true in case the select is disabled)
        cy.get(dropdownSelector).select(randomValue, { force: true });
    });
}
export function clickSubmitButton() {
  cy.get('input[name="Submit"][id="Submit"]').eq(0).click()
  cy.wait(1000);
}

export function clickAddButton() {
  cy.get('#Add').eq(0).click()
}