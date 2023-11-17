/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageInformation } from '../models/PageInformation';
import type { PageInformationChangeRequest } from '../models/PageInformationChangeRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageInformationService {

    /**
     * Returns the Page Information of a page
     * The Page Information includes the event type, message mapping guide, name, datamart, description, and any related conditions
     * @returns PageInformation OK
     * @throws ApiError
     */
    public static find({
        authorization,
        page,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
    }): CancelablePromise<PageInformation> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{page}/information',
            path: {
                'page': page,
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
    /**
     * Returns the Page Information of a page
     * The Page Information includes the event type, message mapping guide, name, datamart, description, and any related conditions
     * @returns PageInformation OK
     * @throws ApiError
     */
    public static getPageHistory({
                           authorization,
                           page,
                       }: {
        authorization: string,
        /**
         * page
         */
        page: number,
    }): CancelablePromise<PageInformation> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{page}/page-history',
            path: {
                'page': page,
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

    /**
     * Allows changing the Information of a page
     * Allows changing message mapping guide, name, datamart, description, and related conditions of a Page.
     * @returns any OK
     * @throws ApiError
     */
    public static change({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: PageInformationChangeRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{page}/information',
            path: {
                'page': page,
            },
            headers: {
                'Authorization': authorization,
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
