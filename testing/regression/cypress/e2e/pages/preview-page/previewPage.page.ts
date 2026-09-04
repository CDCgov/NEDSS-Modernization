class PreviewPagePage {
    navigateToPreviewPage() {
        this.navigateToPreviewPageWithStatusInitialDraft();
    }

    viewsElementsOnPreviewPage(content: string, type: string) {
        if (type === 'Icon') {
            if (content === 'Preview') {
                cy.get('[aria-label="Preview in NBS Classic"]');
            } else if (content === 'Clone') {
                cy.get('[aria-label="Page porting"]');
            } else if (content === 'Print') {
                cy.get('[aria-label="Print this page"]');
            }
        } else {
            cy.contains(content);
        }
    }

    loadPage() {
        cy.visit('/page-builder/pages');
        cy.get('#range-toggle').select('100');
        cy.get('table[data-testid=table] tr').should('have.length.above', 38);
    }

    navigateToPreviewPageWithStatusInitialDraft() {
        this.loadPage();
        cy.get('table[data-testid=table]')
            .eq(0)
            .find('tbody tr')
            .each(($tr, index) => {
                if ($tr.find('td').eq(3).text() === 'Initial Draft') {
                    cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                    return false;
                }
            });
    }

    navigateToPreviewPageWithStatusPublished() {
        this.loadPage();
        function search() {
            let isExist = false;
            cy.get('table[data-testid=table]')
                .eq(0)
                .find('tbody tr')
                .then(($rows) => {
                    cy.wrap($rows).each(($tr, index) => {
                        const statusText = $tr.find('td').eq(3).text();
                        if (statusText === 'Published') {
                            isExist = true;
                            cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                            return false;
                        }
                    });
                })
                .then(() => {
                    if (!isExist) {
                        cy.contains('Next').eq(0).click();
                        search();
                    }
                });
        }
        search();
    }

    navigateToPreviewPageWithStatusPublishedWithDraft() {
        this.loadPage();
        cy.get('table[data-testid=table]')
            .eq(0)
            .find('tbody tr')
            .each(($tr, index) => {
                if ($tr.find('td').eq(3).text() === 'Published with Draft') {
                    cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                    return false;
                }
            });
    }

    clickOnEditPageDetails() {
        cy.get('footer button').eq(0).click();
    }

    checkNavigatedToPageDetails() {
        cy.contains('Page Details');
    }

    checkConditionsField() {
        cy.get('.multi-select__input-container').eq(0).click({ force: true });
    }

    checkRemoveOrAddConditions() {
        cy.selectDropdownByLabel('Condition(s)', 'Cholera');
    }

    checkPageNameField(check?: boolean) {
        cy.get('#name').click();
        if (check) {
            cy.get('#name').clear();
            this.selectPageName();
        }
    }

    checkPageNameFieldMaxLength() {
        cy.get('#name')
            .invoke('text')
            .then((text) => {
                if (text) {
                    expect(text.length).to.be.lessThan(50);
                }
            });
    }

    checkEventTypeField() {
        cy.findByLabelText('Event type').should('be.disabled');
    }

    checkReportingMechanismField() {
        cy.get('#messageMappingGuide').should('be.exist');
    }

    selectAnotherOptionFromReportingMechanism() {
        cy.get('#messageMappingGuide').select(2);
    }

    checkPageDescriptionField() {
        cy.get('#description').should('be.exist');
    }

    checkPageDescriptionFieldMaxLength() {
        cy.get('#description')
            .invoke('text')
            .then((text) => {
                if (text) {
                    expect(text.length).to.be.lessThan(2000);
                }
            });
    }

    checkDatamartNameField() {
        cy.get('#datamart').should('be.exist');
    }

    checkDatamartNameFieldMaxField() {
        cy.get('#datamart')
            .invoke('text')
            .then((text) => {
                if (text) {
                    expect(text.length).to.be.lessThan(2000);
                }
            });
    }

    clickCancelBtnPageDetailsPage() {
        cy.contains('Cancel').click();
    }

    checkNavigatedBackToPreviewPage() {
        cy.contains('Page Details');
    }

    checkChangesOnPreviewPage() {
        cy.contains('Page information');
    }

    clickSaveChangesBtnPageDetailsPage() {
        this.clickOnEditPageDetails();
        cy.get('#name').type('test');
        cy.contains('Save').click();
    }

    checkChangesOnPreviewPageStatusType() {
        cy.contains('PREVIEWING:');
    }

    checkEditDraftPage() {
        cy.contains('Edit draft');
    }

    clickOnMetadataBtn() {
        this.clickCancelBtnPageDetailsPage();
        cy.contains('Metadata').click();
    }

    clickOnHistoryTab() {
        cy.get('nav div ul li').eq(1).click();
    }

    checkHistoryInfo(info: string) {
        cy.get('aside section div')
            .eq(0)
            .invoke('text')
            .then((text) => {
                if (text.includes(info)) {
                    cy.contains(info);
                }
            });
    }

    userSeeOnlyTenRows() {
        cy.get('aside section div')
            .eq(0)
            .invoke('text')
            .then((text: any) => {
                if (text.includes(10)) {
                    cy.contains(10);
                }
            });
    }

    checkRowOptionsAvailable() {
        cy.get('aside section div')
            .eq(0)
            .invoke('text')
            .then((text: any) => {
                if (text.includes(20)) {
                    cy.contains(20);
                }
            });
    }

    clickCreateNewPageButton() {
        cy.visit('/page-builder/pages');
        cy.get('.createNewPageButton').eq(0).click();
    }
    userViewsEventTypeField() {
        cy.get('#eventType');
    }

    selectEventType(type = 'INV') {
        cy.get('#eventType').select(type);
    }

    viewTextOnPage(text: string) {
        cy.contains(text);
    }

    selectCondition() {
        this.selectEventType();
        cy.selectDropdownByLabel('Condition(s)', 'African Tick Bite Fever');
    }

    selectPageName() {
        cy.get('#name').click({ force: true });
        const newPageName = Math.random().toString(36).substring(2, 8);
        cy.get('#name').type(`New page test ${newPageName}`);
    }

    selectTemplate() {
        cy.get('#templateId')
            .find('option')
            .eq(1)
            .then((option) => {
                cy.get('#templateId').select(option.attr('value')!);
            });
    }

    selectReportingMechanism() {
        cy.get('#messageMappingGuide')
            .find('option')
            .eq(1)
            .then((option) => {
                cy.get('#messageMappingGuide').select(option.attr('value')!);
            });
    }
    enterPageDescription() {
        cy.get('#pageDescription').type('This page is for diagnosis');
    }

    clickCreatePageButton() {
        cy.get('.createPage').eq(0).click();
        cy.findByRole('heading', { name: 'Create new page' }).should('be.visible');
    }

    clickPreviewAfterNewlyCreatedPage() {
        cy.contains('Preview').click();
    }

    clickPublishBtn() {
        cy.contains('button', 'Publish').click();
    }

    clickPublishBtnOnPublishPage() {
        cy.get('#notes').type('Version note test', { force: true });
        cy.get('form button[type="submit"]').eq(0).click({ force: true });
    }

    viewTextOnPageForStatus(text: string) {
        cy.contains(text);
    }
}

export const previewPagePage = new PreviewPagePage();
