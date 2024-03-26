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
    number?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    numberOfElements?: number;
    size?: number;
    content?: Array<Concept>;
    empty?: boolean;
};

