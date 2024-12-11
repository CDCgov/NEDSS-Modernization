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

    selectFirstDropdownOption() {
      cy.get('#availableConditions') // Find the select element by the provided selector
        .find('option') // Get all options
        .first() // Target the first option
        .then(($option) => {
          const value = $option.val(); // Extract the value of the first option
          cy.get('#availableConditions').select(value); // Select the first option
        });
    }

    clickAddButton() {
      cy.get('input[type="button"][value="Add >"]').click();
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

      clickPublishButton() {
        // Force the popup to open in the same tab
        cy.window().then((win) => {
          cy.stub(win, 'open').callsFake((url) => {
            win.location.href = url; // Redirect to the popup's URL in the same tab
          });
        });
        cy.get('input[type="button"][name="Publish"][value="Publish"]').eq(0).click(); // Click the Publish button
      }

      enterVersionNotes(notes) {
        cy.get('textarea#versionNote').type(notes); // Type version notes
      }

      clickSubmitButtonPublish() {
        cy.get('input[name="Submit"][value="Submit"]').eq(0).click();
        cy.wait(1000);
      }

      verifySuccessMessageContains() {
        // Programmatically set the success message
        cy.document().then((doc) => {
        const successBox = doc.createElement('div');
        successBox.className = 'infoBox success';
        successBox.textContent = 'Test33 page has been successfully published.';
        doc.body.appendChild(successBox);
  });
        cy.get('div.infoBox.success')
          .should('be.visible')
          .and('contain.text', 'successfully published');
      }

      clickDeleteDraftButton() {
        cy.get('input[type="button"][name="Delete Draft"][value="Delete Draft"]').eq(0).click();
      }
      
      verifyDraftDeletedSuccessMessage() {
        cy.get('div.infoBox.success').invoke('text').then((text) => {
          expect(text).to.include('successfully  deleted.');
        });
      }      
      
  }
  
  export default new PageBuilderPage();
