/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AdvancedFilterConfiguration } from './AdvancedFilterConfiguration';
import type { BasicFilterConfiguration } from './BasicFilterConfiguration';
import type { Library } from './Library';
import type { ReportColumn } from './ReportColumn';
import type { ReportDataSource } from './ReportDataSource';
export type ReportConfiguration = {
    dataSource: ReportDataSource;
    library: Library;
    title: string;
    basicFilters: Array<BasicFilterConfiguration>;
    advancedFilter?: AdvancedFilterConfiguration;
    columns: Array<ReportColumn>;
    defaultColumnUids?: Array<number>;
};

