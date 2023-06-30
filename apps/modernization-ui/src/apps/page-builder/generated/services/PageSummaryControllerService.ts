/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Page_PageSummary_ } from '../models/Page_PageSummary_';
import type { PageSummaryRequest } from '../models/PageSummaryRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageSummaryControllerService {
    /**
     * getAllPageSummary
     * @returns Page_PageSummary_ OK
     * @throws ApiError
     */
    public static getAllPageSummaryUsingGet({
        authorization,
        page,
        size,
        sort
    }: {
        authorization: any;
        page?: number;
        size?: number;
        sort?: string;
    }): CancelablePromise<Page_PageSummary_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/pageBuilder/api/v1/pages',
            headers: {
                Authorization: authorization
            },
            query: {
                page: page,
                size: size,
                sort: sort
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`
            }
        });
    }

    /**
     * search
     * @returns Page_PageSummary_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchUsingPost({
        authorization,
        request,
        page,
        size,
        sort
    }: {
        authorization: any;
        /**
         * request
         */
        request: PageSummaryRequest;
        page?: number;
        size?: number;
        sort?: string;
    }): CancelablePromise<Page_PageSummary_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/pageBuilder/api/v1/pages',
            headers: {
                Authorization: authorization
            },
            query: {
                page: page,
                size: size,
                sort: sort
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`
            }
        });
    }
}
