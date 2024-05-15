class PageElementsPage {
    navigateToCreatePage () {
        cy.visit('/page-builder/pages/add')
    }

    userViewsCreatePage() {
        cy.get("#createNewPage")
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
        cy.get('#conditionIds .multi-select__option input[type="checkbox"]').eq(0).click()
        cy.get('.multi-select__option--is-focused').click();
    }

    conditionFieldHasValue() {
        cy.get('.multi-select__value-container--has-value').should('be.visible')
    }

    clickOnConditionDropdownArrow() {
        cy.get('.multi-select__dropdown-indicator').click()
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

}

export const pageElementsPage = new PageElementsPage()