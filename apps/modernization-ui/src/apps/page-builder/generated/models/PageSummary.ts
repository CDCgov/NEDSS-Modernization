/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ConditionSummary } from './ConditionSummary';
import type { EventType } from './EventType';
import type { MessageMappingGuide } from './MessageMappingGuide';

export type PageSummary = {
    conditions?: Array<ConditionSummary>;
    description?: string;
    eventType?: EventType;
    id?: number;
    lastUpdate?: string;
    lastUpdateBy?: string;
    messageMappingGuide?: MessageMappingGuide;
    name?: string;
    status?: string;
};

