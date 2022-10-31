import CodedResult from '../../../models/enums/CodedResult';
import { SystemType } from '../../../models/enums/SystemType';
import Test from '../../../models/enums/Test';
import WorkflowAlgorithm, { AdvancedCriteria, InvestigationDefaultValue } from '../../../models/WorkflowAlgorithm';
import BasePage from '../../BasePage';

enum Selector {
    BASIC_CRITERIA_TAB = 'td[id=tabs0head0]',
    ACTIONS_TAB = 'td[id=tabs0head1]',
    SUBMIT_BUTTON = 'input[id=Submit]',

    // basic criteria tab
    ALGORITHM_NAME = 'input[id=AlgoNm]',
    EVENT_TYPE = 'input[name=EVENT_TY_textbox]',
    ADMINISTRATIVE_COMMENTS = 'textarea[name=decisionSupportClientVO\\.answer\\(Comment\\)]',
    SENDING_SYSTEMS = 'select[id=sendSys]',
    LABORATORY_SENDING_SYSTEMS = 'select[id=selectedLaboratory]',

    // actions tab
    ACTION = 'input[name=ActionList_textbox]',
    INVESTIGATION_TYPE = 'input[name=relatedPage_textbox]',
    CONDITIONS = 'select[id=conditionList]',
    CONDITION_DROPDOWN = 'input[name=PublishedCondition_textbox]',
    ON_FAILURE = 'input[name=onfail_textbox]',
    ADVANCED_CRITERIA_TABLE = 'table[id=IdAdvancedSubSection] table[class=dtTable] tbody',
    INVESTIGATION_DEFAULTS_TABLE = 'table[id=IdSubSection] table[class=dtTable] tbody',

    // Lab report specific
    LAB_CRITERIA_LOGIC_ANY = 'table[id=ElrTheAdvancedSubSection] tbody input[value=OR]',
    LAB_CRITERIA_LOGIC_ALL = 'table[id=ElrTheAdvancedSubSection] tbody input[value=AND]',
    LAB_CRITERIA_RESULTED_TEST_CODE = 'input[id=testCodeId]',
    LAB_CRITERIA_RESULTED_TEST_DESCRIPTION_WITH_CODE = 'input[id=testDescriptionId]',
    LAB_CRITERIA_RESULTED_TEST_DESCRIPTION = 'span[id=testDescription]',
    LAB_CRITERIA_CODED_RESULT_CODE = 'input[id=codeResultId]',
    LAB_CRITERIA_CODED_RESULT_DESCRIPTION_WITH_CODE = 'input[id=resultDescriptionId]',
    LAB_CRITERIA_CODED_RESULT_DESCRIPTION = 'span[id=resultDescription]',
    LAB_CRITERIA_NUMERIC_RESULT_OPERATOR = 'input[name=numericResultOperatorList_textbox]',
    LAB_CRITERIA_NUMERIC_RESULT_VALUE = 'input[id=numericResult_text]',
    LAB_CRITERIA_NUMERIC_RESULT_TYPE = 'input[name=numericResultTypeList_textbox]',
    LAB_CRITERIA_TEXT_RESULT_OPERATOR = 'input[name=resultOperatorList_textbox]',
    LAB_CRITERIA_TEXT_RESULT_VALUE = 'input[id=textResult_text]',
    LAB_CRITERIA_ADD_BUTTON = 'tr[id=AddButtonToggleIdELRAdvancedSubSection] input[type=button]',
    LAB_INVESTIGATION_CRITERIA_YES = 'input[id=useInvLogicRadioYes]',
    LAB_INVESTIGATION_CRITERIA_NO = 'input[id=useInvLogicRadioNo]',
    LAB_TIME_FRAME_LOGIC_YES = 'input[id=useEventDateLogicRadioYes]',
    LAB_TIME_FRAME_LOGIC_NO = 'input[id=useEventDateLogicRadioNo]',
    LAB_TIME_FRAME_OPERATOR = 'input[name=TimeFrameOpSel_textbox]',
    LAB_TIME_FRAME_DAYS = 'input[id=TimeFrameDays]',
    LAB_ADVANCED_CRITERIA_QUESTION = 'input[name=questionAdvInvList_textbox]',
    LAB_ADVANCED_CRITERIA_LOGIC = 'input[name=advInvLogicList_textbox]',
    LAB_ADVANCED_CRITERIA_VALUE = 'input[id=advInvVal_text]',
    LAB_ADVANCED_CRITERIA_ADD_BUTTON = 'tr[id=AddButtonToggleIdAdvancedInvSubSection] td input[type=button]',
    LAB_ADVANCED_CRITERIA_VALUE_TEXTAREA = 'textarea[id=advInvVal_textArea]',
    LAB_ADVANCED_CRITERIA_VALUE_INPUT = 'input[id=advInvVal_text]',
    LAB_ADVANCED_CRITERIA_VALUE_DATE = 'input[id=advInvVal_date]',
    LAB_ADVANCED_CRITERIA_VALUE_DROP_DOWN = 'input[name=advInvValueList1_textbox]',
    LAB_ADVANCED_CRITERIA_VALUE_SELECT = 'select[id=advInvValueList2]',

