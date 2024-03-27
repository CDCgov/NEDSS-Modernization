/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Concept } from './Concept';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';

export type PageConcept = {
    totalElements?: number;
    totalPages?: number;
    pageable?: PageableObject;
    size?: number;
    content?: Array<Concept>;
    numberOfElements?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    number?: number;
    empty?: boolean;
};

