class PageElementsPage {
    navigateToCreatePage () {
        cy.visit('/page-builder/pages/add')
    }

    userViewsCreatePage() {
        cy.get(".createNewPage")
    }

    selectEventType() {
        cy.wait(1000)
        cy.get("#eventType").select("INV")
    }

    seeElementText(text) {
        this.selectEventType()
        cy.contains(new RegExp(text, 'i'))
    }

    clickConditionField() {
        this.selectEventType()
        cy.get("#conditionIds").click()
    }

    dropdownConditionsOpen() {
        cy.get(".multi-select__control--menu-is-open").should('be.visible')
    }

    selectValueFromConditions() {
        cy.get('#conditionIds').eq(0).click({ force: true })
    }

    conditionFieldHasValue() {
        cy.get('#conditionIds').eq(0).click({ force: true })
    }

    clickOnConditionDropdownArrow() {
        cy.get('#conditionIds').eq(0).click({ force: true })
    }

    dropdownConditionsClose() {
        cy.get(".multi-select__control--menu-is-open").should('not.exist')
    }

    clickPageNameField() {
        this.selectEventType()
        cy.get('#name').click()
    }

    pageNameFieldFocused() {
        cy.get('#name').should('be.focused')
    }

    enterValueInPageNameField() {
        cy.get('#name').type('Malaria Investigation')
    }

    pageNameFieldAllows() {
        cy.get('#name').should('have.value', 'Malaria Investigation')
    }

    clickEventTypeField() {
        cy.wait(1000)
        cy.get('#eventType').select('', {force: true})
    }

    eventTypeFieldFocused() {
        cy.wait(1000)
        cy.get('#eventType').select('', {force: true})
        cy.get('#eventType').should('be.focused')
    }

    eventTypeFieldHasValue(optionText) {
        cy.get('#eventType').should('contain', optionText)
    }

    clickTemplateField() {
        this.selectEventType()
        cy.wait(500)
        cy.get('#templateId').select('')
    }

    templateFieldFocused() {
        cy.get('#templateId').should('be.focused')
    }

    templateFieldHasValueList() {
        cy.get('#templateId').find('option').should('have.length.gt', 1)
    }

    selectValueFromTemplateList() {
        cy.get('#templateId').find('option').eq(1).then((option) => {
            cy.get('#templateId').select(option.attr('value'))
        })
    }

    templateFieldHasValue() {
        cy.get('#templateId').invoke('val').should('exist')
    }

    clickMMGField() {
        this.selectEventType()
        cy.wait(500)
        cy.get('#messageMappingGuide').select('')
    }

    mmgFieldFocused() {
        cy.get('#messageMappingGuide').should('be.focused')
    }

    mmgFieldHasValueList() {
        cy.get('#messageMappingGuide').find('option').should('have.length.gt', 1)
    }

    selectValueFromMMGList() {
        cy.get('#messageMappingGuide').find('option').eq(1).then((option) => {
            cy.get('#messageMappingGuide').select(option.attr('value'))
        })
    }

    mmgFieldHasValue() {
        cy.get('#messageMappingGuide').invoke('val').should('exist')
    }

    clickCancelButton() {
        cy.get('#cancelBtn').click()
    }

    checkPageLibraryShowing() {
        cy.visit('/page-builder/pages')
        cy.url().should('include', 'page-builder/pages')
    }

    clickPageLibraryLink() {
        cy.get('#pageLibraryLink').click()
    }

}

export const pageElementsPage = new PageElementsPage()