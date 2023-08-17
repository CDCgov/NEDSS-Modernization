/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateConditionRequest } from '../models/CreateConditionRequest';
import type { CreateConditionResponse } from '../models/CreateConditionResponse';
import type { SearchConditionRequest } from '../models/SearchConditionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConditionControllerService {
    /**
     * createCondition
     * @returns CreateConditionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createConditionUsingPost({
        authorization,
        request
    }: {
        authorization: any;
        /**
         * request
         */
        request: CreateConditionRequest;
    }): CancelablePromise<CreateConditionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/conditions/',
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

    /**
     * searchConditions
     * @returns CreateConditionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchConditionUsingPost({
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
        request: SearchConditionRequest;
        page?: number;
        size?: number;
        sort?: string;
    }): CancelablePromise<CreateConditionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/conditions/search',
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
