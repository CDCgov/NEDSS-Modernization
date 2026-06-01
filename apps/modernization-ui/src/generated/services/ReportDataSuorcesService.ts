/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ReportDataSuorcesService {
    /**
     * Report data sources
     * Provides all report data sources in NBS.
     * @returns Option OK
     * @throws ApiError
     */
    public static reportDataSources(): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/report/datasources',
        });
    }
}
