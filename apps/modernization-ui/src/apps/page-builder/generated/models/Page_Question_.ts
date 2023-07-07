/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Pageable } from './Pageable';
import type { Question } from './Question';
import type { Sort } from './Sort';

export type Page_Question_ = {
    content?: Array<Question>;
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

