/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateNumericQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    codeSet?: CreateNumericQuestionRequest.codeSet;
    uniqueId?: string;
    subgroup?: string;
    mask?: CreateNumericQuestionRequest.mask;
    fieldLength?: number;
    defaultValue?: number;
    minValue?: number;
    maxValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
};

export namespace CreateNumericQuestionRequest {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
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

