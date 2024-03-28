/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';
export type UpdateNumericQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    type?: UpdateNumericQuestionRequest.type;
    mask?: UpdateNumericQuestionRequest.mask;
    fieldLength?: number;
    defaultValue?: number;
    minValue?: number;
    maxValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
};
export namespace UpdateNumericQuestionRequest {
    export enum type {
        TEXT = 'TEXT',
        NUMERIC = 'NUMERIC',
        DATE = 'DATE',
        CODED = 'CODED',
    }
    export enum mask {
        NUM_DD = 'NUM_DD',
        NUM_MM = 'NUM_MM',
        NUM_YYYY = 'NUM_YYYY',
        NUM = 'NUM',
        NUM_EXT = 'NUM_EXT',
        NUM_SN = 'NUM_SN',
        NUM_TEMP = 'NUM_TEMP',
    }
}

