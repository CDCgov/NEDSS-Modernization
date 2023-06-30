/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CreateQuestionRequest } from './CreateQuestionRequest';
import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type Text = (CreateQuestionRequest & {
    adminComments?: string;
    codeSet?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    fieldLength?: string;
    label?: string;
    mask?: string;
    messagingInfo?: MessagingInfo;
    subgroup?: string;
    tooltip?: string;
    type?: Text.type;
    uniqueId?: string;
    uniqueName?: string;
});

export namespace Text {

    export enum type {
        CODED = 'CODED',
        DATE = 'DATE',
        NUMERIC = 'NUMERIC',
        TEXT = 'TEXT',
    }


}

