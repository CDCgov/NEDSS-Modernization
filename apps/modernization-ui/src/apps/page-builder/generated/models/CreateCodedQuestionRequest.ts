/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateCodedQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    codeSet?: CreateCodedQuestionRequest.codeSet;
    uniqueId?: string;
    subgroup?: string;
    valueSet?: number;
    defaultValue?: string;
};

export namespace CreateCodedQuestionRequest {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }


}

