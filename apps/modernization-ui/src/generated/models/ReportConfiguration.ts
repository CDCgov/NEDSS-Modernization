/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FilterConfiguration } from './FilterConfiguration';
import type { ReportColumn } from './ReportColumn';
export type ReportConfiguration = {
    runner: string;
    filters: Array<FilterConfiguration>;
    reportColumns?: Array<ReportColumn>;
    python?: boolean;
};

