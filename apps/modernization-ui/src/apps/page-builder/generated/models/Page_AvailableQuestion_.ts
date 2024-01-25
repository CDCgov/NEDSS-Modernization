/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { AvailableQuestion } from './AvailableQuestion';
import type { Pageable } from './Pageable';
import type { Sort } from './Sort';

export type Page_AvailableQuestion_ = {
    content?: Array<AvailableQuestion>;
    empty?: boolean;
    first?: boolean;
    last?: boolean;
    number?: number;
    numberOfElements?: number;
    pageable?: Pageable;
    size?: number;
    sort?: Sort;
    totalElements?: number;
    totalPages?: number;
};

