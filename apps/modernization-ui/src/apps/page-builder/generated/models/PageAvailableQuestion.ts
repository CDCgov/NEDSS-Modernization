/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { AvailableQuestion } from './AvailableQuestion';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageAvailableQuestion = {
    totalPages?: number;
    totalElements?: number;
    pageable?: PageableObject;
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    size?: number;
    content?: Array<AvailableQuestion>;
    empty?: boolean;
};

