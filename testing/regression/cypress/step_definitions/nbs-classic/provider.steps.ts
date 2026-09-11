import { Then, When } from '@badeball/cypress-cucumber-preprocessor';
import classicProviderPage from '@pages/nbs-classic/provider.page';

Then('Navigate to classic provider add page', () => {
    classicProviderPage.navigateToAddProvider();
});

When('Enter last name {string}', (text: string) => {
    classicProviderPage.enterLastName(text);
});

When('Enter first name {string}', (text: string) => {
    classicProviderPage.enterFirstName(text);
});

When('Navigate to classic provider search page', () => {
    classicProviderPage.navigateToClassicProviderSearchPane();
});

Then('Click on Add button on provider add page', () => {
    classicProviderPage.clickAddButtonOnAddProvider();
});

Then('Enter quick code for new provider', () => {
    classicProviderPage.enterQuickCode();
});

Then('provider {string}, {string} should appear in search results', (lastName: string, firstName: string) => {
    classicProviderPage.verifyProviderNameInSearchResults(lastName, firstName);
});

Then('Click Submit button on provider add page', () => {
    classicProviderPage.clickSubmitBtnOnProvider();
});

Then('Click Edit button on provider page', () => {
    classicProviderPage.clickEditBtnOnProvider();
});

Then('Check Edit Provider on the page', () => {
    classicProviderPage.checkEditProviderPage();
});

Then('Click New Provider Edit Radio Option', () => {
    classicProviderPage.clickEditNewProviderRadio();
});

Then('Type new name for Edit Provider first name', () => {
    classicProviderPage.clickEditProviderAddName();
});
