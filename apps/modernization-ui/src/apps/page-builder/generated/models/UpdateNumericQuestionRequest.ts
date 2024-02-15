/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdateNumericQuestionRequest = {
    adminComments?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: number;
    description?: string;
    displayControl?: number;
    fieldLength?: number;
    label?: string;
    mask?: UpdateNumericQuestionRequest.mask;
    maxValue?: number;
    messagingInfo?: MessagingInfo;
    minValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
    tooltip?: string;
    type?: UpdateNumericQuestionRequest.type;
    uniqueName?: string;
};

export namespace UpdateNumericQuestionRequest {

    export enum mask {
        NUM = 'NUM',
        NUM_DD = 'NUM_DD',
        NUM_EXT = 'NUM_EXT',
        NUM_MM = 'NUM_MM',
        NUM_SN = 'NUM_SN',
        NUM_TEMP = 'NUM_TEMP',
        NUM_YYYY = 'NUM_YYYY',
    }

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }


}

