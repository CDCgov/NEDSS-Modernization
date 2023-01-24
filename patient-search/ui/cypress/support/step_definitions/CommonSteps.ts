// / <reference types="cypress" />
import { When } from 'cypress-cucumber-preprocessor/steps';

/* 
 * Following are the step definitions that are very generic in nature and intended to be used across the board.
 * Please refrain from adding any specific ones here. Instead create a separate feature specific step file and add there iof required.
 */

// Use to visit any URL
When('I visit the {string} page', (route) => {
    cy.visit(route);
});

// Use to enter any value in Input
Then('I enter {string} in {string}', (value, selector) => {
    cy.get(selector).type(value);
});

// Use to choose an option from Select
Then('I select option {int} from {string}', (option, selector) => {
    cy.get(selector).select(option);
});

// Use to click Buttons
Then('I click the {string} button', (selector) => {
    cy.get(selector).click();
});

// Use to intercept any api call by its method and url and giving it an alias to refer later
Then('I intercept the {string} method of {string} alias {string} api', (method, api, alias) => {
    cy.intercept(method, api).as(alias);
});

// Use to wait on the alias api call and then validate request parameters of the api call
Then('I expect the {string} api to be called with {string}', (alias, requestParams) => {
    cy.wait(`@${alias}`).then((xhr) => {
        assert.deepEqual(xhr.request.body, JSON.parse(requestParams))
    });
});