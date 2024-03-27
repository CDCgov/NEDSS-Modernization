/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { SelectableCondition } from './SelectableCondition';
import type { SelectableEventType } from './SelectableEventType';
import type { SelectableMessageMappingGuide } from './SelectableMessageMappingGuide';

export type PageInformation = {
    page: number;
    eventType: SelectableEventType;
    messageMappingGuide: SelectableMessageMappingGuide;
    name: string;
    datamart?: string;
    description?: string;
    conditions: Array<SelectableCondition>;
};

