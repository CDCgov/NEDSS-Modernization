class PreviewPagePage {
    navigateToPreviewPage (toSpecificPage) {
        if(toSpecificPage) {
            cy.visit('/page-builder/pages/1010441');
        } else {
            cy.visit('/page-builder/pages');
            cy.get('table.pageLibraryTable tbody tr td a').eq(2).click();
        }
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
}

export const previewPagePage = new PreviewPagePage()