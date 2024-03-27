/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { DataMartInfo } from './DataMartInfo';
import type { MessagingInfo } from './MessagingInfo';
import type { Question } from './Question';

export type TextQuestion = (Question & {
    id?: number;
    mask?: string;
    fieldSize?: string;
    defaultValue?: string;
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

