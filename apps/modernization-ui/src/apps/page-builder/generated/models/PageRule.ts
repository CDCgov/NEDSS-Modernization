/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageableObject } from './PageableObject';
import type { Rule } from './Rule';
import type { SortObject } from './SortObject';
export type PageRule = {
    totalPages?: number;
    totalElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: Array<SortObject>;
    size?: number;
    content?: Array<Rule>;
    pageable?: PageableObject;
    numberOfElements?: number;
    empty?: boolean;
};

