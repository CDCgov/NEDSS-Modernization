import BasePage from '../../BasePage';
import EditAlgorithmPage from './EditAlgorithmPage';

enum Selector {
    ALGORITHM_SUMMARY_TABLE = 'div[id=bd] table[class=style] tbody',
    BASIC_CRITERIA_TAB = 'td[id=tabs0head0]',
    ACTIONS_TAB = 'td[id=tabs0head1]',
    EDIT_BUTTON = 'input[id=Submit]',
    MAKE_ACTIVE_BUTTON = 'input[id=submitB]',
    // Action tab
    // Shared tables
    ACTION_DETAILS_TABLE = 'table[id=subsec4]',
    INVESTIGATION_DEFAULTS_TABLE = 'table[id=IdSubSection]',
    // Case report Tables
    ADVANCED_CRITERIA_TABLE = 'table[id=IdAdvancedSubSection]',
    ADVANCED_CRITERIA_DETAILS_TABLE = 'table[id=advancedCriteriaTable]',
    // Lab report tables
    LAB_CRITERIA_LOGIC_TABLE = 'table[id=ElrTheAdvancedSubSection]',
    LAB_CRITERIA_TABLE = 'table[id=ElrIdAdvancedSubSection]',
    INVESTIGATION_CRITERIA_TABLE = 'table[id=invCriteriaSubSection]',
    ADVANCED_INVESTIGATION_CRITERIA_TABLE = 'table[id=IdAdvancedInvSubSection]',
    ADVANCED_INVESTIGATION_CRITERIA_DETAILS = 'table[id=advancedInvCriteriaTable]',
    TIME_FRAME_LOGIC_TABLE = 'table[id=timeFrameSubSection]'
}
export default class ViewAlgorithmPage extends BasePage {
    constructor(uid: string) {
        super(`/DecisionSupport.do?algorithmUid=${uid}&method=viewLoad`);
    }

    getAlgorithmSummaryTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ALGORITHM_SUMMARY_TABLE);
    }

    getActionDetailsTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ACTION_DETAILS_TABLE);
    }

    getInvestigationDefaultsTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.INVESTIGATION_DEFAULTS_TABLE);
    }

    getAdvancedCriteriaTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ADVANCED_CRITERIA_TABLE);
    }

    getAdvancedCriteriaDetailsTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ADVANCED_CRITERIA_DETAILS_TABLE);
    }

    // clicks the view icon associated with the supplied 'question'
    private clickViewEntryInTable(table: Cypress.Chainable, question: string): Cypress.Chainable {
        return table
            .find('table[class=dtTable]', this.defaultOptions)
            .find('tbody', this.defaultOptions)
            .find('tr', this.defaultOptions)
            .then((rows) => {
                for (let i = 0; i < rows.length; i++) {
                    const rowQuestion = rows[i].children[1].innerHTML;
                    if (rowQuestion.indexOf(question) > -1) {
                        // click the 'view' button
                        rows[i].children[0].getElementsByTagName('input')[0].click();
                        return;
                    }
                }
                throw new Error('Failed to find entry in table for question: ' + question);
            });
    }

    clickViewAdvancedCriteria(question: string): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.clickViewEntryInTable(this.getAdvancedCriteriaTable(), question).then(() => {
            return this.getAdvancedCriteriaDetailsTable();
        });
    }

    clickViewAdvancedInvestigationCriteria(question: string): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.clickViewEntryInTable(this.getAdvancedInvestigationCriteriaTable(), question).then(() => {
            return this.getAdvancedInvestigationCriteriaDetailsTable();
        });
    }

    getLabCriteriaLogic(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.LAB_CRITERIA_LOGIC_TABLE)
            .find('tbody', this.defaultOptions)
            .find('tr', this.defaultOptions)
            .eq(1, this.defaultOptions)
            .find('td', this.defaultOptions)
            .eq(1, this.defaultOptions);
    }

    getLabCriteriaTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.LAB_CRITERIA_TABLE);
    }

    getInvestigationCriteria(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.INVESTIGATION_CRITERIA_TABLE)
            .find('tbody', this.defaultOptions)
            .find('tr', this.defaultOptions)
            .eq(1, this.defaultOptions)
            .find('td', this.defaultOptions)
            .eq(1, this.defaultOptions);
    }

    getAdvancedInvestigationCriteriaTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ADVANCED_INVESTIGATION_CRITERIA_TABLE);
    }

    getAdvancedInvestigationCriteriaDetailsTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ADVANCED_INVESTIGATION_CRITERIA_DETAILS);
    }

    getTimeFrameLogicTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.TIME_FRAME_LOGIC_TABLE);
    }

    setActiveTab(tab: 'Basic Criteria' | 'Action'): void {
        if (tab === 'Basic Criteria') {
            this.clickFirst(Selector.BASIC_CRITERIA_TAB);
        } else {
            this.clickFirst(Selector.ACTIONS_TAB);
        }
        cy.wait(200, this.defaultOptions);
    }

    clickEdit(): Cypress.Chainable<EditAlgorithmPage> {
        this.clickFirst(Selector.EDIT_BUTTON);
        return cy.url(this.defaultOptions).then((url) => {
            const uid = url.substring(url.indexOf('algorithmUid=') + 'algorithmUid='.length, url.indexOf('&method='));
            return new EditAlgorithmPage(uid);
        });
    }

    clickMakeActive(): void {
        this.clickFirst(Selector.MAKE_ACTIVE_BUTTON);
    }
}
