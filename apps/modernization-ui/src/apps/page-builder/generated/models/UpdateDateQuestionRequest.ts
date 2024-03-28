/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';
export type UpdateDateQuestionRequest = {
    uniqueName?: string;
    description?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    dataMartInfo?: ReportingInfo;
    messagingInfo?: MessagingInfo;
    adminComments?: string;
    type?: UpdateDateQuestionRequest.type;
    mask?: UpdateDateQuestionRequest.mask;
    allowFutureDates?: boolean;
};
export namespace UpdateDateQuestionRequest {
    export enum type {
        TEXT = 'TEXT',
        NUMERIC = 'NUMERIC',
        DATE = 'DATE',
        CODED = 'CODED',
    }
    export enum mask {
        DATE = 'DATE',
    }
}

