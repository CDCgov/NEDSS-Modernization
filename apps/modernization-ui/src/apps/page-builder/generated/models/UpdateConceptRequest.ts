/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ConceptMessagingInfo } from './ConceptMessagingInfo';

export type UpdateConceptRequest = {
    active: boolean;
    adminComments?: string;
    conceptMessagingInfo?: ConceptMessagingInfo;
    displayName: string;
    effectiveFromTime: string;
    effectiveToTime?: string;
    longName: string;
};

