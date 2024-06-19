class AddSectionPage {
    navigateEditPage () {
        cy.visit('/page-builder/pages');
        cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
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