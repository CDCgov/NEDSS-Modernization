class PageBuilderPage {
    clickAddNewButton() {
      cy.get('input[name="Submit"][value="Add New"]').eq(0).click(); // Click the "Add New" button
    }
  
    selectTemplate(templateName) {
      cy.get('input[name="existingTemplate_textbox"]').type(templateName).type('{enter}'); // Enter and select template name
    }
  
    selectMappingGuide(mappingGuideName) {
      cy.get('input[name="mappingGuide_textbox"]').type(mappingGuideName).type('{enter}'); // Enter and select mapping guide
    }
  
    enterPageName() {
      const newName = this.newName(); // Generate a unique name
      cy.get('#uniquePageName').type(`Page name ${newName}`); // Type the generated page name
    }
  
    newName() {
      return Date.now(); // Example: Use timestamp for unique names
    }
  
    clickSubmitButton() {
      cy.get('input#submitButton1').click(); // Click the "Submit" button
    }

    clickPageHistoryButton() {
        // Force the popup to open in the same tab
        cy.window().then((win) => {
          cy.stub(win, 'open').callsFake((url) => {
            win.location.href = url; // Redirect to the popup's URL in the same tab
          });
        });
        cy.get('input[name="Page History"][value="Page History"]').eq(0).click(); // Click the button
      }
    
      verifyPageHistoryPopup(expectedTitle) {
        cy.get('div.popupTitle').should('contain.text', expectedTitle); // Assert the page title
      }
  }
  
  export default new PageBuilderPage();
