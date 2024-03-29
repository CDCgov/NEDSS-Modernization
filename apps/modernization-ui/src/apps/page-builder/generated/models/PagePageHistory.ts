/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageableObject } from './PageableObject';
import type { PageHistory } from './PageHistory';
import type { SortObject } from './SortObject';
export type PagePageHistory = {
    totalPages?: number;
    totalElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: Array<SortObject>;
    size?: number;
    content?: Array<PageHistory>;
    pageable?: PageableObject;
    numberOfElements?: number;
    empty?: boolean;
};

