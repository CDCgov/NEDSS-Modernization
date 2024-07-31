class DeleteSectionPage {
    navigateEditPage () {
        this.navigateToPreviewPageWithStatusInitialDraft()
        cy.get("body").then($body => {
            if ($body.find("#create-new-draft-button").length > 0) {
                cy.get("create-new-draft-button").then($button => {
                    if ($button.is(':visible')){
                        $button.click()
                        cy.get('.editDraftBtn').eq(0).click();
                    }
                })
            } else {
                cy.get('.editDraftBtn').eq(0).click();
            }
        });
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
        cy.get('body').then((body) => {
            if(body.find('.moreOptionsSection-no').length > 0) {
                cy.get(".moreOptionsSection-no").eq(0).click();
            } else {
                cy.get('.addSection').eq(0).click();
                cy.get('.sectionName').eq(0).type("test new section");
                cy.get('.addSectionBtn').eq(0).click();
                cy.wait(5000)
                cy.get(".moreOptionsSection-no").eq(0).click();
            }
        })
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
        cy.wait(1000)
        cy.contains(text);
    }

    navigateToPreviewPageWithStatusInitialDraft() {
        cy.visit('/page-builder/pages');
        cy.wait(2000);
        cy.get('#range-toggle').select('100')
        cy.wait(5000);
        cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr, index) => {
            if($tr.find("td").eq(3).text() === "Initial Draft") {
                cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                return false
            }
        });
    }

}

export const deleteSectionPage = new DeleteSectionPage()