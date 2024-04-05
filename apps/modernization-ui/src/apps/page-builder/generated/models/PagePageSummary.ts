/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageableObject } from './PageableObject';
import type { PageSummary } from './PageSummary';
import type { SortObject } from './SortObject';
export type PagePageSummary = {
    totalElements?: number;
    totalPages?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: Array<SortObject>;
    size?: number;
    content?: Array<PageSummary>;
    pageable?: PageableObject;
    numberOfElements?: number;
    empty?: boolean;
};

