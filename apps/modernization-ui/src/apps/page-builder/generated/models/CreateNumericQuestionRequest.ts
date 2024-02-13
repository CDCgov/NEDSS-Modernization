/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateNumericQuestionRequest = {
    adminComments?: string;
    codeSet?: CreateNumericQuestionRequest.codeSet;
    dataMartInfo?: ReportingInfo;
    defaultValue?: number;
    description?: string;
    displayControl?: number;
    fieldLength?: number;
    label?: string;
    mask?: CreateNumericQuestionRequest.mask;
    maxValue?: number;
    messagingInfo?: MessagingInfo;
    minValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
    subgroup?: string;
    tooltip?: string;
    uniqueId?: string;
    uniqueName?: string;
};

export namespace CreateNumericQuestionRequest {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }

    export enum mask {
        NUM = 'NUM',
        NUM_DD = 'NUM_DD',
        NUM_EXT = 'NUM_EXT',
        NUM_MM = 'NUM_MM',
        NUM_SN = 'NUM_SN',
        NUM_TEMP = 'NUM_TEMP',
        NUM_YYYY = 'NUM_YYYY',
    }


}

