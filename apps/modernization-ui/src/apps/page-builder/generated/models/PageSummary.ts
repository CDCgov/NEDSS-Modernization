/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Condition } from './Condition';

export type PageSummary = {
    conditions?: Array<Condition>;
    eventType?: string;
    id?: number;
    lastUpdate?: string;
    lastUpdateBy?: string;
    messageMappingGuide?: string;
    name?: string;
    status?: string;
};

