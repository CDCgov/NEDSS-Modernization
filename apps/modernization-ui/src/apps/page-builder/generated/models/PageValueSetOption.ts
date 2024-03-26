/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
import type { ValueSetOption } from './ValueSetOption';

export type PageValueSetOption = {
    totalPages?: number;
    totalElements?: number;
    pageable?: PageableObject;
    numberOfElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: SortObject;
    size?: number;
    content?: Array<ValueSetOption>;
    empty?: boolean;
};

