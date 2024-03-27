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
     * @returns Tab OK
     * @throws ApiError
     */
    public static updateTab({
        page,
        tabId,
        requestBody,
    }: {
        page: number,
        tabId: number,
        requestBody: UpdateTabRequest,
    }): CancelablePromise<Tab> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/tabs/{tabId}',
            path: {
                'page': page,
                'tabId': tabId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static deleteTab({
        page,
        tabId,
    }: {
        page: number,
        tabId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/pages/{page}/tabs/{tabId}',
            path: {
                'page': page,
                'tabId': tabId,
            },
        });
    }

    /**
     * @returns Tab OK
     * @throws ApiError
     */
    public static createTab({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: CreateTabRequest,
    }): CancelablePromise<Tab> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/tabs',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
