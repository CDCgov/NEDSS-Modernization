/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';
export type CreateDateQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    codeSet?: CreateDateQuestionRequest.codeSet;
    uniqueId?: string;
    subgroup?: string;
    mask?: CreateDateQuestionRequest.mask;
    allowFutureDates?: boolean;
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

