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

