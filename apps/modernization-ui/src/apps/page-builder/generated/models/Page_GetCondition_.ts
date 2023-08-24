/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GetCondition } from './GetCondition';
import type { Pageable } from './Pageable';
import type { Sort } from './Sort';

export type Page_GetCondition_ = {
    content?: Array<GetCondition>;
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

