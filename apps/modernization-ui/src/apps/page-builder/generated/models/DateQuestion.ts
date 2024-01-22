/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { DataMartInfo } from './DataMartInfo';
import type { MessagingInfo } from './MessagingInfo';
import type { Question } from './Question';

export type DateQuestion = (Question & {
    adminComments?: string;
    allowFutureDates?: boolean;
    codeSet?: string;
    dataMartInfo?: DataMartInfo;
    description?: string;
    displayControl?: number;
    id?: number;
    label?: string;
    mask?: string;
    messagingInfo?: MessagingInfo;
    status?: string;
    subgroup?: string;
    tooltip?: string;
    type?: string;
    uniqueId?: string;
    uniqueName?: string;
});

