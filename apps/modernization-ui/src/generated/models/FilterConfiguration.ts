/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FilterDefaultValue } from './FilterDefaultValue';
import type { FilterType } from './FilterType';
export type FilterConfiguration = {
    reportFilterUid: number;
    reportColumnUid?: number;
    minValueCount?: number;
    maxValueCount?: number;
    filterType: FilterType;
    filterDefaultValues: Array<FilterDefaultValue>;
};

