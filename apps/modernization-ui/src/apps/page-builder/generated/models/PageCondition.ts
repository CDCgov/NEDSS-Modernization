/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Condition } from './Condition';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageCondition = {
    totalPages?: number;
    totalElements?: number;
    pageable?: PageableObject;
    numberOfElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: SortObject;
    size?: number;
    content?: Array<Condition>;
    empty?: boolean;
};

