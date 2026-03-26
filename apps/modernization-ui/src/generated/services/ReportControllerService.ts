/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ReportConfiguration } from '../models/ReportConfiguration';
import type { ReportExecutionRequest } from '../models/ReportExecutionRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ReportControllerService {
    /**
     * @returns string OK
     * @throws ApiError
     */
    public static executeReport({
        requestBody,
    }: {
        requestBody: ReportExecutionRequest,
    }): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/report/execute',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ReportConfiguration OK
     * @throws ApiError
     */
    public static getReportConfiguration({
        reportUid,
        dataSourceUid,
    }: {
        reportUid: number,
        dataSourceUid: number,
    }): CancelablePromise<ReportConfiguration> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/report/configuration/{reportUid}/{dataSourceUid}',
            path: {
                'reportUid': reportUid,
                'dataSourceUid': dataSourceUid,
            },
        });
    }
}
