/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdatePageTextQuestionRequest = {
    adminComments?: string;
    dataMartInfo: ReportingInfo;
    defaultValue?: string;
    displayControl?: number;
    enabled: boolean;
    fieldLength?: number;
    label: string;
    messagingInfo: MessagingInfo;
    required: boolean;
    tooltip: string;
    visible: boolean;
};

