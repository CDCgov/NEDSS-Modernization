/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
import type { ValueSetOption } from './ValueSetOption';

export type PageValueSetOption = {
    totalElements?: number;
    totalPages?: number;
    pageable?: PageableObject;
    size?: number;
    content?: Array<ValueSetOption>;
    numberOfElements?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    number?: number;
    empty?: boolean;
};