    ADVANCED_CRITERIA_QUESTION = 'input[name=questionAdvList_textbox]',
    ADVANCED_CRITERIA_LOGIC = 'input[name=advLogicList_textbox]',
    // Depending on the question selected the inputs change
    ADVANCED_CRITERIA_VALUE_TEXTAREA = 'textarea[id=advVal_textArea]',
    ADVANCED_CRITERIA_VALUE_INPUT = 'input[id=advVal_text]',
    ADVANCED_CRITERIA_VALUE_DATE = 'input[id=advVal_date]',
    ADVANCED_CRITERIA_VALUE_DROP_DOWN = 'input[id=advValueList1_textbox]',
    ADVANCED_CRITERIA_VALUE_SELECT = 'select[id=advValueList2]',
    ADVANCED_CRITERIA_ADD_BUTTON = 'tr[id=AddButtonToggleIdAdvancedSubSection] td input[type=button]',

    INVESTIGATION_DEFAULT_QUESTION = 'input[name=questionList_textbox]',
    // Radio buttons only show if Question has a Date response
    INVESTIGATION_DEFAULT_USE_CURRENT_DATE_RADIO_BTN = 'input[id=CURRENT_CURRENT_SELECT_DATE_LOGIC]',
    // Depending on the question selected the inputs change

    INVESTIGATION_DEFAULT_VALUE_TEXTAREA = 'textarea[id=Val_textArea]',
    INVESTIGATION_DEFAULT_VALUE_INPUT = 'input[id=Val_text]',
    INVESTIGATION_DEFAULT_VALUE_DATE = 'input[id=Val_date]',
    INVESTIGATION_DEFAULT_VALUE_DROP_DOWN = 'input[name=valueList1_textbox]',
    INVESTIGATION_DEFAULT_VALUE_SELECT = 'select[id=valueList2]',

    INVESTIGATION_DEFAULT_USE_VALUE_RADIO_BTN = 'input[id=SELECT_CURRENT_SELECT_DATE_LOGIC]',
    INVESTIGATION_DEFAULT_BEHAVIOR = 'input[name=behavior_textbox]',
    INVESTIGATION_DEFAULT_ADD_BUTTON = 'tr[id=AddButtonToggleIdSubSection] td input[type=button]'
}
export default class AddAlgorithmPage extends BasePage {
    constructor(relativeUrl = '/DecisionSupport.do?method=createLoad') {
        super(relativeUrl);
    }

    setActiveTab(tab: 'Basic Criteria' | 'Action'): void {
        if (tab === 'Basic Criteria') {
            this.clickFirst(Selector.BASIC_CRITERIA_TAB);
        } else {
            this.clickFirst(Selector.ACTIONS_TAB);
        }
        cy.wait(200, this.defaultOptions);
    }

