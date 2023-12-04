/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateDateQuestionRequest = {
    adminComments?: string;
    allowFutureDates?: boolean;
    codeSet?: CreateDateQuestionRequest.codeSet;
    dataMartInfo?: ReportingInfo;
    description?: string;
    displayControl?: number;
    label?: string;
    mask?: CreateDateQuestionRequest.mask;
    messagingInfo?: MessagingInfo;
    subgroup?: string;
    tooltip?: string;
    uniqueId?: string;
    uniqueName?: string;
};

export namespace CreateDateQuestionRequest {
    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }
    export enum mask {
        DATE = 'DATE',
    }
}

