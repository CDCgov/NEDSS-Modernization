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
        let isExist = false;
        const findOne = () => {
            cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr, index) => {
                cy.log('xox-$tr.find("td").eq(3).text()' + $tr.find("td").eq(3).text())
                if($tr.find("td").eq(3).text() === "Published") {
                    isExist = true;
                    cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                    return false
                }
            });
         }
        findOne();
        if(!isExist) {
            cy.get("th .usa-button.usa-button--unstyled").eq(2).click();
            cy.wait(2000);
             cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
        }
    }

    navigateToPreviewPageWithStatusPublishedWithDraft() {
        cy.visit('/page-builder/pages');
        cy.wait(2000);
         cy.get("table[data-testid=table]").eq(0).find("tbody tr").each(($tr, index) => {
            if($tr.find("td").eq(3).text() === "Published with Draft") {
                cy.get('table.pageLibraryTable tbody tr td a').eq(index).click();
                return false
            }
        });
    }
}

export const previewPagePage = new PreviewPagePage()