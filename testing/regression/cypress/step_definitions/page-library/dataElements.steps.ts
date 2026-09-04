import { Then } from '@badeball/cypress-cucumber-preprocessor';
import { pageLibraryDataElementsPage } from '@pages/page-library/dataElements.page';

Then('User views the {string} column', (string: string) => {
    pageLibraryDataElementsPage.userViewsColumnAndSeeList(string);
});

Then('User will see a list of the {string} populated in the {string} column', (string: string, string1: string) => {
    pageLibraryDataElementsPage.userViewsColumnAndSeeList(string1);
});
