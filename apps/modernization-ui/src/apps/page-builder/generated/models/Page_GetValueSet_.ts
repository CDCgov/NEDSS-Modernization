/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GetValueSet } from './GetValueSet';
import type { Pageable } from './Pageable';
import type { Sort } from './Sort';

export type Page_GetValueSet_ = {
    content?: Array<GetValueSet>;
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