    clickSubmit(): void {
        this.clickFirst(Selector.SUBMIT_BUTTON);
    }

    // Basic Criteria Tab
    setAlgorithmName(algorithmName: string): void {
        this.setText(Selector.ALGORITHM_NAME, algorithmName);
    }

    setEventType(eventType: SystemType): void {
        this.setText(Selector.EVENT_TYPE, eventType);
        this.blur(Selector.EVENT_TYPE);
    }

    setSendingSystems(sendingSystems: string[], eventType: SystemType): void {
        const selector =
            eventType === SystemType.CASE_REPORT ? Selector.SENDING_SYSTEMS : Selector.LABORATORY_SENDING_SYSTEMS;
        this.select(selector, sendingSystems);
    }

    setAdministrativeComments(comments: string): void {
        this.setText(Selector.ADMINISTRATIVE_COMMENTS, comments);
    }

    // Action
    setAction(action: 'Create Investigation' | 'Create Investigation with Notification'): void {
        this.clear(Selector.ACTION);
        this.setText(Selector.ACTION, action);
        this.blur(Selector.ACTION);
    }

    setInvestigationType(investigationType: string): void {
        this.setText(Selector.INVESTIGATION_TYPE, investigationType);
        this.blur(Selector.INVESTIGATION_TYPE);
    }

    setConditions(conditions: string[]): void {
        this.select(Selector.CONDITIONS, conditions);
    }

    setConditionsDropdown(condition: string): void {
        this.setText(Selector.CONDITION_DROPDOWN, condition);
    }

    setOnFailureToCreate(onFailure: 'Retain Event Record' | 'Roll-Back All (including event record)'): void {
        this.setText(Selector.ON_FAILURE, onFailure);
    }

    setAdvancedCriteriaQuestion(question: string): void {
        this.setText(Selector.ADVANCED_CRITERIA_QUESTION, question);
        this.blur(Selector.ADVANCED_CRITERIA_QUESTION);
    }

    setAdvancedCriteriaLogic(logic: string): void {
        this.setText(Selector.ADVANCED_CRITERIA_LOGIC, logic);
    }

    setAdvancedCriteriaValue(value: string, inputType: 'textarea' | 'input' | 'date' | 'drop-down' | 'select'): void {
        switch (inputType) {
            case 'textarea':
                this.setText(Selector.ADVANCED_CRITERIA_VALUE_TEXTAREA, value);
                break;
            case 'input':
                this.setText(Selector.ADVANCED_CRITERIA_VALUE_INPUT, value);
                break;
            case 'date':
                this.setText(Selector.ADVANCED_CRITERIA_VALUE_DATE, value);
                break;
            case 'drop-down':
                this.setText(Selector.ADVANCED_CRITERIA_VALUE_DROP_DOWN, value);
                break;
            case 'select':
                this.setText(Selector.ADVANCED_CRITERIA_VALUE_SELECT, value);
                break;
        }
    }

    clickAddAdvancedCriteria(): void {
        this.click(Selector.ADVANCED_CRITERIA_ADD_BUTTON);
    }

    setLabAdvancedCriteriaQuestion(question: string): void {
        this.setText(Selector.LAB_ADVANCED_CRITERIA_QUESTION, question);
        this.blur(Selector.LAB_ADVANCED_CRITERIA_QUESTION);
    }

    setLabAdvancedCriteriaLogic(logic: string): void {
        this.setText(Selector.LAB_ADVANCED_CRITERIA_LOGIC, logic);
    }

    setLabAdvancedCriteriaValue(
        value: string,
        inputType: 'textarea' | 'input' | 'date' | 'drop-down' | 'select'
    ): void {
        switch (inputType) {
            case 'textarea':
                this.setText(Selector.LAB_ADVANCED_CRITERIA_VALUE_TEXTAREA, value);
                break;
            case 'input':
                this.setText(Selector.LAB_ADVANCED_CRITERIA_VALUE_INPUT, value);
                break;
            case 'date':
                this.setText(Selector.LAB_ADVANCED_CRITERIA_VALUE_DATE, value);
                break;
            case 'drop-down':
                this.setText(Selector.LAB_ADVANCED_CRITERIA_VALUE_DROP_DOWN, value);
                break;
            case 'select':
                this.setText(Selector.LAB_ADVANCED_CRITERIA_VALUE_SELECT, value);
                break;
        }
    }

