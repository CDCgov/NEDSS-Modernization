/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { DataMartInfo } from './DataMartInfo';
import type { MessagingInfo } from './MessagingInfo';
import type { Question } from './Question';

export type NumericQuestion = (Question & {
    adminComments?: string;
    codeSet?: string;
    dataMartInfo?: DataMartInfo;
    defaultValue?: string;
    description?: string;
    displayControl?: number;
    fieldLength?: string;
    id?: number;
    label?: string;
    mask?: string;
    maxValue?: number;
    messagingInfo?: MessagingInfo;
    minValue?: number;
    status?: string;
    subgroup?: string;
    tooltip?: string;
    type?: string;
    uniqueId?: string;
    uniqueName?: string;
    unitTypeCd?: string;
    unitValue?: string;
});

