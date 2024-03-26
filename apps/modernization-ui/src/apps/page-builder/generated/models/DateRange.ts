/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { FilterJSON } from './FilterJSON';

export type DateRange = (FilterJSON & {
    property?: string;
    after?: string;
    before?: string;
} & {
    property: string;
});

