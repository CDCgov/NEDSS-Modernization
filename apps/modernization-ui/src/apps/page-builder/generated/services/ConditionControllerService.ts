/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Condition } from '../models/Condition';
import type { ConditionStatusResponse } from '../models/ConditionStatusResponse';
import type { CreateConditionRequest } from '../models/CreateConditionRequest';
import type { PageCondition } from '../models/PageCondition';
import type { ReadConditionRequest } from '../models/ReadConditionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConditionControllerService {

    /**
     * @returns PageCondition OK
     * @throws ApiError
     */
    public static searchConditions({
        requestBody,
        page,
        size = 20,
        sort,
    }: {
        requestBody: ReadConditionRequest,
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
    }): CancelablePromise<PageCondition> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/conditions/search',
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
     * @returns PageCondition OK
     * @throws ApiError
     */
    public static findConditions({
        page,
        size = 20,
        sort,
    }: {
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
    }): CancelablePromise<PageCondition> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/conditions/',
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
        });
    }

    /**
     * @returns Condition OK
     * @throws ApiError
     */
    public static createCondition({
        requestBody,
    }: {
        requestBody: CreateConditionRequest,
    }): CancelablePromise<Condition> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/conditions/',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns ConditionStatusResponse OK
     * @throws ApiError
     */
    public static inactivateCondition({
        id,
    }: {
        id: string,
    }): CancelablePromise<ConditionStatusResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/conditions/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @returns ConditionStatusResponse OK
     * @throws ApiError
     */
    public static activateCondition({
        id,
    }: {
        id: string,
    }): CancelablePromise<ConditionStatusResponse> {
        return __request(OpenAPI, {
            method: 'PATCH',
            url: '/api/v1/conditions/{id}',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @returns Condition OK
     * @throws ApiError
     */
    public static findConditionsNotInUse({
        page,
    }: {
        page?: number,
    }): CancelablePromise<Array<Condition>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/conditions/available',
            query: {
                'page': page,
            },
        });
    }

    /**
     * @returns Condition OK
     * @throws ApiError
     */
    public static findAllConditions(): CancelablePromise<Array<Condition>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/conditions/all',
        });
    }

}
