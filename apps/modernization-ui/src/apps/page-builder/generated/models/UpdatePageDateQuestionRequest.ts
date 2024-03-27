/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdatePageDateQuestionRequest = {
    label: string;
    tooltip: string;
    visible: boolean;
    enabled: boolean;
    required: boolean;
    displayControl?: number;
    mask?: UpdatePageDateQuestionRequest.mask;
    allowFutureDates?: boolean;
    dataMartInfo: ReportingInfo;
    messagingInfo: MessagingInfo;
    adminComments?: string;
};

export namespace UpdatePageDateQuestionRequest {

    export enum mask {
        DATE = 'DATE',
    }


}

