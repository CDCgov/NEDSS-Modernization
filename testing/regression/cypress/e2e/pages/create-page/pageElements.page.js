class PageElementsPage {
    navigateToCreatePage () {
        cy.visit('/page-builder/pages/add')
    }

    userViewsCreatePage() {
        cy.get("#createNewPage")
    }

    selectEventType() {
        cy.wait(500)
        cy.get("#eventType").select("INV")
    }

    seeElementText(text) {
        this.selectEventType()
        cy.contains(new RegExp(text, 'i'))
    }

}

export const pageElementsPage = new PageElementsPage()