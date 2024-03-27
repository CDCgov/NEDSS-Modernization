/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdatePageNumericQuestionRequest = {
    label: string;
    tooltip: string;
    visible: boolean;
    enabled: boolean;
    required: boolean;
    displayControl?: number;
    mask?: UpdatePageNumericQuestionRequest.mask;
    fieldLength?: number;
    defaultValue?: number;
    minValue?: number;
    maxValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
    dataMartInfo: ReportingInfo;
    messagingInfo: MessagingInfo;
    adminComments?: string;
};

export namespace UpdatePageNumericQuestionRequest {

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

