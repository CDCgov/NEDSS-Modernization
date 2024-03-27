/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateTextQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    codeSet?: CreateTextQuestionRequest.codeSet;
    uniqueId?: string;
    subgroup?: string;
    mask?: CreateTextQuestionRequest.mask;
    fieldLength?: number;
    defaultValue?: string;
};

export namespace CreateTextQuestionRequest {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }

    export enum mask {
        TXT = 'TXT',
        CENSUS_TRACT = 'CENSUS_TRACT',
        TXT_EMAIL = 'TXT_EMAIL',
        TXT_ID10 = 'TXT_ID10',
        TXT_ID12 = 'TXT_ID12',
        TXT_ID15 = 'TXT_ID15',
        TXT_PHONE = 'TXT_PHONE',
        TXT_SSN = 'TXT_SSN',
        TXT_IDTB = 'TXT_IDTB',
        TXT_ZIP = 'TXT_ZIP',
    }


}

