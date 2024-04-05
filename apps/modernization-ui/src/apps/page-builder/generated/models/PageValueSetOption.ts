/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
import type { ValueSetOption } from './ValueSetOption';
export type PageValueSetOption = {
    totalElements?: number;
    totalPages?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: Array<SortObject>;
    size?: number;
    content?: Array<ValueSetOption>;
    pageable?: PageableObject;
    numberOfElements?: number;
    empty?: boolean;
};

