/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ReportConfiguration } from '../models/ReportConfiguration';
import type { ReportExecutionRequest } from '../models/ReportExecutionRequest';
import type { ReportResult } from '../models/ReportResult';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ReportControllerService {
    /**
     * @returns ReportResult OK
     * @throws ApiError
     */
    public static runReport({
        requestBody,
    }: {
        requestBody: ReportExecutionRequest,
    }): CancelablePromise<ReportResult> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/report/run',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ReportResult OK
     * @throws ApiError
     */
    public static exportReport({
        requestBody,
    }: {
        requestBody: ReportExecutionRequest,
    }): CancelablePromise<ReportResult> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/report/export',
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
