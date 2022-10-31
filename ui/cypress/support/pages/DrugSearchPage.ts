import BasePage from './BasePage';

enum Selector {
    SEARCH_INPUT = 'input[id=labTest]',
    SHORT_LIST_RADIO_BUTTON = 'input[value=LOCAL]',
    LONG_LIST_RADIO_BUTTON = 'input[value=LOINC]',
    SUBMIT_BUTTON = 'input[id=Submit]'
}
export default class DrugSearchPage extends BasePage {
    constructor() {
        super('/SingleSelectLinkPB.do?method=searchLoad&identifier=NBS_LAB110');
    }

    setSearchText(searchText: string): void {
        this.setText(Selector.SEARCH_INPUT, searchText);
    }

    setSearchType(searchType: 'short' | 'long'): void {
        if (searchType === 'short') {
            this.click(Selector.SHORT_LIST_RADIO_BUTTON);
        } else {
            this.click(Selector.LONG_LIST_RADIO_BUTTON);
        }
    }

    clickSubmit(): void {
        this.clickFirst(Selector.SUBMIT_BUTTON);
    }
}
