class DeleteSectionPage {

    navigateEditPage () {
        cy.visit('/page-builder/pages');
        cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
        cy.get('.editDraftBtn').eq(0).click();
    }

    clickMenuIcon() {
        cy.get(".moreOptionsSection-yes").eq(0).click();
    }

    clickDeleteSubsection() {
        cy.get(".moreOptionsSection-yes").eq(0)
            .get('.deleteSectionBtn').eq(0).click();
    }

    verifyWaringMessage(title, description) {
        cy.contains(title);
        cy.contains(description);
    }

    verifyOkayBtnShowing(text) {
        cy.contains(text)
    }

    clickMenuIconWithoutSubsections() {
        cy.get(".moreOptionsSection-no").eq(0).click();
    }

    clickDeleteSubsectionWithoutSubsections() {
        cy.get(".moreOptionsSection-no").eq(0)
            .get('.deleteSectionBtn').eq(0).click();
    }

    verifyWaringMessageWithoutSubsections(description1, description2) {
        cy.contains(description1);
        cy.contains(description2);
    }

    clickYesDeleteBtnWithoutSubsections() {
        cy.get('.usa-modal').filter(':visible')
            .contains('button', 'Yes, delete').click();
    }

    verifyMessageSectionDeleted(text) {
        cy.contains(text);
    }

}

export const deleteSectionPage = new DeleteSectionPage()