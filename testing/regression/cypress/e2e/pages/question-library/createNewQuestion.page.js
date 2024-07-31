class CreateNewQuestionPage {
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

    clickAddQuestionBtn() {
        cy.get('.subsectionHeader').eq(0)
            .get('.addQuestionBtn').eq(0).click();
    }

    clickCreateNewQuestionButton() {
        cy.get('.addQuestionCreateNewBtn').eq(0).click();
    }

    selectLocalOption() {
        cy.get('#codeSet_LOCAL').should('have.value', 'LOCAL')
    }

    enterUniqueId() {
        cy.get('#uniqueId').type(`NBS${this.newText()}`);
    }

    enterUniqueName(){
        cy.get('#uniqueName').type(`new test question ${this.newText()}`);
    }

    subgroupClinicalInformation() {
        cy.get('#subgroup').select('', { force: true });
        cy.get('#subgroup').select('CLN', { force: true });
    }

    enterDescription() {
        cy.get('#description').type('new test description');
    }

    fieldTypeValueSet() {
        cy.get('.fieldType-option-0').eq(0).click();
    }

    verifyQuestionsAvailable() {
        cy.contains('User interface');
    }

    selectValueSet() {
        cy.get('#valueSet').select("140");
    }

    selectDefaultValueField() {
        cy.get('#defaultValue').select('CDC');
    }

    enterQuestionLabel() {
        cy.get('#label').type('new question label');
    }

    enterTooltip() {
        cy.get('#tooltip').type('new question tooltip');
    }

    selectDisplayType() {
        cy.get('[data-testid="displayType"]').select(1);
    }

    EnterDefaultLabelReport() {
        cy.get('#dataMartInfo\\.reportLabel').type('test default label')
    }

    EnterRdbColumnName() {
        cy.get('[name="dataMartInfo.rdbColumnName"]').type(`I${this.newText()}`);
    }

    dataMartColumnNamePopulated() {
        cy.get('[name="dataMartInfo.dataMartColumnName"]').invoke('val').should('not.be.empty')
    }

    toggleMessagingIncluded() {
        cy.get('label[for="messagingInfo.includedInMessage"]').click()
        cy.wait(1000)
    }

    enterMessageVariableId() {
        cy.get('#messagingInfo\\.messageVariableId').type('AA12345')
    }

    enterMessageLabel() {
        cy.get('[name="messagingInfo.labelInMessage"]').type('Assigning Authority');
    }

    selectCodeSystem() {
        cy.get('#messagingInfo\\.codeSystem').select('ENTITY_CD_HL7');
    }

    toggleRequiredInMessage() {
        cy.get('label[for="includedInMessage"]').click()
    }

    selectHl7DataType() {
        cy.get('#messagingInfo\\.hl7DataType').select('CE');
    }

    checkFieldsAreDisabled() {
        cy.contains('HL7 segment');
        cy.contains('Group number');
    }

    enterAdministrativeComments() {
        cy.get('#adminComments').type('test')
    }

    createAndApplyToPageBtnIsEnabled() {
        cy.get('.createAndDeployToPageBtn').eq(0)
    }

    clickCreateAndAddToPageBtn() {
        cy.get('.createAndDeployToPageBtn').eq(0).click();
    }

    checkConfirmationMessage() {
        cy.wait(4000);
        cy.contains('Successfully added');
    }

    navigatePageLibrary() {
        cy.visit('/page-builder/pages');
    }

    searchWithCreatedQuestion() {
        cy.get('#question-search').type('NBS');
    }

    clickSearchButton() {
        cy.get('#searchButton').click();
    }

    newText() {
        return Math.random().toString(36).substring(2, 8);
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

export const createNewQuestionPage = new CreateNewQuestionPage()