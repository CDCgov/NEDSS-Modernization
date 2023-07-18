/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CreateQuestionRequest } from './CreateQuestionRequest';
import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type Date = (CreateQuestionRequest & {
    adminComments?: string;
    allowFutureDates?: boolean;
    codeSet?: string;
    dataMartInfo?: ReportingInfo;
    description?: string;
    displayControl?: number;
    label?: string;
    mask?: string;
    messagingInfo?: MessagingInfo;
    subgroup?: string;
    tooltip?: string;
    type?: Date.type;
    uniqueId?: string;
    uniqueName?: string;
});

export namespace Date {

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }


}

