/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { Rule } from './Rule';
import type { SortObject } from './SortObject';

export type PageRule = {
    totalPages?: number;
    totalElements?: number;
    pageable?: PageableObject;
    numberOfElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: SortObject;
    size?: number;
    content?: Array<Rule>;
    empty?: boolean;
};

