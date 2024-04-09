class LoginPage {
    navigateToHomepage() {
        cy.visit('/nbs/login')
    }

    login(user, pass) {
        cy.intercept("POST", "/graphql").as("loginRequest");
        cy.get('#id_UserName').type(user)
        if (pass != '') {
            cy.get('#id_Password').type(pass)
        }
        cy.get('#id_Submit_bottom_ToolbarButtonGraphic').click()        
        cy.get('#homePageAdvancedSearch').click()
        cy.wait("@loginRequest")
    }

    verifyLoginPage() {
        cy.get('.logo img').should('be.visible')
    }
  }
  
  export const loginPage = new LoginPage();
  