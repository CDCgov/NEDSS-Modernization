class PreviewPagePage {
    navigateToPreviewPage () {
        this.navigateToPreviewPageWithStatusInitialDraft();
    }

    viewsElementsOnPreviewPage(content, type) {
        if(type === "Icon") {
            if(content === "Preview") {
                cy.get('[aria-label="Preview in NBS Classic"]')
            } else if(content === "Clone") {
                cy.get('[aria-label="Page porting"]')
            } else if(content === "Print") {
                cy.get('[aria-label="Print this page"]')
            }
        } else {
            cy.contains(content)
        }
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

    navigateToPreviewPageWithStatusPublished() {
        cy.visit('/page-builder/pages');
        cy.wait(2000);
        function search () {
            cy.get('#range-toggle').select('100')
            cy.wait(5000);
            let isExist = false;
            cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr, index) => {
                if($tr.find("td").eq(3).text() === "Published") {
                    isExist = true;
                    cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                    return false
                }
            }).then(() => {
                if(!isExist) {
                    cy.contains('Next').eq(0).click()
                    search()
                }
            })
        }
        search()
    }

    navigateToPreviewPageWithStatusPublishedWithDraft() {
        cy.visit('/page-builder/pages');
        cy.wait(2000);
        cy.get('#range-toggle').select('100')
        cy.wait(5000);
        cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr, index) => {
            if($tr.find("td").eq(3).text() === "Published with Draft") {
                cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                return false
            }
        });
    }

    clickOnEditPageDetails() {
        cy.get('footer button').eq(0).click()
    }

    checkNavigatedToPageDetails() {
        cy.contains('Page Details')
    }

    checkConditionsField() {
        cy.wait(3000)
        cy.get('.multi-select__input-container').eq(0).click({ force: true })
    }

    checkRemoveOrAddConditions() {
        cy.get('.multi-select__menu').eq(0).click()
        cy.get('.multi-select__input-container').eq(0).click({ force: true })
    }

    checkPageNameField(check) {
        cy.get('#name').click()
        if (check) {
            cy.get('#name').clear()
            this.selectPageName()
        }
    }

    checkPageNameFieldMaxLength() {
        cy.get('#name')
            .invoke('text')
            .then((text) => {
                if(text) {
                    expect(text.length).to.be.lessThan(50)
                }
            })
    }

    checkEventTypeField() {
        cy.get('[data-testid="dropdown"]').should('be.disabled')
    }

    checkReportingMechanismField() {
        cy.get('#messageMappingGuide').should('be.exist')
    }

    selectAnotherOptionFromReportingMechanism() {
        cy.get('#messageMappingGuide').select(2)
    }

    checkPageDescriptionField() {
        cy.get('#description').should('be.exist')
    }

    checkPageDescriptionFieldMaxLength() {
        cy.get('#description')
            .invoke('text')
            .then((text) => {
                if(text) {
                    expect(text.length).to.be.lessThan(2000)
                }
            })
    }

    checkDatamartNameField() {
        cy.get('#datamart').should('be.exist')
    }

    checkDatamartNameFieldMaxField() {
        cy.get('#datamart')
            .invoke('text')
            .then((text) => {
                if(text) {
                    expect(text.length).to.be.lessThan(2000)
                }
            })
    }

    clickCancelBtnPageDetailsPage() {
        cy.contains('Cancel').click()
    }

    checkNavigatedBackToPreviewPage() {
        cy.contains('Page Details')
    }

    checkChangesOnPreviewPage() {
        cy.contains('Page information')
    }

    clickSaveChangesBtnPageDetailsPage() {
        this.clickOnEditPageDetails()
        cy.get('#name').type('test')
        cy.contains('Save').click()
    }

    checkSuccessMessage() {
        cy.wait(5000)
    }

    checkChangesOnPreviewPageStatusType() {
        cy.contains('PREVIEWING:')
    }

    checkEditDraftPage() {
        cy.contains('Edit draft')
    }

    clickOnMetadataBtn() {
        this.clickCancelBtnPageDetailsPage()
        cy.contains('Metadata').click()
    }

    clickOnHistoryTab() {
        cy.get('nav div ul li').eq(1).click()
    }

    checkHistoryInfo(info) {
         cy.get('aside section div').eq(0)
            .invoke('text')
            .then((text) => {
                if (text.includes(info)) {
                    cy.contains(info);
                }
            })
    }

    userSeeOnlyTenRows() {
         cy.get('aside section div').eq(0)
            .invoke('text')
            .then((text) => {
                if (text.includes(10)) {
                    cy.contains(10);
                }
            })
    }

    checkRowOptionsAvailable() {
        cy.get('aside section div').eq(0)
            .invoke('text')
            .then((text) => {
                if (text.includes(20)) {
                    cy.contains(20);
                }
            })
    }

    clickCreateNewPageButton() {
        cy.visit('/page-builder/pages');
        cy.get(".createNewPageButton").eq(0).click()
    }
    userViewsEventTypeField() {
        cy.get("#eventType")
    }

    selectEventType(type) {
        cy.wait(2000)
        if(type) {
            cy.get("#eventType").select(type)
        } else {
            cy.get("#eventType").select("INV")
        }
    }

    viewTextOnPage(text) {
        cy.contains(text)
    }

    selectCondition() {
        this.selectEventType()
        cy.get("#conditionIds").click()
        cy.get('#conditionIds .multi-select__option input[type="checkbox"]').eq(0).click({ force: true })
    }

    selectPageName() {
        cy.get('#name').click({ force: true })
        const newPageName = Math.random().toString(36).substring(2, 8);
        cy.get('#name').type(`New page test ${newPageName}`)
    }

    selectTemplate() {
        cy.get('#templateId').find('option').eq(1).then((option) => {
            cy.get('#templateId').select(option.attr('value'))
        })
    }

    selectReportingMechanism() {
        cy.get('#messageMappingGuide').find('option').eq(1).then((option) => {
            cy.get('#messageMappingGuide').select(option.attr('value'))
        })
    }
    enterPageDescription() {
        cy.get("#pageDescription").type("This page is for diagnosis")
    }

    clickCreatePageButton() {
        cy.get('.createPage').eq(0).click()
        cy.wait(4000)
    }

    clickPreviewAfterNewlyCreatedPage() {
        cy.contains('Preview').click();
    }

    clickPublishBtn() {
        cy.get('menu div button').eq(0).click();

    }

    clickPublishBtnOnPublishPage() {
        cy.wait(2000)
        cy.get('#notes').type('Version note test', { force: true });
        cy.get('form button[type="submit"]').eq(0).click({ force: true });
    }

    viewTextOnPageForStatus(text) {
        cy.wait(2000)
        cy.contains(text)
    }

}

export const previewPagePage = new PreviewPagePage()