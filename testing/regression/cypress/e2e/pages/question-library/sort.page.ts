class SortPage {
    navigateEditPage() {
        this.navigateToPreviewPageWithStatusInitialDraft();
        cy.get('body').then(($body) => {
            if ($body.find('#create-new-draft-button').length > 0) {
                cy.get('create-new-draft-button').then(($button) => {
                    if ($button.is(':visible')) {
                        $button.click();
                        cy.get('.editDraftBtn').eq(0).click();
                    }
                });
            } else {
                cy.get('.editDraftBtn').eq(0).click();
            }
        });
    }

    clickAddQuestionBtn() {
        cy.get('.subsectionHeader').eq(0).get('.addQuestionBtn').eq(0).click();
    }

    clickColumnArrow(column: string) {
        const columnIndex = this.getColumnIndexByName(column)!;
        cy.get('th .usa-button.usa-button--unstyled').eq(columnIndex).click();
    }

    listedInDescendingOrder(column: string) {
        this.checkOrder(column, 'descending');
    }

    listedInAscendingOrder(column: string) {
        this.checkOrder(column, 'ascending');
    }

    getColumnIndexByName(columnName: string) {
        if (columnName === 'Type') {
            return 0;
        } else if (columnName === 'Unique ID') {
            return 1;
        } else if (columnName === 'Label') {
            return 2;
        } else if (columnName === 'Subgroup') {
            return 3;
        }
    }

    checkOrder(columnName: string, sortType: string) {
        const list: string[] = [];
        const index = this.getColumnIndexByName(columnName)!;
        this.openInvestigationTable.find('tbody tr').each(($tr) => {
            list.push($tr.find('td').eq(index).text());
        });
        let isOrdered = false;
        if (sortType === 'ascending') {
            isOrdered = this.isAscending(list);
        } else if (sortType === 'descending') {
            isOrdered = this.isDescending(list);
        }
        expect(isOrdered).to.be.true;
    }

    isAscending(list: string[]) {
        return list.every((value: string, index: number, array: string[]) => {
            if (index === 0) {
                return true; // Skip the first element
            }
            return value >= array[index - 1];
        });
    }

    isDescending(list: string[]) {
        return list.every((value: string, index: number, array: string[]) => {
            if (index === 0) {
                return true; // Skip the first element
            }
            return value <= array[index - 1];
        });
    }

    get table() {
        return 'table[data-testid=table]';
    }

    get openInvestigationTable() {
        return cy.get(this.table).eq(0);
    }

    navigateToPreviewPageWithStatusInitialDraft() {
        cy.visit('/page-builder/pages');
        cy.wait(2000);
        cy.get('#range-toggle').select('100');
        cy.wait(2000);
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
}

export const questionLibrarySortPage = new SortPage();
