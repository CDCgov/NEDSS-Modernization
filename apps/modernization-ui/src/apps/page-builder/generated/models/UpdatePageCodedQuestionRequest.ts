/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type UpdatePageCodedQuestionRequest = {
    adminComments?: string;
    datamartInfo: ReportingInfo;
    defaultValue?: string;
    displayControl?: number;
    enabled: boolean;
    label: string;
    messagingInfo: MessagingInfo;
    required: boolean;
    tooltip: string;
    valueSet?: number;
    visible: boolean;
};

