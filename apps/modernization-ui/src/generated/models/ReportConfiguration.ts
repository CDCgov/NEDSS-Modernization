/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Library } from './Library';
import type { ReportDataSource } from './ReportDataSource';
export type ReportConfiguration = {
    runner: string;
    dataSource: ReportDataSource;
    reportLibrary: Library;
    python?: boolean;
};