    clickAddLabAdvancedCriteria(): void {
        this.click(Selector.LAB_ADVANCED_CRITERIA_ADD_BUTTON);
    }

    setInvestigationDefaultQuestion(question: string): void {
        this.setText(Selector.INVESTIGATION_DEFAULT_QUESTION, question);
        this.blur(Selector.INVESTIGATION_DEFAULT_QUESTION);
    }

    setInvestigationDefaultValue(
        value: string,
        inputType: 'textarea' | 'input' | 'date' | 'drop-down' | 'select'
    ): void {
        switch (inputType) {
            case 'textarea':
                this.setText(Selector.INVESTIGATION_DEFAULT_VALUE_TEXTAREA, value);
                break;
            case 'input':
                this.setText(Selector.INVESTIGATION_DEFAULT_VALUE_INPUT, value);
                break;
            case 'date':
                this.setText(Selector.INVESTIGATION_DEFAULT_VALUE_DATE, value);
                break;
            case 'drop-down':
                this.setText(Selector.INVESTIGATION_DEFAULT_VALUE_DROP_DOWN, value);
                break;
            case 'select':
                this.setText(Selector.INVESTIGATION_DEFAULT_VALUE_SELECT, value);
                break;
        }
    }

    setInvestigationDefaultValueDateBehavior(value: 'Use Current Date' | 'Use Value Field'): void {
        if (value === 'Use Current Date') {
            this.click(Selector.INVESTIGATION_DEFAULT_USE_CURRENT_DATE_RADIO_BTN);
        } else {
            this.click(Selector.INVESTIGATION_DEFAULT_USE_VALUE_RADIO_BTN);
        }
    }

    setInvestigationDefaultBehavior(behavior: 'Overwrite Existing Values' | 'Update Only Null Values'): void {
        this.setText(Selector.INVESTIGATION_DEFAULT_BEHAVIOR, behavior);
    }

    setLogicMode(logicMode?: 'OR' | 'AND'): void {
        if (logicMode === 'OR') {
            this.click(Selector.LAB_CRITERIA_LOGIC_ANY);
        } else if (logicMode === 'AND') {
            this.click(Selector.LAB_CRITERIA_LOGIC_ALL);
        }
    }

    setApplyInvestigationLogic(applyInvestigationLogic: boolean): void {
        if (applyInvestigationLogic) {
            this.click(Selector.LAB_INVESTIGATION_CRITERIA_YES);
        } else {
            this.click(Selector.LAB_INVESTIGATION_CRITERIA_NO);
        }
    }

    setApplyTimeFrameLogic(applyTimeFrameLogic: boolean): void {
        if (applyTimeFrameLogic) {
            this.click(Selector.LAB_TIME_FRAME_LOGIC_YES);
        } else {
            this.click(Selector.LAB_TIME_FRAME_LOGIC_NO);
        }
    }

    setTimeFrameOperator(operator: '<' | '<=' | '>' | '>='): void {
        this.setText(Selector.LAB_TIME_FRAME_OPERATOR, operator);
    }

    setTimeFrameDays(days: string): void {
        this.setText(Selector.LAB_TIME_FRAME_DAYS, days);
    }

    clickAddInvestigationDefault(): void {
        this.click(Selector.INVESTIGATION_DEFAULT_ADD_BUTTON);
    }

    setLabCriteriaResultedTest(resultedTest: Test): void {
        this.setValue(Selector.LAB_CRITERIA_RESULTED_TEST_CODE, resultedTest.code);
        this.setValue(
            Selector.LAB_CRITERIA_RESULTED_TEST_DESCRIPTION_WITH_CODE,
            `${resultedTest.description} (${resultedTest.code})`
        );
        this.setInnerHtml(
            Selector.LAB_CRITERIA_RESULTED_TEST_DESCRIPTION,
            `${resultedTest.description} (${resultedTest.code})`
        );
    }

