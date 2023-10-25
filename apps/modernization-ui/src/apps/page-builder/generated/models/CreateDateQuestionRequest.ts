/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateDateQuestionRequest = {
    adminComments?: string;
    allowFutureDates?: boolean;
    codeSet?: string;
    dataMartInfo?: ReportingInfo;
    description?: string;
    displayControl?: number;
    label?: string;
    mask?: string;
    messagingInfo?: MessagingInfo;
    subgroup?: string;
    tooltip?: string;
    uniqueId?: string;
    uniqueName?: string;
};

