/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FilterConfiguration } from './FilterConfiguration';
import type { Library } from './Library';
import type { ReportColumn } from './ReportColumn';
import type { ReportDataSource } from './ReportDataSource';
export type ReportConfiguration = {
    dataSource: ReportDataSource;
    reportLibrary: Library;
    reportTitle: string;
    filters: Array<FilterConfiguration>;
    reportColumns?: Array<ReportColumn>;
    python?: boolean;
};

