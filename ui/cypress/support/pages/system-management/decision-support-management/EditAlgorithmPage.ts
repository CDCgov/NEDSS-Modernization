import { SystemType } from '../../../models/enums/SystemType';
import WorkflowAlgorithm from '../../../models/WorkflowAlgorithm';
import AddAlgorithmPage from './AddAlgorithmPage';

enum Selector {
    ALGORITHM_SUMMARY_TABLE = 'div[id=bd] table[class=style] tbody',
    ADVANCED_CRITERIA_TABLE = 'table[id=IdAdvancedSubSection] table[class=dtTable] tbody',
    LAB_CRITERIA_TABLE = 'table[id=ElrIdAdvancedSubSection] tbody[id=questionbodyIdElrAdvancedSubSection]',
    INVESTIGATION_DEFAULTS_TABLE = 'table[id=IdSubSection] table[class=dtTable] tbody[id=questionbodyIdSubSection]',
    LAB_INVESTIGATION_DEFAULTS_TABLE = 'table[id=actionQuestionTable] tbody',
    LAB_ADVANCED_CRITERIA_TABLE = 'tbody[id=questionbodyIdAdvancedInvSubSection]'
}
export default class EditAlgorithmPage extends AddAlgorithmPage {
    constructor(uid: string) {
        super(`/DecisionSupport.do?algorithmUid=${uid}&method=editLoad`);
    }

    getAlgorithmSummaryTable(): Cypress.Chainable<JQuery<HTMLElement>> {
        return this.getElement(Selector.ALGORITHM_SUMMARY_TABLE);
    }

    private clickDeleteOnAllEntries(selector: string, deleteId: string): void {
        this.getElement(selector).then((jqueryTable) => {
            const inputSearch = jqueryTable[0].getElementsByTagName('input');
            for (let i = 0; i < inputSearch.length; i++) {
                const input = inputSearch[i];
                if (input.id.indexOf(deleteId) > -1 && input.id.length > deleteId.length) {
                    input.click();
                }
            }
        });
    }

    clearExistingAdvancedCriteria(systemType: SystemType): void {
        if (systemType === SystemType.CASE_REPORT) {
            this.clickDeleteOnAllEntries(Selector.ADVANCED_CRITERIA_TABLE, 'deleteIdAdvancedSubSection');
        } else {
            this.clickDeleteOnAllEntries(Selector.LAB_ADVANCED_CRITERIA_TABLE, 'deleteIdAdvancedInvSubSection');
        }
    }

    clearExistingInvestigationDefaults(systemType: SystemType): void {
        if (systemType === SystemType.CASE_REPORT) {
            this.clickDeleteOnAllEntries(Selector.INVESTIGATION_DEFAULTS_TABLE, 'deleteIdSubSection');
        } else {
            this.clickDeleteOnAllEntries(Selector.LAB_INVESTIGATION_DEFAULTS_TABLE, 'deleteIdSubSection');
        }
    }

    clearLabCriteria(): void {
        this.clickDeleteOnAllEntries(Selector.LAB_CRITERIA_TABLE, 'deleteIdElrAdvancedSubSection');
    }

    updateAlgorithm(algorithm: WorkflowAlgorithm): void {
        this.setActiveTab('Basic Criteria');
        this.setAlgorithmName(algorithm.algorithmName);
        this.setSendingSystems(algorithm.sendingSystems, algorithm.eventType);
        this.setAdministrativeComments(algorithm.administrativeComments);

        this.setActiveTab('Action');
        this.clearExistingInvestigationDefaults(algorithm.eventType);
        if (algorithm.eventType === SystemType.CASE_REPORT) {
            // Case report creation
            this.clearExistingAdvancedCriteria(algorithm.eventType);
            this.setAction(algorithm.action);
            this.setInvestigationType(algorithm.investigationType);
            this.setConditions(algorithm.conditions);
            this.setOnFailureToCreate(algorithm.onFailureToCreate);
            algorithm.advancedCritera.forEach((c) => this.addAdvancedCriteria(c, algorithm.eventType));
            algorithm.investigationDefaultValues?.forEach((d) => this.addInvestigationDefault(d));
        } else if (algorithm.eventType === SystemType.LABORATORY_REPORT) {
            // Lab report creationg
            if (algorithm.advancedCritera.length > 0) {
                this.clearExistingAdvancedCriteria(algorithm.eventType);
            }
            this.clearLabCriteria();

            this.setAction(algorithm.action);
            this.setConditionsDropdown(algorithm.conditions[0]);
            this.setLogicMode(algorithm.logicMode);
            algorithm.labCriteria?.forEach((criteria) => {
                this.setLabCriteriaResultedTest(criteria.resultedTest);
                if (criteria.codedResult) {
                    this.setLabCriteriaCodedResult(criteria.codedResult);
                } else if (
                    criteria.numericResultOperator &&
                    criteria.numericResultValue &&
                    criteria.numericResultType
                ) {
                    this.setNumericResult(
                        criteria.numericResultOperator,
                        criteria.numericResultValue,
                        criteria.numericResultType
                    );
                } else if (criteria.textResultOperator && criteria.textResultValue) {
                    this.setTextResult(criteria.textResultOperator, criteria.textResultValue);
                }
                this.clickAddLabCriteria();
            });
            if (algorithm.advancedCritera.length > 0) {
                this.setApplyInvestigationLogic(true);
                algorithm.advancedCritera.forEach((c) => this.addAdvancedCriteria(c, algorithm.eventType));
                if (algorithm.timeFrame) {
                    this.setApplyTimeFrameLogic(true);
                    this.setTimeFrameOperator(algorithm.timeFrame.operator);
                    this.setTimeFrameDays(algorithm.timeFrame.value);
                }
            }
            algorithm.investigationDefaultValues?.forEach((d) => this.addInvestigationDefault(d));
        } else {
            throw new Error('Unsupported SystemType for algorithm: ' + algorithm.algorithmName);
        }
        this.clickSubmit();
    }
}
