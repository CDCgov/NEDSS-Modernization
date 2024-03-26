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
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    size?: number;
    content?: Array<Rule>;
    empty?: boolean;
};

