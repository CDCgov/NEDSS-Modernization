/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { PageSummary } from './PageSummary';
import type { SortObject } from './SortObject';

export type PagePageSummary = {
    totalPages?: number;
    totalElements?: number;
    pageable?: PageableObject;
    numberOfElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: SortObject;
    size?: number;
    content?: Array<PageSummary>;
    empty?: boolean;
};

