/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Concept } from './Concept';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageConcept = {
    totalPages?: number;
    totalElements?: number;
    pageable?: PageableObject;
    numberOfElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: SortObject;
    size?: number;
    content?: Array<Concept>;
    empty?: boolean;
};

