/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdateCodedQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    type?: UpdateCodedQuestionRequest.type;
    valueSet?: number;
    defaultValue?: string;
};

export namespace UpdateCodedQuestionRequest {

    export enum type {
        TEXT = 'TEXT',
        NUMERIC = 'NUMERIC',
        DATE = 'DATE',
        CODED = 'CODED',
    }


}

