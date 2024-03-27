/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { PageHistory } from './PageHistory';
import type { SortObject } from './SortObject';

export type PagePageHistory = {
    totalElements?: number;
    totalPages?: number;
    pageable?: PageableObject;
    size?: number;
    content?: Array<PageHistory>;
    numberOfElements?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    number?: number;
    empty?: boolean;
};

