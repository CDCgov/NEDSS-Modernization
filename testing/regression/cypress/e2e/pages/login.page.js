const user = Cypress.env()["env"].loginusername;
const pass = Cypress.env()["env"].loginpassword;  

class LoginPage {

    navigateToHomepage() {
        cy.visit('/nbs/login')
    }

    login() {      
        cy.get('body').then((body) => {
            if (body.find("input[id='id_UserName']").length > 0) {
                cy.intercept("POST", "/graphql").as("loginRequest");
                cy.get('#id_UserName').type(user);
                if (pass != '') {
                    cy.get('#id_Password').type(pass);
                }
                cy.get('#id_Submit_bottom_ToolbarButtonGraphic').click();
                cy.get('#homePageAdvancedSearch').click();
                cy.wait("@loginRequest");         
            } else {
                cy.intercept("POST", "/graphql").as("loginRequest");
                cy.get('#username').type(user);
                if (pass != '') {
                    cy.get('#password').type(pass);
                }
                cy.get('#kc-login').click();
                cy.get('#homePageAdvancedSearch').click();
                cy.wait("@loginRequest");
            }
        });



    }

    verifyLoginPage() {
        cy.get('.logo img').should('be.visible')
    }
  }
  
export const loginPage = new LoginPage(); 