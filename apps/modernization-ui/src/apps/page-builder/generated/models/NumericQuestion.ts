/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DataMartInfo } from './DataMartInfo';
import type { MessagingInfo } from './MessagingInfo';
import type { Question } from './Question';
export type NumericQuestion = (Question & {
    id?: number;
    mask?: string;
    fieldLength?: string;
    defaultValue?: string;
    minValue?: number;
    maxValue?: number;
    unitTypeCd?: string;
    unitValue?: string;
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

