/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Condition } from './Condition';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageCondition = {
    totalElements?: number;
    totalPages?: number;
    pageable?: PageableObject;
    size?: number;
    content?: Array<Condition>;
    numberOfElements?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    number?: number;
    empty?: boolean;
};

