/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo0 } from './MessagingInfo0';
import type { ReportingInfo } from './ReportingInfo';

export type UpdateDateQuestionRequest = {
    adminComments?: string;
    allowFutureDates?: boolean;
    dataMartInfo?: ReportingInfo;
    description?: string;
    displayControl?: number;
    label?: string;
    mask?: UpdateDateQuestionRequest.mask;
    messagingInfo?: MessagingInfo0;
    tooltip?: string;
    type?: UpdateDateQuestionRequest.type;
    uniqueName?: string;
};

export namespace UpdateDateQuestionRequest {

    export enum mask {
        DATE = 'DATE',
    }

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }


}

