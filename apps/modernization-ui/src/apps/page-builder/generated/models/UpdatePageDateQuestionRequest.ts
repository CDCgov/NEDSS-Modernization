/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdatePageDateQuestionRequest = {
    adminComments?: string;
    allowFutureDates?: boolean;
    dataMartInfo: ReportingInfo;
    displayControl?: number;
    enabled: boolean;
    label: string;
    mask?: UpdatePageDateQuestionRequest.mask;
    messagingInfo: MessagingInfo;
    required: boolean;
    tooltip: string;
    visible: boolean;
};

export namespace UpdatePageDateQuestionRequest {
    export enum mask {
        DATE = 'DATE'
    }
}
