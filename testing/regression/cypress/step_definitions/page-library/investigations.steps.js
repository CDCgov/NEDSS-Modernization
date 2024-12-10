import {Then} from "@badeball/cypress-cucumber-preprocessor";
import PageBuilderPage from '@pages/page-library/investigations.page';


Then('I add new page', () => {
    PageBuilderPage.clickAddNewButton();
    PageBuilderPage.selectTemplate('42v1');
    PageBuilderPage.selectMappingGuide('Message Mapping Guide');
    PageBuilderPage.enterPageName(); // Calls the existing method
    PageBuilderPage.clickSubmitButton();
  });

  Then('the user clicks on the Page History button', () => {
    PageBuilderPage.clickPageHistoryButton();
  });  

  Then('the Manage Pages: View Page History page is displayed', () => {
    PageBuilderPage.verifyPageHistoryPopup(' Manage Pages : View Page History ');
  });
  