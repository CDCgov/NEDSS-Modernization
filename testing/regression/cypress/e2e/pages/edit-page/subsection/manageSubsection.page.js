class ManageSubsectionPage {

    navigateEditPage () {
        cy.visit('/page-builder/pages');
        cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
        cy.get('.editDraftBtn').eq(0).click();
    }

    openManageSubsectionWindow() {
        cy.get('[data-testid="manageSubsectionGearIcon-yes"]').eq(0).click();
        cy.contains('Manage subsections');
    }

    clickEditSubsectionIcon() {
        cy.get('.subsectionHeader').eq(0)
            .get('[data-testid="subsectionTileEditIcon"]').eq(0).click();
    }

    verifyEditSubsectionIsVisible() {
        cy.contains('Edit subsection')
    }

    verifyButtonsAreVisible() {
        cy.contains('Cancel');
        cy.contains('Save changes');
    }

    checkSaveButtonDisabledByDefault() {
        cy.get('[data-testid="confirmation-btn"]')
            .filter(':visible').eq(0).should('be.disabled');
    }

    updateSubsectionName() {
        const newSubsectionName = Math.random().toString(36).substring(2, 8);
        cy.get('[data-testid="subsectionName"]')
            .filter(':visible')
            .eq(0)
            .then((btn) => {
                cy.wrap(btn).clear()
                    .type(`Subsection name edited ${newSubsectionName}`);
            });
    }

    checkSaveButtonEnabled() {
        cy.get('[data-testid="confirmation-btn"]');
    }

    clickSaveBtn() {
        cy.get('[data-testid="confirmation-btn"]')
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
            .get('[data-testid="subsectionTileDeleteIcon"]').eq(0).click();
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
        const dataTestId = ``;
        cy.get('.subsectionHeader').eq(0)
            .get(`[data-testid="subsectionTileVisibilityIcon-${onOrOff}"]`).eq(0).click();
    }

    checkVisibilityIconTurnedOff(visibility) {
        const onOrOff = visibility ? 'on' : 'off'
        cy.get('.subsectionHeader').eq(0)
            .get('[data-testid="subsectionTileVisibilityIcon-off"]').eq(0)
    }

    verifyVisibilitySuccessMessage(text) {
        cy.wait(2000);
        cy.contains(text);
    }

    clickAddNewSubsectionBtn() {
        cy.get('[data-testid="addNewSubsection"]').eq(0).click();
    }

    checkAddSubsectionWindowDisplayed() {
        cy.contains('Add subsection');
    }

    enterNewSubsectionName() {
        const newSubsectionName = Math.random().toString(36).substring(2, 8);
        cy.get('[data-testid="subsectionName"]').eq(0).type(`New subsection name ${newSubsectionName}`);
    }

    toggleVisibilityRadioBtn() {
        cy.contains('Not visible');
        cy.contains('Visible');
    }

    clickAddSubsectionBtn() {
        cy.get('[data-testid="addOrEditSubsectionBtn"]').eq(0).click();
    }

    verifyAddingSubsectionSuccessMessage() {
        cy.wait(2000);
        cy.contains('You have successfully added subsection');
    }

    clickDragAndDropIcon() {
        cy.get('.manage-sections').eq(0)
            .get('[data-testid="dragAndDropIcon"]').eq(0).click();
    }

    checkDragAndDrop() {
        cy.get('.manage-sections').eq(0)
            .get('[data-testid="dragAndDropIcon"]').eq(0)
            .trigger('mousedown').trigger('mouseup');
    }

    closeManageSubsectionWindow() {
        cy.get('.manage-sections').eq(0)
            .get('[data-testid="manageSubsectionCloseBtn"]').eq(0).click();
    }

    checkOnEditPage() {
        cy.contains('Subsection name');
    }


}

export const manageSubsectionPage = new ManageSubsectionPage()