const user = Cypress.env('LOGIN_USERNAME');

// TODO: Fix, as this is almost assuredly setup incorrectly
const pass = Cypress.env('LOGIN_PASSWORD', '') as unknown as string;

class LoginPage {
    navigateToHomepage() {
        cy.visit('/nbs/login');
    }

    login() {
        this.loginAsUserName(user);
    }

    loginAsUserName(username: string) {
        cy.get('body').then((body) => {
            if (body.find("input[id='id_UserName']").length > 0) {
                cy.intercept('POST', '/graphql').as('loginRequest');
                cy.get('#id_UserName').type(username);
                if (pass !== '') {
                    cy.get('#id_Password').type(pass);
                }
                cy.get('#id_Submit_bottom_ToolbarButtonGraphic').click();
                cy.get('#homePageAdvancedSearch').click();
                cy.url().should('include', '/search/patients');
            } else {
                cy.intercept('POST', '/graphql').as('loginRequest');
                cy.get('#username').type(username);
                if (pass !== '') {
                    cy.get('#password').type(pass);
                }
                cy.get('#kc-login').click();
                cy.get('#homePageAdvancedSearch').click();
                cy.url().should('include', '/search/patients');
            }
        });
    }

    loginStayOnClassic() {
        cy.get('body').then((body) => {
            if (body.find("input[id='id_UserName']").length > 0) {
                cy.intercept('POST', '/graphql').as('loginRequest');
                cy.get('#id_UserName').type(user);
                if (pass !== '') {
                    cy.get('#id_Password').type(pass);
                }
                cy.get('#id_Submit_bottom_ToolbarButtonGraphic').click();
            } else {
                cy.intercept('POST', '/graphql').as('loginRequest');
                cy.get('#username').type(user);
                if (pass !== '') {
                    cy.get('#password').type(pass);
                }
                cy.get('#kc-login').click();
            }
        });
    }

    verifyLoginPage() {
        cy.get('.logo img').should('be.visible');
    }
}

export const loginPage = new LoginPage();
