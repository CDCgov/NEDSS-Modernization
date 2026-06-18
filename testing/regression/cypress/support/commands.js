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
import '@testing-library/cypress/add-commands'

// Cypress.Commands.add('selectByLabel', (labelText, value) => {
//     // 1. Testing Library immediately finds the correct element tied to the label text
//     cy.findByLabelText(labelText).then(($el) => {
//         // 2. Determine if it's a standard select dropdown or a React custom input
//         if ($el.is('select')) {
//             // Standard HTML Select
//             cy.wrap($el).select(value);
//         } else {
//             // React Select (where the label points directly to the text input)
//             cy.wrap($el).click({ force: true }).type(`${value}{enter}`);
//         }
//     });
// });

Cypress.Commands.add('selectDropdownByLabel', (labelText, value) => {
    // Get the ID string instead of holding a live element reference
    cy.findByLabelText(labelText)
        .invoke('attr', 'id')
        .then((id) => {
            const escapedId = CSS.escape(id);

            // Check the live DOM state using the ID
            cy.get('body').then(($body) => {
                if ($body.find(`select#${escapedId}`).length > 0) {
                    cy.get(`select#${escapedId}`).select(value);
                } else {
                    // React Select / Custom Checkbox Input
                    cy.get(`input#${escapedId}`).click({ force: true }).clear({ force: true }).type(value);
                    cy.contains('[class*="__option"], [class*="-option"]', value).should('be.visible').click({ force: true });
                    cy.get(`input#${escapedId}`).type('{esc}');
                }
            });
        });
});
