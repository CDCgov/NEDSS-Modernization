/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';
export type UpdatePageCodedQuestionRequest = {
    label: string;
    tooltip: string;
    visible: boolean;
    enabled: boolean;
    required: boolean;
    displayControl?: number;
    valueSet?: number;
    defaultValue?: string;
    dataMartInfo: ReportingInfo;
    messagingInfo: MessagingInfo;
    adminComments?: string;
};

