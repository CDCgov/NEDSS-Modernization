class AddEditSearchDeleteQuestion {

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

    checkSubsectionExpanded() {
        cy.get('.iconExpandLess')
    }

    clickAddQuestionBtn() {
        cy.get('.subsectionHeader').eq(0)
            .get('.addQuestionBtn').eq(0).click();
    }

    addQuestionModalDisplays() {
        cy.contains('Add question')
    }

    clickCreateNewQuestionButton() {
        cy.get('.addQuestionCreateNewBtn').eq(0).click();
    }

    checkAddQuestionModalVisibility() {
        cy.contains('Add question');
    }

    fillAllRequiredFields({ withUniqueID, fieldTypeNumeric, fieldTypeDatePicker } = {}) {
        if(withUniqueID) {
            cy.get('#uniqueId').type('NBS104');
        }
        const newQuestionName = Math.random().toString(36).substring(2, 8);
        cy.get('#uniqueName').type(`new test question ${newQuestionName}`);
        cy.get('#label').type('new question label');
        cy.get('.subgroupSelect').eq(0).select(1, { force: true });
        cy.get('#description').type('new test description');
        if (fieldTypeNumeric) {
            cy.get('.fieldType-option-1').eq(0).click();
            cy.get('#fieldLength').type(5);
        } else if(fieldTypeDatePicker){
            cy.get('.fieldType-option-3').eq(0).click();
        } else {
            cy.get('.fieldType-option-0').eq(0).click();
            cy.get('#valueSet').select(2);
        }
        cy.get('#tooltip').type('new question tooltip');
        cy.get('[data-testid="displayType"]').select(1);
        cy.get('.defaultLabelInReport').eq(0).type('label report');
        cy.get('.rdbColumnName').eq(0).type(`NAME${newQuestionName}`);
    }

    clickCreateAndAddToPageBtn() {
        cy.get('.createAndDeployToPageBtn').eq(0).click();
    }

    checkConfirmationMessage(text) {
        cy.wait(4000);
        cy.contains(text);
    }

    checkNewlyAddedQuestionDisplayed() {
        cy.contains('new question label');
    }

    clickQuestionEditBtn() {
        cy.get('.questionEditButton').eq(0).click();
    }

    editQuestionModalDisplayed() {
        cy.contains('Edit question');
    }

    updateQuestionDetails() {
        cy.get('#label').clear().type('question label edited');
        cy.get('#tooltip').clear().type('question tooltip edited');
    }

    clickEditQuestionSaveBtn() {
        cy.get('.editQuestionSaveBtn').eq(0).click({ force: true });
    }

    closeEditQuestionModal() {
        cy.contains('Edit question').then((ele) => {
            if(ele.length < 1) {
                cy.contains('Edit question').should('not.visible');
            } else {
                cy.contains('button', 'Cancel').eq(0).click({ force: true });
            }
        });
    }

    clickQuestionDeleteBtn() {
        cy.get('.delete-btn').eq(0).click();
    }

    checkQuestionDeleteModalText(text, text1) {
        cy.contains(text);
        cy.contains(text1);
    }

    clickConfirmBtnToDeleteQuestion() {
        cy.get('.questionDeleteConfirmBtn').eq(0).click();
    }

    displaysQuestionDeleteSuccessMessage(text) {
        cy.wait(1000);
        cy.contains(text);
    }

    checkPageStayedOnEdit() {
        cy.get('.subsectionHeader');
    }

    errorMessageForDuplicateUniqueID(text) {
         cy.contains('Error');
    }

    enterExistingQuestionUniqueID() {
        cy.get('#question-search').type('NBS104');
    }

    clickQuestionSearchBtn() {
        cy.get('#searchButton').click();
    }

    showEmptyQuestionSearchList() {
        cy.contains('Showing 0 of 0');
    }

    showCreateNewSection(text, text1) {
        cy.contains(text);
        cy.contains(text1);
    }

    enterInactiveQuestionInSearchField() {
        cy.get('#question-search').type('ARD1157');
    }

    InactiveQuestionNotDisplayed() {
        cy.contains('Showing 0 of 0');
    }

    checkUniqueIDisBlank() {
        cy.get('#uniqueId').should('not.have.value');
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

export const addEditSearchDeleteQuestion = new AddEditSearchDeleteQuestion()