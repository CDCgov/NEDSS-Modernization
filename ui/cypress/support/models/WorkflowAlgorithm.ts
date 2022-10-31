import CodedResult from './enums/CodedResult';
import { SystemType } from './enums/SystemType';
import Test from './enums/Test';

export default class WorkflowAlgorithm {
    // Basic Criteria
    algorithmName: string;
    eventType: SystemType;
    sendingSystems: string[];
    administrativeComments: string;

    // Action
    action: 'Create Investigation' | 'Create Investigation with Notification';
    investigationType: string;
    conditions: string[];
    onFailureToCreate: 'Retain Event Record' | 'Roll-Back All (including event record)';
    advancedCritera: AdvancedCriteria[];

    // case report specific
    investigationDefaultValues?: InvestigationDefaultValue[];

    // lab report specific
    logicMode?: 'OR' | 'AND';
    labCriteria?: LabCriteria[];
    timeFrame?: TimeFrame;
}

export interface TimeFrame {
    operator: '<' | '<=' | '>' | '>=';
    value: string;
}

export interface AdvancedCriteria {
    question: string;
    logic: string;
    value: string;
    valueInputType: 'textarea' | 'input' | 'date' | 'drop-down' | 'select';
}

export interface LabCriteria {
    resultedTest: Test;
    codedResult?: CodedResult;
    numericResultOperator?: '<' | '<=' | '=' | '>' | '>=' | 'Between' | 'Not Equal';
    numericResultValue?: string;
    numericResultType?: string;
    textResultOperator?: 'Contains' | 'Equal' | 'Not Equal' | 'Starts With';
    textResultValue?: string;
}

export interface InvestigationDefaultValue {
    question: string;
    value: string;
    valueInputType: 'textarea' | 'input' | 'date' | 'drop-down' | 'select';
    behvaior: 'Overwrite Existing Values' | 'Update Only Null Values';
}
