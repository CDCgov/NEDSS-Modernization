import BasePage from './BasePage';
import MergePatientSearchResultsPage from './MergePatientSearchResultsPage';

enum Selector {
    LAST_NAME = 'input[name=personSearch\\.lastName]',
    FIRST_NAME = 'input[name=personSearch\\.firstName]',
    SUBMIT_BUTTON = 'input[name=Submit]'
}
export default class MergePatientSearchPage extends BasePage {
    constructor() {
        super('/MyTaskList1.do?ContextAction=GlobalMP_ManualSearch&Mode1=ManualMerge');
    }

    setLastName(lastName: string): void {
        this.setText(Selector.LAST_NAME, lastName);
    }

    setFirstName(firstName: string): void {
        this.setText(Selector.FIRST_NAME, firstName);
    }

    clickSubmit(): MergePatientSearchResultsPage {
        this.clickFirst(Selector.SUBMIT_BUTTON);
        return new MergePatientSearchResultsPage();
    }
}
