/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { SortObject } from './SortObject';

export type PageableObject = {
    pageNumber?: number;
    pageSize?: number;
    paged?: boolean;
    unpaged?: boolean;
    offset?: number;
    sort?: SortObject;
};

