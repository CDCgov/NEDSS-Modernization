/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateTabRequest } from '../models/CreateTabRequest';
import type { CreateTabResponse } from '../models/CreateTabResponse';
import type { DeleteTabResponse } from '../models/DeleteTabResponse';
import type { UpdateTabRequest } from '../models/UpdateTabRequest';
import type { UpdateTabResponse } from '../models/UpdateTabResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TabControllerService {

    /**
     * createTab
     * @returns CreateTabResponse OK
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
    }): CancelablePromise<CreateTabResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/pages/{page}/tabs/',
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
     * @returns UpdateTabResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateTabUsingPut({
        authorization,
        request,
        tabId,
    }: {
        authorization: any,
        /**
         * request
         */
        request: UpdateTabRequest,
        /**
         * tabId
         */
        tabId: number,
    }): CancelablePromise<UpdateTabResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/page-builder/api/v1/pages/{page}/tabs/{tabId}',
            path: {
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
     * @returns DeleteTabResponse OK
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
    }): CancelablePromise<DeleteTabResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/page-builder/api/v1/pages/{page}/tabs/{tabId}',
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
