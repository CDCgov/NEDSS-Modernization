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
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    size?: number;
    content?: Array<Condition>;
    empty?: boolean;
};

