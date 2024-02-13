/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdateCodedQuestionRequest = {
    adminComments?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    label?: string;
    messagingInfo?: MessagingInfo;
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

