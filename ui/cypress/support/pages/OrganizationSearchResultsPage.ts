import AddOrganizationPage from './AddOrganizationPage';
import BasePage from './BasePage';

enum Selector {
    ADD_BUTTON = 'input[id=Add]'
}
export default class OrganizationSearchResultsPage extends BasePage {
    constructor() {
        super('/FindOrganization1.do');
    }

    public navigateTo(): void {
        throw new Error(
            'Unable to navigate directly to results page. Please submit a search using OrganizationSearchPage'
        );
    }

    clickAddButton(): Cypress.Chainable<AddOrganizationPage> {
        return this.clickFirst(Selector.ADD_BUTTON).then(() => {
            return new AddOrganizationPage();
        });
    }
}
