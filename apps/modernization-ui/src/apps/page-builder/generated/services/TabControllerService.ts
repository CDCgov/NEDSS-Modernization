/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateTabRequest } from '../models/CreateTabRequest';
import type { CreateTabResponse } from '../models/CreateTabResponse';

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
        request
    }: {
        authorization: any;
        /**
         * page
         */
        page: number;
        /**
         * request
         */
        request: CreateTabRequest;
    }): CancelablePromise<CreateTabResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/pages/{page}/tabs/',
            path: {
                page: page
            },
            headers: {
                Authorization: authorization
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
