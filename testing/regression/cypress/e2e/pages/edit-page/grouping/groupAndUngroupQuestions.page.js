class GroupAndUngroupQuestions {

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

    checkForGroupedQuestions() {
        cy.get('body').then((ele) => {
            if(ele.find('[data-testid="questionGroupIndicator"]').length > 0) {
                cy.get('[data-testid="questionGroupIndicator"]').eq(0);
            } else {
                // section
                cy.get('.manageSections').eq(0).click();
                cy.get('.addNewSectionBtn').eq(0).click({ force: true });
                cy.get('.sectionName').eq(0).type("test new section");
                cy.get('.addSectionBtn').eq(0).click();
                cy.wait(2000)
                cy.get('.manageSectionsCloseBtn').eq(0).click();
                cy.wait(5000)

                // subsection
                cy.get('[data-testid="addNewSubsection"]').eq(0).click();
                const newSubsectionName = Math.random().toString(36).substring(2, 8);
                cy.get('[data-testid="subsectionName"]').eq(0).type(`New subsection name ${newSubsectionName}`);
                cy.get('[data-testid="addOrEditSubsectionBtn"]').eq(0).click();
                cy.wait(5000)

                // questions
                cy.get('.subsectionHeader').eq(0).get('.addQuestionBtn').eq(0).click();
                cy.wait(5000)
                cy.get('label[for="selection-0"]').click({ force: true });
                cy.wait(1000)
                cy.contains('Apply to page').click();
                cy.wait(5000)

                cy.get('.subsectionHeader').eq(0).get('.addQuestionBtn').eq(0).click();
                cy.wait(5000)
                cy.get('label[for="selection-0"]').click({ force: true });
                cy.wait(1000)
                cy.contains('Apply to page').click();
                cy.wait(5000)

                // grouping
                cy.get(".subsectionOptionsWithGrouped-").eq(0).click();
                cy.get('.subsectionOptionsWithGrouped-').eq(0)
                    .get('[data-testid="groupQuestionsOption"]').eq(0).click();
                this.renameBlockName()
                this.fillTableColumnWidths()
                cy.get('[data-testid="group-questions-submit-btn"]')
                            .filter(':visible').eq(0).click();
                cy.wait(5000)
                cy.get('[data-testid="questionGroupIndicator"]').eq(0);
            }
        })
    }

    clickMenuIcon(toGroup) {
        if(toGroup) {
            cy.get(".subsectionOptionsWithGrouped-").eq(0).click();
        } else {
            cy.get(".subsectionOptionsWithGrouped-grouped").eq(0).click();
        }
    }

    shouldSee(text1, text2, delay) {
        if(delay) {
            cy.wait(4000);
        }
        cy.contains(text1);
        if(text2) {
            cy.contains(text2);
        }
    }

    clickUngroupQuestionOption() {
        cy.get('.subsectionOptionsWithGrouped-grouped').eq(0)
        .get('[data-testid="ungroupQuestionsOption"]').eq(0).click();
    }

    clickUngroupBtn() {
        cy.get('[data-testid="confirmation-btn"]').filter(':visible:enabled').eq(0).click();
    }

    verifyQuestionUngrouped() {
        cy.get('.subsectionOptionsWithGrouped-ungrouped').eq(0);
    }

    clickEditSubsectionOption() {
        cy.get('.subsectionOptionsWithGrouped-grouped').eq(0)
            .get('[data-testid="editSubsectionOption"]').eq(0).click();
    }

    verifyEditSubsectionPrefilled() {
        cy.get('[name="blockName"]').eq(0).invoke('val').should('not.be.empty');
    }

    renameSubsection() {
        cy.get('[data-testid="editSubsectionModalSubsectionName"]').eq(0)
            .clear().type(`Edited section name ${this.newName()}`);
    }

    renameBlockName(newName) {
        cy.get('[data-testid="editSubsectionModalBlockName"]').eq(0)
            .clear().type(newName ? newName : `BLOCKNAME${this.newName()}`);
    }

    updateDatamartValue(num) {
        cy.get('[data-testid="editSubsectionModalDataMart"]').eq(0)
            .clear().type(num);
    }

    updateAppearsInTableValueToNo() {
        cy.get('[name="batches.0.width"]').eq(0).invoke('val').then(value => {
            let columnWidth = value;
            cy.get('[name="batches.0.appearsInTable"]').eq(0).select('N');
            cy.get('[name="batches.1.width"]').eq(0).invoke('val').then(value2 => {
                let updatedWidth = parseInt(value2) + parseInt(columnWidth)
                cy.get('[name="batches.1.width"]').eq(0).clear().type(updatedWidth);
            });
        });
    }

    checkTableColumnPercentage() {
        cy.get('[data-testid="columnMustTotal"]').eq(0).should('have.text', '100%');
    }

    newName() {
        return Math.random().toString(36).substring(2, 12);
    }

     verifyGroupedSubmitButtonEnabled() {
        cy.get('[data-testid="subsection-submit-btn"]');
    }

    clickGroupedSubmitBtn() {
        cy.get('[data-testid="subsection-submit-btn"]')
            .filter(':visible').eq(0).click();
    }

    verifyUngroupedSubmitButtonEnabled() {
        cy.get('[data-testid="group-questions-submit-btn"]');
    }

    clickUngroupedSubmitBtn() {
        cy.get('[data-testid="group-questions-submit-btn"]')
            .filter(':visible').eq(0).click();
    }

    clickGroupQuestionOption() {
        cy.wait(2000)
        cy.get('.subsectionOptionsWithGrouped-ungrouped').eq(1)
        .get('[data-testid="groupQuestionsOption"]').eq(0).click();
    }

    verifySubsectionNamePrefilled() {
        cy.get('[data-testid="editSubsectionModalSubsectionName"]').eq(0)
            .invoke('val').should('not.be.empty');
    }

    verifySubsectionVisible(visible) {
        cy.get('[name="visible"]').eq(0).should('have.value', visible);
    }

    clickPreviewBtn() {
        cy.contains('Preview').eq(0).click();
    }

    fillTableColumnWidths() {
        cy.get('tbody[data-testid="group-questions-tbody"] tr td:last-child [data-testid="group-questions-width"]')
            .its('length')
            .then((length) => {
                const distributeRandomly = (total, numberOfRows) => {
                    const initialCommonValue = Math.floor(total/numberOfRows);
                    const initialCommonValuesTotal = initialCommonValue * numberOfRows
                    const leftOver = total - initialCommonValuesTotal
                    const lastValue = initialCommonValue + leftOver
                    return [initialCommonValue, lastValue]
                };
                const distribution = distributeRandomly(100, length);
                cy.get('tbody[data-testid="group-questions-tbody"] tr')
                    .each(($row, rowIndex) => {
                    cy.get($row)
                        .get('td:last-child [data-testid="group-questions-width"]')
                        .eq(rowIndex)
                        .clear()
                        .type(rowIndex === length-1 ? distribution[1]: distribution[0]);
                });
            });
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

export const groupAndUngroupQuestions = new GroupAndUngroupQuestions()