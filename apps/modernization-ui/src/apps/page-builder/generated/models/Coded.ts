/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CreateQuestionRequest } from './CreateQuestionRequest';
import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type Coded = (CreateQuestionRequest & {
    adminComments?: string;
    codeSet?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    label?: string;
    messagingInfo?: MessagingInfo;
    subgroup?: string;
    tooltip?: string;
    type?: Coded.type;
    uniqueId?: string;
    uniqueName?: string;
    valueSet?: number;
});

export namespace Coded {

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }


}

