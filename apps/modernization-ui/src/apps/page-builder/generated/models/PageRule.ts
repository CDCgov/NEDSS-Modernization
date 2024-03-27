/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { Rule } from './Rule';
import type { SortObject } from './SortObject';

export type PageRule = {
    totalElements?: number;
    totalPages?: number;
    pageable?: PageableObject;
    size?: number;
    content?: Array<Rule>;
    numberOfElements?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    number?: number;
    empty?: boolean;
};

