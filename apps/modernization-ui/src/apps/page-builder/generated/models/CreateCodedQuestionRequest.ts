/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo0 } from './MessagingInfo0';
import type { ReportingInfo } from './ReportingInfo';

export type CreateCodedQuestionRequest = {
    adminComments?: string;
    codeSet?: CreateCodedQuestionRequest.codeSet;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    label?: string;
    messagingInfo?: MessagingInfo0;
    subgroup?: string;
    tooltip?: string;
    uniqueId?: string;
    uniqueName?: string;
    valueSet?: number;
};

export namespace CreateCodedQuestionRequest {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }


}

