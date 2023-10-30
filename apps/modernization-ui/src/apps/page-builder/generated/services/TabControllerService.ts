/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateTabRequest } from '../models/CreateTabRequest';
import type { Tab } from '../models/Tab';
import type { UpdateTabRequest } from '../models/UpdateTabRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TabControllerService {

    /**
     * createTab
     * @returns Tab OK
     * @returns any Created
     * @throws ApiError
     */
    public static createTabUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: any,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: CreateTabRequest,
    }): CancelablePromise<Tab | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/tabs',
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

    /**
     * updateTab
     * @returns Tab OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateTabUsingPut({
        authorization,
        page,
        request,
        tabId,
    }: {
        authorization: any,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: UpdateTabRequest,
        /**
         * tabId
         */
        tabId: number,
    }): CancelablePromise<Tab | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{page}/tabs/{tabId}',
            path: {
                'page': page,
                'tabId': tabId,
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

    /**
     * deleteTab
     * @returns any OK
     * @throws ApiError
     */
    public static deleteTabUsingDelete({
        authorization,
        page,
        tabId,
    }: {
        authorization: any,
        /**
         * page
         */
        page: number,
        /**
         * tabId
         */
        tabId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/pages/{page}/tabs/{tabId}',
            path: {
                'page': page,
                'tabId': tabId,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }

}
