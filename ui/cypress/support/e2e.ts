// Import commands.js using ES2015 syntax:
import './commands';

beforeEach(() => {
    // NBS throws some javascript exceptions on certain pages. Catching them here prevents failure
    cy.on('uncaught:exception', (error, runnable) => {
        return !(
            error.message.includes('Cannot read properties of undefined (reading') ||
            error.message.includes('Cannot read properties of null (reading') ||
            error.message.includes('maxLength is not')
        );
    });
});

// clean up test created data
after(() => {
});
