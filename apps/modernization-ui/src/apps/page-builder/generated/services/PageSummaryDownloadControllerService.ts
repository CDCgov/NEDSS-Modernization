/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Pageable } from '../models/Pageable';
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
    }: {
        requestBody: {
            request?: PageSummaryRequest;
            pageable?: Pageable;
        },
    }): CancelablePromise<Array<string>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/pdf',
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
    }: {
        requestBody: {
            request?: PageSummaryRequest;
            pageable?: Pageable;
        },
    }): CancelablePromise<Blob> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/csv',
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
