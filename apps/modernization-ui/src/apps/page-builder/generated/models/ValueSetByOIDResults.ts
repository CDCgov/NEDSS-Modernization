/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { ValueSetConcept } from './ValueSetConcept';

export type ValueSetByOIDResults = {
    status?: string;
    valueSetCode?: string;
    valueSetConcepts?: Array<ValueSetConcept>;
    valueSetDesc?: string;
    valueSetName?: string;
    valueSetType?: string;
};

