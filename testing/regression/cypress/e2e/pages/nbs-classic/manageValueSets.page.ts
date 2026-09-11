class ClassicManageValueSetsPage {
    navigateToValueSetsLibrary() {
        cy.contains('System Management').click();

        // Expand the "Page Management" subsection
        cy.get('table[id="systemAdmin5"]').find('a[class="toggleIconHref"]').eq(0).click();
        cy.contains('Manage Value Sets').click();
    }

    clickAddNewBtn() {
        cy.get('input[type="submit"][value="Add New"]').eq(0).click();
    }

    fillTheDetailsValueSetFields(type: string) {
        const newName = this.newName();
        if (type === 'LOCAL') {
            cy.get('input[name="ValStF_textbox"]').type('Locally Defined');
        } else if (type === 'PHIN') {
            cy.get('input[name="ValStF_textbox"]').type('PHIN Standard');
        }
        cy.get('#ValSCF').type(`Set code ${newName}`);
        cy.get('#ValSNF').type(`Set Name ${newName}`);
        cy.get('#ValSDF').type('Description for new value set');
    }

    clickSubmitBtnValueSetForm() {
        cy.get('#submitA').eq(0).click();
    }

    clickFilterBtnValueSetLibrary() {
        cy.get('.multiSelect').eq(2).click();
    }

    enterFilterTextValueSetLibrary(text: string) {
        cy.get('#SearchText2').eq(0).type(text);
    }

    clickFilterOkBtnValueSetLibrary() {
        cy.get('#b1SearchText2').eq(0).click();
    }

    clickValueSetInValueSetList() {
        cy.get('#parent tbody tr td a').eq(0).click();
    }

    clickActiveValueSet() {
        cy.get('._indicator_1vvtd_1', { timeout: 10000 }).should('not.exist');

        cy.get('table.dtTable tbody tr').each(($row) => {
            const status = Cypress.$($row).find('td:last-child').text().trim();
            if (status === 'Active') {
                cy.wrap($row).find('td:first-child a img[src="page_white_text.gif"]').click();
                return false; // Stop iteration after clicking
            }
        });
    }

    clickCollapseSubsectionsInValueSetList() {
        cy.wait(2000);
        cy.contains('Collapse Subsections').click();
    }

    clickExpandSubsectionsInValueSetList() {
        cy.wait(2000);
        cy.contains('Expand Subsections').click();
    }

    clickAddNewBtnInValueSetConceptSection() {
        cy.window().then((win) => {
            cy.stub(win, 'open').callsFake((url) => {
                cy.visit(url);
            });
        });

        cy.get('#submitCr').click();
    }

    storeValueSetCount() {
        cy.get('.singlepagebanner b')
            .first()
            .then(($el) => {
                const text = $el.text();
                cy.log('Single page banner text: ' + text);

                const match = text.match(/of\s+(\d+)/);
                if (match && match[1]) {
                    const count = parseInt(match[1], 10);
                    Cypress.env('valueSetCount', count);
                    cy.log('Stored value set count: ' + count);
                } else {
                    throw new Error('Value set count not found in: ' + text);
                }
            });
    }

    verifyValueSetIncreased() {
        const initialCount = Cypress.env('valueSetCount');

        cy.get('.singlepagebanner b')
            .first()
            .then(($el) => {
                const text = $el.text();
                const match = text.match(/of\s+(\d+)/);
                if (match && match[1]) {
                    const newCount = parseInt(match[1], 10);
                    cy.log('Initial: ' + initialCount + ', New: ' + newCount);
                    expect(newCount).to.equal(initialCount + 1);
                } else {
                    throw new Error('Value set count not found in: ' + text);
                }
            });
    }

    fillTheDetailsNewValueSetConcept() {
        const newName = this.newName();
        Cypress.env('newValueSet', newName);
        cy.get('#ValLC').type(`local code ${newName}`);
        cy.get('#ValLDN').type(`display name ${newName}`);
        cy.get('#ValSDN').type(`short name ${newName}`);
        //    cy.get('#ValSCF').type(`concept code ${newName}`)
        //    cy.get('#ValSNF').type(`name ${newName}`)
        //    cy.get('#ValPSNF').type(`pref name ${newName}`)
        cy.get('#CodeSNF_DD').select(1);
    }

    clickSubmitBtnInValueSetConceptForm() {
        cy.get('#submitA').click();
        cy.wait(2000);
        cy.visit('/nbs/ManageCodeSet.do?method=ViewValueSetLib&initLoad=true');
        this.clickValueSetInValueSetList();
    }

    clickMakeInactiveInValueSet() {
        cy.get('body').then(($body) => {
            if ($body.find('input[type="button"][value="Make Inactive"]').length > 0) {
                cy.get('input[type="button"][value="Make Inactive"]').eq(0).click();
            }
        });
    }

    verifyValueSetNamesContain(text: string) {
        cy.get('table.dtTable tbody tr td:nth-child(5)').each(($td) => {
            const valueSetName = $td.text().trim();
            cy.log('Value Set Name: ' + valueSetName);
            expect(valueSetName, 'Value Set Name should contain "' + text + '"').to.include(text);
        });
    }

    verifyValueSetPageExpanded() {
        cy.get('a.toggleHref').should('have.text', 'Collapse Subsections');
        cy.get('#subsec1 tbody').should('have.attr', 'style').and('not.include', 'display: none');
        cy.get('#subsec2 tbody').should('have.attr', 'style').and('not.include', 'display: none');
    }

    verifyValueSetPageCollapsed() {
        cy.get('a.toggleHref').should('have.text', 'Expand Subsections');
        cy.get('#subsec1 tbody').should('have.attr', 'style', 'display: none;');
        cy.get('#subsec2 tbody').should('have.attr', 'style', 'display: none;');
    }
    newName() {
        return Math.random().toString(36).substring(2, 8);
    }
}
export default new ClassicManageValueSetsPage();
