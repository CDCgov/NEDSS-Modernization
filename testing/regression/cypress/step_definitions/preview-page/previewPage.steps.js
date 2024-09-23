import {Then} from "@badeball/cypress-cucumber-preprocessor";
import {previewPagePage} from "@pages/preview-page/previewPage.page";

Then("User navigates to Preview Page and views the Preview Page", () => {
    previewPagePage.navigateToPreviewPage();
});

Then("Below buttons will displays in preview page {string} {string}", (content, type) => {
    previewPagePage.viewsElementsOnPreviewPage(content, type);
});

Then("User navigates to Preview Page and page status is Initial Draft", () => {
    previewPagePage.navigateToPreviewPageWithStatusInitialDraft();
});

Then("User navigates to Preview Page and page status is Published", () => {
    previewPagePage.navigateToPreviewPageWithStatusPublished();
});

Then("user is at Preview page - Page info section with page under Draft or Published with Draft status", () => {
    previewPagePage.navigateToPreviewPageWithStatusInitialDraft();
});

Then("clicks on Edit Page details in preview page", () => {
    previewPagePage.clickOnEditPageDetails();
});

Then("verify user is navigated to Page details page with prepopulated data", () => {
    previewPagePage.checkNavigatedToPageDetails();
});

Then("verify Conditions is required and editable field", () => {
    previewPagePage.checkConditionsField();
});

Then("verify user can remove add the conditions", () => {
    previewPagePage.checkRemoveOrAddConditions();
});

Then("verify Page name is required field and already prefilled", () => {
    previewPagePage.checkPageNameField(true);
});

Then("verify user can update Page name with max characters 50", () => {
    previewPagePage.checkPageNameFieldMaxLength();
});

Then("verify Event Type is required and uneditable field", () => {
    previewPagePage.checkEventTypeField();
});

Then("verify Reporting Mechanism is required and editable field", () => {
    previewPagePage.checkReportingMechanismField();
});

Then("verify user can select another option from reporting mechanism dropdown", () => {
    previewPagePage.selectAnotherOptionFromReportingMechanism();
});

Then("verify Page description is optional field", () => {
    previewPagePage.checkPageDescriptionField();
});

Then("verify maximum allowed characters are 2000 in Page description", () => {
    previewPagePage.checkPageDescriptionFieldMaxLength();
});

Then("verify Data mart name is optional field and may display exiting data mart name", () => {
    previewPagePage.checkDatamartNameField();
});

Then("verify maximum allowed characters are 50 for datamart name", () => {
    previewPagePage.checkDatamartNameFieldMaxField();
});

Then("click on Cancel in page details page", () => {
    previewPagePage.clickCancelBtnPageDetailsPage();
});

Then("verify user is brought back to Preview page with correct status on top right", () => {
    previewPagePage.checkNavigatedBackToPreviewPage();
});

Then("verify no changes are made to page", () => {
    previewPagePage.checkChangesOnPreviewPageStatusType();
});

Then("click on Save changes in  page details page", () => {
    previewPagePage.clickSaveChangesBtnPageDetailsPage();
});

Then("verify user navigates to pre-preview page with success message You have successfully saved you changes", () => {
    previewPagePage.checkSuccessMessage();
});

Then("click on Close button in page details page", () => {
    previewPagePage.clickCloseBtnPageDetailsPage();
});

Then("user has created a page with all the required details", () => {
    previewPagePage.navigateToPreviewPageWithStatusInitialDraft();
});

Then("user clicks on Preview button", () => {
    previewPagePage.checkNavigatedBackToPreviewPage();
});

Then("preview page info is displayed", () => {
    previewPagePage.clickOnEditPageDetails();
    previewPagePage.checkNavigatedBackToPreviewPage();
});

Then("verify Event type Existing Event type is displayed and not editable", () => {
    previewPagePage.checkEventTypeField();
});

Then("verify selected Reporting Mechanism displays", () => {
    previewPagePage.checkReportingMechanismField();
});

Then("verify user can edit when page in draft or published with draft status", () => {
    previewPagePage.checkNavigatedToPageDetails();
});

Then("maximum characters allowed are 50 Reporting mechanism", () => {
    previewPagePage.selectAnotherOptionFromReportingMechanism();
});

Then("verify Page name is displayed", () => {
    previewPagePage.checkPageNameField();
});

Then("maximum characters allowed are 50 in page name", () => {
    previewPagePage.checkPageNameFieldMaxLength();
});

Then("verify Data mart name is displayed", () => {
    previewPagePage.checkDatamartNameField();
});

Then("maximum characters allowed are 2000", () => {
    previewPagePage.checkDatamartNameFieldMaxField();
});

Then("verify Edit page details button is ONLY available for Draft or Publish with draft status", () => {
    previewPagePage.checkNavigatedToPageDetails();
});

Then("clicked on Edit page details", () => {
    previewPagePage.clickCancelBtnPageDetailsPage()
    previewPagePage.clickOnEditPageDetails();
});

Then("verify all field are editable except Event Type", () => {
    previewPagePage.checkEventTypeField();
});

Then("clicked on Metadata button", () => {
    previewPagePage.clickOnMetadataBtn();
});

Then("verify all page metadata is downloaded in xls format", () => {
    previewPagePage.checkChangesOnPreviewPage();
});

Then("user is at Preview page - Page info section with page under Draft or Published with Draft or Published status", () => {
    previewPagePage.navigateToPreviewPageWithStatusInitialDraft();
});

Then("user clicks on History tab next to Details", () => {
    previewPagePage.clickOnHistoryTab();
});

Then("verify user is presented with all history info {string}", (text) => {
    previewPagePage.checkHistoryInfo(text);
});

Then("verify user sees only 10 changes at a time", () => {
    previewPagePage.userSeeOnlyTenRows();
});

Then("verify when more than 10 changes pagination is available", () => {
    previewPagePage.checkRowOptionsAvailable();
});

Then("User already on Create new page with Event Type Investigation selected", () => {
    previewPagePage.clickCreateNewPageButton();
    previewPagePage.selectEventType();
});

Then("User selects an existing Condition to create new page", () => {
    previewPagePage.selectCondition();
});

Then("User enters a Page name to create new page", () => {
    previewPagePage.selectPageName();
});

Then("User selects an existing Template to create new page", () => {
    previewPagePage.selectTemplate();
});

Then("User selects an existing Reporting mechanism to create new page", () => {
    previewPagePage.selectReportingMechanism();
});

Then("User enters a Page description to create new page", () => {
    previewPagePage.enterPageDescription();
});

Then("clicks the Create page button to create new page", () => {
    previewPagePage.clickCreatePageButton();
});

Then("New page is saved to the database and the application directs the user to the Page library Edit page to edit the additional page information", () => {
    previewPagePage.viewTextOnPage('New page test');
});

Then("User clicks the Preview button", () => {
    previewPagePage.clickPreviewAfterNewlyCreatedPage();
});

Then("Application will direct the user to the Preview page to Publish", () => {
    previewPagePage.viewTextOnPage('Publish');
});

Then("User clicks the Publish button", () => {
    previewPagePage.clickPublishBtn();
});

Then("User receives a confirmation that the page was successfully Published", () => {
    previewPagePage.clickPublishBtnOnPublishPage();
    previewPagePage.viewTextOnPage('General Information');
});

Then("Blue label top, right-side should appear as PREVIEWING:PUBLISHED", () => {
    previewPagePage.viewTextOnPageForStatus('Patient Information');
});

