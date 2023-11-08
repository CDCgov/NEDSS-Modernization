/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Filter } from './Filter';

export type DateRangeFilter = (Filter & {
    after?: string;
    before?: string;
    property: string;
});

