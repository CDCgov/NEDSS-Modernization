/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ReportDataSourceFilterableColumnsService {
    /**
     * Filterable report columns for a data source
     * Provides all filterable report columns in a data source in NBS.
     * @returns Option OK
     * @throws ApiError
     */
    public static reportDataSourceColumns({
        dataSourceUid,
    }: {
        dataSourceUid: string,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/report/datasource/{dataSourceUid}/columns/filterable',
            path: {
                'dataSourceUid': dataSourceUid,
            },
        });
    }
}
