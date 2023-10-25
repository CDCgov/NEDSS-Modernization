/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateCodedQuestionRequest = {
    adminComments?: string;
    codeSet?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    label?: string;
    messagingInfo?: MessagingInfo;
    subgroup?: string;
    tooltip?: string;
    uniqueId?: string;
    uniqueName?: string;
    valueSet?: number;
};

