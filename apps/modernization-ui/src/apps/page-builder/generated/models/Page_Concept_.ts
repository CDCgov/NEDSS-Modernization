/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Concept } from './Concept';
import type { Pageable } from './Pageable';
import type { Sort } from './Sort';

export type Page_Concept_ = {
    content?: Array<Concept>;
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

