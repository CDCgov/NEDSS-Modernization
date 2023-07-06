/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Condition } from './Condition';
import type { EventType } from './EventType';

export type PageSummary = {
    conditions?: Array<Condition>;
    eventType?: EventType;
    id?: number;
    lastUpdate?: string;
    lastUpdateBy?: string;
    messageMappingGuide?: string;
    name?: string;
    status?: string;
};

