/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FilterType } from './FilterType';
export type BasicFilterConfiguration = {
    reportFilterUid: number;
    reportColumnUid?: number;
    defaultValue?: Array<string>;
    minValueCount?: number;
    maxValueCount?: number;
    isRequired: boolean;
    filterType: FilterType;
};

