/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ConceptMessagingInfo } from './ConceptMessagingInfo';

export type UpdateConceptRequest = {
    active: boolean;
    conceptCode: string;
    conceptMessagingInfo?: ConceptMessagingInfo;
    displayName: string;
    effectiveToTime?: string;
};

