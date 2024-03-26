/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdatePageTextQuestionRequest = {
    label: string;
    tooltip: string;
    visible: boolean;
    enabled: boolean;
    required: boolean;
    displayControl?: number;
    defaultValue?: string;
    fieldLength?: number;
    dataMartInfo: ReportingInfo;
    messagingInfo: MessagingInfo;
    adminComments?: string;
};

