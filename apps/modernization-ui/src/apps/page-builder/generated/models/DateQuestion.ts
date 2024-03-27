/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { DataMartInfo } from './DataMartInfo';
import type { MessagingInfo } from './MessagingInfo';
import type { Question } from './Question';

export type DateQuestion = (Question & {
    id?: number;
    mask?: string;
    allowFutureDates?: boolean;
    codeSet?: string;
    uniqueId?: string;
    uniqueName?: string;
    status?: string;
    subgroup?: string;
    description?: string;
    type?: string;
    label?: string;
    tooltip?: string;
    displayControl?: number;
    adminComments?: string;
    dataMartInfo?: DataMartInfo;
    messagingInfo?: MessagingInfo;
});

