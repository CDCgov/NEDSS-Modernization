import { When, Then } from 'cypress-cucumber-preprocessor/steps';
import DrugSearchPage from '../pages/DrugSearchPage';
import DrugSearchResultsPage from '../pages/DrugSearchResultsPage';

When('I do a {string} search for {string}', (searchType: 'short' | 'long', searchText: string) => {
    const drugSearchPage = new DrugSearchPage();
    drugSearchPage.navigateTo();
    drugSearchPage.setSearchType(searchType);
    // trying to get the Examples table to use literal \ is a pain. workaround here
    if (searchText === 'backslash') {
        searchText = '\\';
    }
    drugSearchPage.setSearchText(searchText);
    drugSearchPage.clickSubmit();
});

Then('I can see the result {string}', (expectedCode: string) => {
    const drugSearchResultsPage = new DrugSearchResultsPage();
    const resultsTable = drugSearchResultsPage.getResultsTable();
    resultsTable.should('contain', expectedCode);
});
