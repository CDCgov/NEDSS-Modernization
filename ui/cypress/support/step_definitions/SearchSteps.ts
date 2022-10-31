import { When } from 'cypress-cucumber-preprocessor/steps';

When(/I navigate to the simple search page/, () => {
    cy.visit('/search');
});
