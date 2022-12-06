// / <reference types="cypress" />
import { When } from 'cypress-cucumber-preprocessor/steps';

When(/I navigate to the simple search page/, () => {
    cy.visit('/search');
});

Then('I enter the Last Name {string}', (LastName) => {
    cy.get('#lastName').type(LastName);
});

Then('I enter the First Name {string}', (FirstName) => {
    cy.get('#firstName').type(FirstName);
});

Then('I click on search button', () => {
    cy.get('button').contains('Search').click();
});

// Then('I select gender as a {string}', (Gender) => {
//     cy.get('select').select(Gender);
// });

// Then('I enter the Date of Birth {string}', (dateofbirth) => {
//     cy.get('[data-testid="date-picker-external-input"]').type(dateofbirth);
// });

Given('I visit the nbs site', () => {
    cy.visit('/login');
});

Given('I login in with {string}', (username) => {
    cy.get('#username').type(username);
    Cypress.on('uncaught:exception', (err, ruunable) => {
        return false;
    });
    cy.get('button').contains('Sign in').click();
});

Then('I click on Advanced Search', () => {
    cy.get('p').contains('Advanced Search').click();
});