    setLabCriteriaCodedResult(codedResult: CodedResult): void {
        this.setValue(Selector.LAB_CRITERIA_CODED_RESULT_CODE, codedResult.code);
        this.setValue(
            Selector.LAB_CRITERIA_CODED_RESULT_DESCRIPTION_WITH_CODE,
            `${codedResult.description} (${codedResult.code})`
        );
        this.setInnerHtml(
            Selector.LAB_CRITERIA_CODED_RESULT_DESCRIPTION,
            `${codedResult.description} (${codedResult.code})`
        );
    }

    setNumericResult(operator: string, value: string, type: string): void {
        this.setText(Selector.LAB_CRITERIA_NUMERIC_RESULT_OPERATOR, operator);
        this.setText(Selector.LAB_CRITERIA_NUMERIC_RESULT_VALUE, value);
        this.setText(Selector.LAB_CRITERIA_NUMERIC_RESULT_TYPE, type);
    }

    setTextResult(operator: string, value: string): void {
        this.setText(Selector.LAB_CRITERIA_TEXT_RESULT_OPERATOR, operator);
        this.setText(Selector.LAB_CRITERIA_TEXT_RESULT_VALUE, value);
    }

    clickAddLabCriteria(): void {
        this.click(Selector.LAB_CRITERIA_ADD_BUTTON);
    }

    addAdvancedCriteria(advancedCritera: AdvancedCriteria, eventType: SystemType): void {
        if (eventType === SystemType.CASE_REPORT) {
            this.setAdvancedCriteriaQuestion(advancedCritera.question);
            this.setAdvancedCriteriaLogic(advancedCritera.logic);
            this.setAdvancedCriteriaValue(advancedCritera.value, advancedCritera.valueInputType);
            this.clickAddAdvancedCriteria();
        } else {
            this.setLabAdvancedCriteriaQuestion(advancedCritera.question);
            this.setLabAdvancedCriteriaLogic(advancedCritera.logic);
            this.setLabAdvancedCriteriaValue(advancedCritera.value, advancedCritera.valueInputType);
            this.clickAddLabAdvancedCriteria();
        }
    }

    addInvestigationDefault(investigationDefault: InvestigationDefaultValue): void {
        this.setInvestigationDefaultQuestion(investigationDefault.question);
        this.setInvestigationDefaultValue(investigationDefault.value, investigationDefault.valueInputType);
        this.setInvestigationDefaultBehavior(investigationDefault.behvaior);
        this.clickAddInvestigationDefault();
    }

    createAlgorithm(algorithm: WorkflowAlgorithm): void {
        this.setActiveTab('Basic Criteria');
        this.setAlgorithmName(algorithm.algorithmName);
        this.setEventType(algorithm.eventType);
        this.setSendingSystems(algorithm.sendingSystems, algorithm.eventType);
        this.setAdministrativeComments(algorithm.administrativeComments);

        this.setActiveTab('Action');
        if (algorithm.eventType === SystemType.CASE_REPORT) {
            // Case report creation
            this.setAction(algorithm.action);
            this.setInvestigationType(algorithm.investigationType);
            this.setConditions(algorithm.conditions);
            this.setOnFailureToCreate(algorithm.onFailureToCreate);
            algorithm.advancedCritera.forEach((c) => this.addAdvancedCriteria(c, algorithm.eventType));
            algorithm.investigationDefaultValues?.forEach((d) => this.addInvestigationDefault(d));
        } else if (algorithm.eventType === SystemType.LABORATORY_REPORT) {
            // Lab report creationg
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

    public navigateTo(): void {
        cy.visit(this.relativeUrl, this.defaultOptions);
        // The page takes a moment before the tabs are ready
        cy.wait(100, this.defaultOptions);
    }
}
