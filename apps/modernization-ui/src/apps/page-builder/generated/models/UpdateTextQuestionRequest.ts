/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdateTextQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    type?: UpdateTextQuestionRequest.type;
    mask?: UpdateTextQuestionRequest.mask;
    fieldLength?: number;
    defaultValue?: string;
};

export namespace UpdateTextQuestionRequest {

    export enum type {
        TEXT = 'TEXT',
        NUMERIC = 'NUMERIC',
        DATE = 'DATE',
        CODED = 'CODED',
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

