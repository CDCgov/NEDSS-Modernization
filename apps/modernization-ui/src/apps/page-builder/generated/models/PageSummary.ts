/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ConditionSummary } from './ConditionSummary';
import type { EventType } from './EventType';

export type PageSummary = {
    conditions?: Array<ConditionSummary>;
    eventType?: EventType;
    id?: number;
    lastUpdate?: string;
    lastUpdateBy?: string;
    name?: string;
    status?: string;
};

