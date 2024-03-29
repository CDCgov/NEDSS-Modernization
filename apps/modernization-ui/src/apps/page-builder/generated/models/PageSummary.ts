/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ConditionSummary } from './ConditionSummary';
import type { EventType } from './EventType';
export type PageSummary = {
    id?: number;
    eventType?: EventType;
    name?: string;
    status?: string;
    conditions?: Array<ConditionSummary>;
    lastUpdate?: string;
    lastUpdateBy?: string;
};

