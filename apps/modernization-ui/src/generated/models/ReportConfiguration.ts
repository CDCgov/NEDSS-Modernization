/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Library } from './Library';
import type { ReportDataSource } from './ReportDataSource';
import type { FilterConfiguration } from './FilterConfiguration';
import type { ReportColumn } from './ReportColumn';
export type ReportConfiguration = {
    runner: string;
    dataSource: ReportDataSource;
    reportLibrary: Library;
    filters: Array<FilterConfiguration>;
    reportColumns?: Array<ReportColumn>;
    python?: boolean;
};

