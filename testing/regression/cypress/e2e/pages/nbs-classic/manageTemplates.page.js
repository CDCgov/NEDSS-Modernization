class ClassicManageTemplatePage {
    navigateToTemplateLibrary() {
        cy.contains('System Management').click();

        // Expand the "Page Management" subsection
        cy.get('table[id="systemAdmin5"]').find('a[class="toggleIconHref"]').eq(0).click();
        cy.contains('Manage Templates').click();
    }

    clickImportBtnTemplateLibrary() {
        cy.get('input[type="button"][value="Import"]').eq(0).click();
    }

    clickChooseFileBtnTemplateLibrary() {
        cy.get('input[type="file"][name="importFile"]').click();
    }

    clickFilterIconTemplateLibrary() {
        cy.get('.multiSelect').eq(0).click();
    }

    enterFilterTextInTemplateNameFilterInbox(text) {
        cy.get('#SearchText1').eq(0).type(text);
    }

    clickOKbtnTemplateLibrary() {
        cy.get('#b2SearchText1').eq(0).click();
    }

    clickTemplateInTemplateList() {
        cy.get('table#parent tbody tr td a').eq(1).click();
    }

    clickViewRulesBtnTemplateLibrary() {
        cy.get('input[type="button"][name="View Rules"]').eq(0).click();
    }

    verifyRulesListedInResultsPage() {
        cy.contains('Rules');
    }

    checkTemplateNamesContain(text) {
        cy.get('table.dtTable tbody tr td:nth-child(2)').each(($td) => {
            const templateName = $td.text().trim();
            expect(templateName, 'Template Name should contain "' + text + '"').to.include(text);
        });
    }
}
export default new ClassicManageTemplatePage();
