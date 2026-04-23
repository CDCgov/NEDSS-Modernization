/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Clause } from './Clause';
import type { Connector } from './Connector';
import type { FilterType } from './FilterType';
export type AdvancedFilterConfiguration = {
    reportFilterUid: number;
    reportColumnUid?: number;
    defaultValue?: (Clause | Connector);
    filterType: FilterType;
};

