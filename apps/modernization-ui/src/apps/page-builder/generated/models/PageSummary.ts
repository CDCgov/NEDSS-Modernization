/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Condition } from './Condition';
import type { EventType } from './EventType';
import type { MessageMappingGuide } from './MessageMappingGuide';

export type PageSummary = {
    conditions?: Array<Condition>;
    description?: string;
    eventType?: EventType;
    id?: number;
    lastUpdate?: string;
    lastUpdateBy?: string;
    messageMappingGuide?: MessageMappingGuide;
    name?: string;
    status?: string;
};

