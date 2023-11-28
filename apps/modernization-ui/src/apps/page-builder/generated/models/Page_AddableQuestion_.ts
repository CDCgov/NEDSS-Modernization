/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { AddableQuestion } from './AddableQuestion';
import type { Pageable } from './Pageable';
import type { Sort } from './Sort';

export type Page_AddableQuestion_ = {
    content?: Array<AddableQuestion>;
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

