class AddSectionPage {
    navigateEditPage () {
        cy.visit('/page-builder/pages/1010436/edit')
    }

    openAddSectionPopup() {
        cy.get('.addSection').eq(0).click();
    }

    enterSectionName() {
        cy.get('.sectionName').eq(0).type("test new section");
    }

    clickAddSectionBtn() {
        cy.get('.addSectionBtn').eq(0).click();
    }

    checkAlertIsShowing() {
        cy.wait(1000)
        cy.get(".usa-alert")
    }

    checkNewlyCreatedSectionShowing() {
        cy.contains("test new section")
    }

}

export const addSectionPage = new AddSectionPage()