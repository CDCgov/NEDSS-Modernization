class BusinessRulesPage {
    navigateToBusinessRulesPage () {
        cy.visit('/page-builder/pages');
        cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
        cy.contains('button', 'Business rules').click();
    }

    viewsBusinessRulesPage() {
        cy.get(".business-rules-library")
    }

    checkLogicColumnPopulated() {
        cy.contains("Logic");
    }

    displayLogics(logic) {
        cy.get('.business-rules-library')
            .invoke('text')
            .then((text) => {
                if (!text.includes(logic)) {
                    cy.contains("Logic");
                } else {
                    cy.contains(logic);
                }
            })
    }

    checkFunctionColumnPopulated() {
        cy.get('.business-rules-library')
            .invoke('text')
            .then((text) => {
                if (!text.includes("Disable")) {
                    cy.contains("Disable");
                } else if (!text.includes("Enable")) {
                   cy.contains("Enable");
               } else if (!text.includes("Hide")) {
                    cy.contains("Hide");
               } else if (!text.includes("Unhide")) {
                    cy.contains("Unhide");
               }
            })
    }

    checkTargetColumnPopulated() {
        cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr) => {
            expect($tr.find("td").eq(4).text()).to.exist;
        });
    }

    enterUniqueNameInSearchField() {
        cy.get('#business-rules-search').type('date of birth');
    }

    clickBusinessRulesSearchBtn() {
        cy.get('[data-testid="businessRulesSearchBtn"]').click();
    }

    checkBusinessRulesListDisplayed() {
        cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr) => {
            expect($tr.find("td").eq(0).text()).to.exist;
        });
    }

    enterUniqueIdInSearchField() {
        cy.get('#business-rules-search').type('dem115');
    }

    checkBusinessRulesLibraryDefaultRows() {
        cy.contains("10");
    }

    selectRowsFromDisplayDropdown(selection) {
        cy.get('#range-toggle').select(selection);
    }

    checkBusinessRulesListMatchingRows(numOfRows) {
        cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr) => {
            expect($tr.length).to.be.lessThan(numOfRows);
        });
    }

    clickAddBusinessRuleBtn() {
        cy.contains('button', 'Add new business rule').click();
    }

    addNewBusinessRulesModalDisplayed() {
        cy.contains('Add new business rules');
    }

    selectEnabled() {
        cy.get('.fieldType-option-0').eq(0).click();
    }

    completeAllRequiredFields(logic) {
        cy.get('[data-testid="searchSourceQuestionBtn"]').eq(0).click();
        cy.get('[data-testid="listedSections"]').eq(0).click({ force: true });
        cy.get('[data-testid="listedSubsections"]').eq(0).click({ force: true });
        cy.get('[for="sourceId0"]').click({ force: true });
        cy.get('[data-testid="sourceQuestionModalContinueBtn"]').eq(0).click({ force: true });
        cy.wait(1000);
        cy.get('[data-testid="LogicSelectDropdown"]').eq(0).select('NOT_EQUAL_TO');
        cy.get('.multi-select__input-container').eq(0).click();
        cy.get('#react-select-3-option-0').click();
        cy.get('.multi-select__input-container').eq(0).click();
        cy.get('[data-testid="searchTargetQuestionBtn"]').eq(0).click();
        cy.wait(2000);
        cy.get('[data-testid="listedSectionsTarget"]').eq(0).click({ force: true });
        cy.get('[data-testid="listedSubsectionsTarget"]').eq(0).click({ force: true });
        cy.get('[for="hots1"]').click({ force: true });
        cy.get('[data-testid="targetQuestionModalContinueBtn"]').eq(0).click({ force: true });
    }

    clickAddToLibraryBtnNewBusinessRulesModel() {
        cy.get('[data-testid="AddToLibraryNewBusinessRulesModel"]').eq(0).click();
    }

    checkRedirectedToLibraryPage() {
        cy.contains('Add new business rule');
    }

    selectDisabled() {
        cy.get('.fieldType-option-1').eq(0).click();
    }

    selectHide() {
        cy.get('.fieldType-option-3').eq(0).click();
    }

    selectUnhide() {
        cy.get('.fieldType-option-4').eq(0).click();
    }

    selectRequireIf() {
        cy.get('.fieldType-option-5').eq(0).click();
    }

    clickQuestion() {
        cy.get('table.business-rules-table tbody tr td a').eq(0).click();
    }

    editBusinessRulesPageDisplayed() {
        cy.contains('Edit business rules', { timeout: 8000 });
    }

    clickDeleteEditBusinessRulesPage() {
        cy.get('[data-testid="deleteBtnEditBusinessRulesPage"]').eq(0).click();
    }

    checkWarningMessage(text) {
        cy.contains(text);
    }

    clickYesDeleteBtnEditBusinessRulesPage() {
        cy.get('[data-testid="confirmation-btn"]').eq(0).click();
    }

    clickCancelBtnEditBusinessRulesPageConfirmationModal() {
        cy.get('[data-testid="cancel-btn"]').eq(0).click();
    }

    clickCancelBtnEditBusinessRulesPage() {
        cy.get('[data-testid="cancelEditBusinessRulesModel"]').eq(0).click();
    }

    selectLogicFromDropdown() {
        cy.wait(1000);
        cy.get('[data-testid="LogicSelectDropdown"]')
            .invoke('prop', 'selectedIndex')
            .then((selectedIndex) => {
                if(selectedIndex !== 1) {
                    cy.get('[data-testid="LogicSelectDropdown"]').select(1);
                } else {
                    cy.get('[data-testid="LogicSelectDropdown"]').select(2);
                }
            })
    }

    selectTargetQuestion() {
        cy.get('[data-testid="targetQuestionEditBtn"]').eq(0).click();
        cy.wait(2000);
        cy.get('[data-testid="listedSectionsTarget"]').eq(0).click({ force: true });
        cy.get('[data-testid="listedSubsectionsTarget"]').eq(0).click({ force: true });
        cy.get('[for="hots1"]').click({ force: true });
        cy.get('.usa-checkbox__input')
            .then((items) => {
                cy.log('xox-items.length', items.length)
                if(items.length > 9) {
                    cy.get('[for="hots1"]').click({ force: true });
                    cy.get('[for="sourceId0"]').click({ force: true });
                } else {
                    cy.get('[for="hots1"]').click({ force: true });
                }
            })
        cy.get('[data-testid="targetQuestionModalContinueBtn"]').eq(0).click({ force: true });
    }

    targetQuestionUpdated() {
        cy.get('[data-testid="targetQuestionEditBtn"]').eq(0)
    }

    clickUpdateBtnEditBusinessRulePage() {
        cy.get('[data-testid="updateBtnEditBusinessRulesPage"]').eq(0).click({ force: true });
    }

}

export const businessRulesPage = new BusinessRulesPage()
