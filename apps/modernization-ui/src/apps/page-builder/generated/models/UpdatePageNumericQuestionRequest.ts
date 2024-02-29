/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdatePageNumericQuestionRequest = {
    adminComments?: string;
    dataMartInfo: ReportingInfo;
    defaultValue?: number;
    displayControl?: number;
    enabled: boolean;
    fieldLength?: number;
    label: string;
    mask?: UpdatePageNumericQuestionRequest.mask;
    maxValue?: number;
    messagingInfo: MessagingInfo;
    minValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
    required: boolean;
    tooltip: string;
    visible: boolean;
};

export namespace UpdatePageNumericQuestionRequest {
    export enum mask {
        NUM = 'NUM',
        NUM_DD = 'NUM_DD',
        NUM_EXT = 'NUM_EXT',
        NUM_MM = 'NUM_MM',
        NUM_SN = 'NUM_SN',
        NUM_TEMP = 'NUM_TEMP',
        NUM_YYYY = 'NUM_YYYY'
    }
}
