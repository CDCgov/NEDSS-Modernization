class ManageSubsectionPage {

    navigateEditPage () {
        cy.visit('/page-builder/pages');
        cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
        cy.get('.editDraftBtn').eq(0).click();
    }

    openManageSubsectionWindow() {
        cy.get('.manageSubsectionGearIcon-yes').eq(0).click();
        cy.contains('Manage subsections');
    }

    clickEditSubsectionIcon() {
        cy.get('.subsectionHeader').eq(0)
            .get('.subsectionTileEditIcon').eq(0).click();
    }

    verifyEditSubsectionIsVisible() {
        cy.contains('Edit subsection')
    }

    verifyButtonsAreVisible() {
        cy.contains('Cancel');
        cy.contains('Save changes');
    }

    checkSaveButtonDisabledByDefault() {
        cy.get('.saveChangesSubsectionBtn').eq(0).should('be.disabled');
    }

    updateSubsectionName() {
        const newSubsectionName = Math.random().toString(36).substring(2, 8);
        cy.get('.subsectionName')
            .filter(':visible')
            .eq(0)
            .then((btn) => {
                cy.wrap(btn).clear()
                    .type(`Subsection name edited ${newSubsectionName}`);
            });
    }

    checkSaveButtonEnabled() {
        cy.get('.saveChangesSubsectionBtn');
    }

    clickSaveBtn() {
        cy.get('.saveChangesSubsectionBtn')
        .filter(':visible:enabled').eq(0).click();
    }

    checkManageSubsectionWindowVisible() {
        cy.contains('Manage subsections');
    }

    checkSubsectionNameUpdated() {
        cy.contains('Subsection name edited');
    }

    clickDeleteSubsectionIcon() {
        cy.get('.subsectionHeader').eq(0)
            .get('.subsectionTileDeleteIcon').eq(0).click();
    }

    showWarningMessageOnSubsectionDelete(text) {
        cy.contains('Subsection cannot be deleted').then((ele) => {
            if(ele.length < 1) {
                cy.contains(text);
            }
        });
    }

    clickOkLink() {
        cy.contains('Subsection cannot be deleted').then((ele) => {
            if(ele.length > 1) {
                cy.get('.subsectionHeader').eq(0)
                    .get('.subsectionOkLink').eq(0).click();
            }
        });
    }

    checkButtonsAreVisible(text, text1) {
        cy.contains('Subsection cannot be deleted').then((ele) => {
            if(ele.length < 1) {
                cy.contains('Are you sure you want to delete').should('contain', text);
            }
        });

    }

    clickVisibilitySubsectionIcon(visibility) {
        const onOrOff = visibility ? 'on' : 'off'
        cy.get('.subsectionHeader').eq(0)
            .get(`.subsectionTileVisibilityIcon-${onOrOff}`).eq(0).click();
    }

    checkVisibilityIconTurnedOff(visibility) {
        const onOrOff = visibility ? 'on' : 'off'
        cy.get('.subsectionHeader').eq(0)
            .get('.subsectionTileVisibilityIcon-off').eq(0)
    }

    verifyVisibilitySuccessMessage(text) {
        cy.contains(text);
    }
}

export const manageSubsectionPage = new ManageSubsectionPage()