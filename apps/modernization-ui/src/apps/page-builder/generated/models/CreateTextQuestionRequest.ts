/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateTextQuestionRequest = {
    adminComments?: string;
    codeSet?: CreateTextQuestionRequest.codeSet;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    fieldLength?: number;
    label?: string;
    mask?: CreateTextQuestionRequest.mask;
    messagingInfo?: MessagingInfo;
    subgroup?: string;
    tooltip?: string;
    uniqueId?: string;
    uniqueName?: string;
};

export namespace CreateTextQuestionRequest {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }

    export enum mask {
        CENSUS_TRACT = 'CENSUS_TRACT',
        TXT = 'TXT',
        TXT_EMAIL = 'TXT_EMAIL',
        TXT_ID10 = 'TXT_ID10',
        TXT_ID12 = 'TXT_ID12',
        TXT_ID15 = 'TXT_ID15',
        TXT_IDTB = 'TXT_IDTB',
        TXT_PHONE = 'TXT_PHONE',
        TXT_SSN = 'TXT_SSN',
        TXT_ZIP = 'TXT_ZIP',
    }


}

