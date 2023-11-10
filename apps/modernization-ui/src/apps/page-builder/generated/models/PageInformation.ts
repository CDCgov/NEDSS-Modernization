/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { SelectableCondition } from './SelectableCondition';
import type { SelectableEventType } from './SelectableEventType';
import type { SelectableMessageMappingGuide } from './SelectableMessageMappingGuide';

export type PageInformation = {
    conditions?: Array<SelectableCondition>;
    datamart?: string;
    description?: string;
    eventType?: SelectableEventType;
    messageMappingGuide?: SelectableMessageMappingGuide;
    name?: string;
    page?: number;
};

