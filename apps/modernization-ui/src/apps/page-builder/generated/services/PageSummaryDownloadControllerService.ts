/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageSummaryRequest } from '../models/PageSummaryRequest';
import type { Resource } from '../models/Resource';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageSummaryDownloadControllerService {

    /**
     * csv
     * @returns Resource OK
     * @returns any Created
     * @throws ApiError
     */
    public static csvUsingPost({
        authorization,
        request,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * request
         */
        request: PageSummaryRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Resource | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/csv',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * pdf
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static pdfUsingPost({
        authorization,
        request,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * request
         */
        request: PageSummaryRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/pdf',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * downloadPageMetadata
     * @returns Resource OK
     * @throws ApiError
     */
    public static downloadPageMetadataUsingGet({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<Resource> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{id}/metadata',
            path: {
                'id': id,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
