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
    previewPagePage.checkPageNameField();
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

