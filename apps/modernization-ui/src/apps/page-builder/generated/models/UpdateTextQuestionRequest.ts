/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo0 } from './MessagingInfo0';
import type { ReportingInfo } from './ReportingInfo';

export type UpdateTextQuestionRequest = {
    adminComments?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    fieldLength?: number;
    label?: string;
    mask?: UpdateTextQuestionRequest.mask;
    messagingInfo?: MessagingInfo0;
    tooltip?: string;
    type?: UpdateTextQuestionRequest.type;
    uniqueName?: string;
};

export namespace UpdateTextQuestionRequest {

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

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }


}

