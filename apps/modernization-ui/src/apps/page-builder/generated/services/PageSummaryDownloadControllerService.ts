/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageSummaryRequest } from '../models/PageSummaryRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PageSummaryDownloadControllerService {
    /**
     * @returns string OK
     * @throws ApiError
     */
    public static pdf({
        requestBody,
        page,
        size = 10,
        sort,
    }: {
        requestBody: PageSummaryRequest,
        /**
         * Zero-based page index (0..N)
         */
        page?: number,
        /**
         * The size of the page to be returned
         */
        size?: number,
        /**
         * Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
         */
        sort?: Array<string>,
    }): CancelablePromise<Array<string>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/pdf',
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns binary OK
     * @throws ApiError
     */
    public static csv({
        requestBody,
        page,
        size = 10,
        sort,
    }: {
        requestBody: PageSummaryRequest,
        /**
         * Zero-based page index (0..N)
         */
        page?: number,
        /**
         * The size of the page to be returned
         */
        size?: number,
        /**
         * Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
         */
        sort?: Array<string>,
    }): CancelablePromise<Blob> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/csv',
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns binary OK
     * @throws ApiError
     */
    public static downloadPageMetadata({
        id,
    }: {
        id: number,
    }): CancelablePromise<Blob> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{id}/metadata',
            path: {
                'id': id,
            },
        });
    }
}
