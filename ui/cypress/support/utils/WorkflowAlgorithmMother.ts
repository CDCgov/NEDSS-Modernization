import CodedResult from '../models/enums/CodedResult';
import { SystemType } from '../models/enums/SystemType';
import Test from '../models/enums/Test';
import WorkflowAlgorithm from '../models/WorkflowAlgorithm';
import SystemMother from './SystemMother';

export default class WorkflowAlgorithmMother {
    public static caseReportAlgorithm(overrides?: Partial<WorkflowAlgorithm>): WorkflowAlgorithm {
        return {
            algorithmName: 'case report algorithm',
            eventType: SystemType.CASE_REPORT,
            sendingSystems: [SystemMother.caseReportSystem().displayName],
            administrativeComments: 'comments for case report algorithm \\&<>\'"',
            action: 'Create Investigation',
            investigationType: 'Tuberculosis',
            conditions: ['Tuberculosis'],
            onFailureToCreate: 'Roll-Back All (including event record)',
            advancedCritera: [
                {
                    question: 'Comments: (INV167)',
                    logic: 'Equal',
                    value: 'Test investigation comments check',
                    valueInputType: 'textarea'
                }
            ],
            investigationDefaultValues: [
                {
                    question: 'Comments: (INV167)',
                    value: 'Auto generated default comment',
                    valueInputType: 'textarea',
                    behvaior: 'Update Only Null Values'
                }
            ],
            ...overrides
        };
    }

    public static labReportAlgorithm(overrides?: Partial<WorkflowAlgorithm>): WorkflowAlgorithm {
        return {
            algorithmName: 'laboratory report algorithm',
            eventType: SystemType.LABORATORY_REPORT,
            sendingSystems: [SystemMother.labReportSystem().displayName],
            administrativeComments: 'comments for lab report algorithm \\&<>\'"',
            action: 'Create Investigation',
            investigationType: 'Varicella',
            conditions: ['Varicella'],
            onFailureToCreate: 'Retain Event Record',
            logicMode: 'OR',
            timeFrame: { operator: '<', value: '30' },
            labCriteria: [
                {
                    resultedTest: Test.ACID_FAST_STAIN,
                    codedResult: CodedResult.ABNORMALLY_HIGH,
                    numericResultOperator: '<',
                    numericResultValue: '20',
                    numericResultType: '(kcal)'
                }
            ],
            advancedCritera: [
                {
                    question: 'Contact Investigation Priority (NBS055)',
                    logic: 'Equal',
                    value: 'High',
                    valueInputType: 'drop-down'
                }
            ],
            investigationDefaultValues: [
                {
                    question: '52. Was the patient pregnant during this varicella illness: (INV178)',
                    value: 'Yes',
                    valueInputType: 'drop-down',
                    behvaior: 'Overwrite Existing Values'
                },
                {
                    question: 'Itchy: (VAR116)',
                    value: 'Yes',
                    valueInputType: 'drop-down',
                    behvaior: 'Update Only Null Values'
                }
            ],
            ...overrides
        };
    }
}
