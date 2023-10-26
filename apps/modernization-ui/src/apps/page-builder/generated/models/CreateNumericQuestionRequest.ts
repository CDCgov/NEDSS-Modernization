/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type CreateNumericQuestionRequest = {
    adminComments?: string;
    codeSet?: string;
    dataMartInfo?: ReportingInfo;
    defaultValue?: number;
    description?: string;
    displayControl?: number;
    fieldLength?: number;
    label?: string;
    mask?: string;
    maxValue?: number;
    messagingInfo?: MessagingInfo;
    minValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
    subgroup?: string;
    tooltip?: string;
    uniqueId?: string;
    uniqueName?: string;
};

