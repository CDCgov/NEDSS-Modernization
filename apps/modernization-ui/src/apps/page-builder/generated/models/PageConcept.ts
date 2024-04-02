/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Concept } from './Concept';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
export type PageConcept = {
    totalElements?: number;
    totalPages?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: Array<SortObject>;
    size?: number;
    content?: Array<Concept>;
    pageable?: PageableObject;
    numberOfElements?: number;
    empty?: boolean;
};

