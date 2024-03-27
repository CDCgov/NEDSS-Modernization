/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { AvailableQuestion } from './AvailableQuestion';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageAvailableQuestion = {
    totalElements?: number;
    totalPages?: number;
    pageable?: PageableObject;
    size?: number;
    content?: Array<AvailableQuestion>;
    numberOfElements?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    number?: number;
    empty?: boolean;
};

