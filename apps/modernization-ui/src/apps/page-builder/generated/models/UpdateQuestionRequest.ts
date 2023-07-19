/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type UpdateQuestionRequest = {
    adminComments?: string;
    allowFutureDates?: boolean;
    codeSystem?: string;
    datamartColumnName?: string;
    defaultLabelInReport?: string;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    fieldLength?: string;
    hl7DataType?: string;
    includedInMessage?: boolean;
    label?: string;
    labelInMessage?: string;
    mask?: string;
    maxValue?: number;
    messageVariableId?: string;
    minValue?: number;
    rdbColumnName?: string;
    requiredInMessage?: boolean;
    tooltip?: string;
    type?: UpdateQuestionRequest.type;
    uniqueName?: string;
    unitType?: UpdateQuestionRequest.unitType;
    unitValue?: string;
    valueSet?: number;
};

export namespace UpdateQuestionRequest {

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }

    export enum unitType {
        CODED = 'CODED',
        LITERAL = 'LITERAL',
    }


}

