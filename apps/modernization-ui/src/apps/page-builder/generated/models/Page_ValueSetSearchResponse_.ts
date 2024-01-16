/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Pageable } from './Pageable';
import type { Sort } from './Sort';
import type { ValueSetSearchResponse } from './ValueSetSearchResponse';

export type Page_ValueSetSearchResponse_ = {
    content?: Array<ValueSetSearchResponse>;
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

