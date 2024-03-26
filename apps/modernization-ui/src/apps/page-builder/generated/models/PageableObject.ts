/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { SortObject } from './SortObject';

export type PageableObject = {
    paged?: boolean;
    unpaged?: boolean;
    pageNumber?: number;
    pageSize?: number;
    sort?: SortObject;
    offset?: number;
};

