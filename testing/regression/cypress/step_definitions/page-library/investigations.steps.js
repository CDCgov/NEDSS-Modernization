import {Then} from "@badeball/cypress-cucumber-preprocessor";
import PageBuilderPage from '@pages/page-library/investigations.page';


Then('I add new page', () => {
    PageBuilderPage.clickAddNewButton();
    PageBuilderPage.selectTemplate('42v1');
    PageBuilderPage.selectMappingGuide('Message Mapping Guide');
    PageBuilderPage.enterPageName(); // Calls the existing method
    PageBuilderPage.selectFirstDropdownOption();
    PageBuilderPage.clickAddButton();
    PageBuilderPage.clickSubmitButton();
  });

  Then('the user clicks on the Page History button', () => {
    PageBuilderPage.clickPageHistoryButton();
  });  

  Then('the Manage Pages: View Page History page is displayed', () => {
    PageBuilderPage.verifyPageHistoryPopup(' Manage Pages : View Page History ');
  });

  Then('the user clicks the Publish button', () => {
    PageBuilderPage.clickPublishButton(); // Call the new method
  });
  
  Then('the user enters Version Notes as {string}', (notes) => {
    PageBuilderPage.enterVersionNotes(notes); // Pass the notes to the method
  });

  Then('the user clicks the Submit button to publish', () => {
    PageBuilderPage.clickSubmitButtonPublish();
  });
  
  Then('success message contains the phrase "successfully published"', () => {
    PageBuilderPage.verifySuccessMessageContains();
  });
  