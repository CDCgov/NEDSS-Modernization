/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo0 } from './MessagingInfo0';
import type { ReportingInfo } from './ReportingInfo';

export type UpdateCodedQuestionRequest = {
    adminComments?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    label?: string;
    messagingInfo?: MessagingInfo0;
    tooltip?: string;
    type?: UpdateCodedQuestionRequest.type;
    uniqueName?: string;
    valueSet?: number;
};

export namespace UpdateCodedQuestionRequest {

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }


}

