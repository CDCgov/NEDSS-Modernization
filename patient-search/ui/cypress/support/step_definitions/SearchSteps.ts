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
    cy.get('#simpleSeachButton').click();
});

// Then('I select gender as a {string}', (Gender) => {
//     cy.get('select').select(Gender);
// });

// Then('I enter the Date of Birth {string}', (dateofbirth) => {
//     cy.get('[data-testid="date-picker-external-input"]').type(dateofbirth);
// });

Given('I visit the nbs site', () => {
    cy.visit('/nbs/login');
});

Given('I login in with {string}', (username) => {
    cy.get('#id_UserName').type(username);
    Cypress.on('uncaught:exception', (err, ruunable) => {
        return false;
    });
    cy.get('#id_Submit_bottom_ToolbarButtonGraphic').click();
});
