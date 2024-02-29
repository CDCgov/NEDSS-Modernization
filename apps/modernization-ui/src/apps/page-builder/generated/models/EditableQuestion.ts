/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { MessagingInfo } from './MessagingInfo';
import type { ReportingInfo } from './ReportingInfo';

export type EditableQuestion = {
    adminComments?: string;
    allowFutureDates?: boolean;
    codeSet: EditableQuestion.codeSet;
    dataMartInfo: ReportingInfo;
    defaultValue?: string;
    description: string;
    displayControl: number;
    enabled: boolean;
    fieldLength?: number;
    label: string;
    mask?: string;
    maxValue?: number;
    messagingInfo: MessagingInfo;
    minValue?: number;
    questionType?: string;
    relatedUnitsLiteral?: string;
    relatedUnitsValueSet?: number;
    required: boolean;
    subgroup: string;
    tooltip: string;
    uniqueId: string;
    uniqueName: string;
    valueSet?: number;
    visible: boolean;
};

export namespace EditableQuestion {

    export enum codeSet {
        LOCAL = 'LOCAL',
        PHIN = 'PHIN',
    }


}

