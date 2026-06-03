/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AdminReportRequest } from '../models/AdminReportRequest';
import type { ReportConfiguration } from '../models/ReportConfiguration';
import type { ReportExecutionRequest } from '../models/ReportExecutionRequest';
import type { ReportId } from '../models/ReportId';
import type { ReportResult } from '../models/ReportResult';
import type { SaveAsReportRequest } from '../models/SaveAsReportRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ReportControllerService {
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
    /**
     * @returns ReportId OK
     * @throws ApiError
     */
    public static editReport({
        reportUid,
        dataSourceUid,
        requestBody,
    }: {
        reportUid: number,
        dataSourceUid: number,
        requestBody: AdminReportRequest,
    }): CancelablePromise<ReportId> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/api/report/configuration/{reportUid}/{dataSourceUid}',
            path: {
                'reportUid': reportUid,
                'dataSourceUid': dataSourceUid,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ReportId OK
     * @throws ApiError
     */
    public static saveReport({
        reportUid,
        dataSourceUid,
        requestBody,
    }: {
        reportUid: number,
        dataSourceUid: number,
        requestBody: ReportExecutionRequest,
    }): CancelablePromise<ReportId> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/api/report/configuration/{reportUid}/{dataSourceUid}/save',
            path: {
                'reportUid': reportUid,
                'dataSourceUid': dataSourceUid,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
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
     * @returns ReportId OK
     * @throws ApiError
     */
    public static createReport({
        requestBody,
    }: {
        requestBody: AdminReportRequest,
    }): CancelablePromise<ReportId> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/report/configuration',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns ReportId OK
     * @throws ApiError
     */
    public static saveAsReport({
        reportUid,
        dataSourceUid,
        requestBody,
    }: {
        reportUid: number,
        dataSourceUid: number,
        requestBody: SaveAsReportRequest,
    }): CancelablePromise<ReportId> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/report/configuration/{reportUid}/{dataSourceUid}/save-as',
            path: {
                'reportUid': reportUid,
                'dataSourceUid': dataSourceUid,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns string OK
     * @throws ApiError
     */
    public static getReportRunner({
        reportUid,
        dataSourceUid,
    }: {
        reportUid: number,
        dataSourceUid: number,
    }): CancelablePromise<string> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/report/runner/{reportUid}/{dataSourceUid}',
            path: {
                'reportUid': reportUid,
                'dataSourceUid': dataSourceUid,
            },
        });
    }
}
