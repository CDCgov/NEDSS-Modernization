/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class DistinctValuesService {
    /**
     * Distinct values in a report column
     * Provides all distinct options for a specific report column.
     * @returns Option OK
     * @throws ApiError
     */
    public static distinctValues({
        columnUid,
    }: {
        columnUid: string,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/report/distinct/{columnUid}',
            path: {
                'columnUid': columnUid,
            },
        });
    }
}
