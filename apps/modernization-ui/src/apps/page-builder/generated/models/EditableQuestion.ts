/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type EditableQuestion = {
    codeSet: EditableQuestion.codeSet;
    uniqueName: string;
    uniqueId: string;
    subgroup: string;
    description: string;
    label: string;
    tooltip: string;
    displayControl: number;
    visible: boolean;
    enabled: boolean;
    required: boolean;
    adminComments?: string;
    questionType?: string;
    defaultValue?: string;
    fieldLength?: number;
    mask?: string;
    allowFutureDates?: boolean;
    valueSet?: number;
    minValue?: number;
    maxValue?: number;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
    dataMartInfo: ReportingInfo;
    messagingInfo: MessagingInfo;
};

export namespace EditableQuestion {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }


}

