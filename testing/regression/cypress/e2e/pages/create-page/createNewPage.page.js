class CreateNewPagePage {
    userViewsCreateNewPageButton() {
        cy.get(".createNewPageButton")
    }
    clickCreateNewPageButton() {
        cy.get(".createNewPageButton").eq(0).click()
    }
    userViewsEventTypeField() {
        cy.get("#eventType")
    }

    selectEventType(type) {
        cy.wait(2000)
        if(type) {
            cy.get("#eventType").select(type)
        } else {
            cy.get("#eventType").select("INV")
        }
    }
    clickCreatePageButton() {
        cy.get('.createPage').eq(0).click()
    }
    navigateToClassicDesign () {
        cy.visit('/advanced-search/person')
    }
    viewTextOnPage(text) {
        cy.contains(text)
    }
    navigateToCreatePage () {
        cy.visit('/page-builder/pages/add')
    }
    seeElementText(text) {
        this.selectEventType()
        cy.contains(new RegExp(text, 'i'))
    }

    selectCondition() {
        this.selectEventType()
        cy.get("#conditionIds").click()
        cy.get('#conditionIds .multi-select__option input[type="checkbox"]').eq(0).click()
        cy.get('.multi-select__option--is-focused').click();
    }
    selectPageName() {
        this.selectEventType()
        cy.get('#name').click()
        cy.get('#name').type('Malaria Investigation')
    }
    selectTemplate() {
        this.selectEventType()
        cy.wait(500)
        cy.get('#templateId').find('option').eq(1).then((option) => {
            cy.get('#templateId').select(option.attr('value'))
        })
    }

    selectReportingMechanism() {
        this.selectEventType()
        cy.wait(500)
        cy.get('#messageMappingGuide').find('option').eq(1).then((option) => {
            cy.get('#messageMappingGuide').select(option.attr('value'))
        })
    }
    enterPageDescription() {
        this.selectEventType()
        cy.wait(500)
        cy.get("#pageDescription").type("This page is for diagnosis")
    }
    navigateToLibrary () {
        cy.visit('/page-builder/pages')
    }
}

export const createNewPagePage = new CreateNewPagePage()