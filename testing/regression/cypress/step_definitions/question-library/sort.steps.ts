import { Then } from '@badeball/cypress-cucumber-preprocessor';
import { questionLibrarySortPage } from '@pages/question-library/sort.page';

Then('User navigates to Question Library and views the Question library', () => {
    questionLibrarySortPage.navigateEditPage();
    questionLibrarySortPage.clickAddQuestionBtn();
});

Then('User click the up or down arrow in the {string} column', (column: string) => {
    questionLibrarySortPage.clickColumnArrow(column);
});

Then('In {string} column {string} are listed in descending order', (column: string) => {
    questionLibrarySortPage.listedInDescendingOrder(column);
});

Then('User click the up or down arrow in the {string} column again', (column: string) => {
    questionLibrarySortPage.clickColumnArrow(column);
});

Then('In {string} column {string} are listed in ascending order', (column: string) => {
    questionLibrarySortPage.listedInAscendingOrder(column);
});
