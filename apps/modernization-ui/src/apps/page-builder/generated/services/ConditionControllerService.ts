/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Condition } from '../models/Condition';
import type { CreateConditionRequest } from '../models/CreateConditionRequest';
<<<<<<< HEAD
=======
import type { CreateConditionResponse } from '../models/CreateConditionResponse';
>>>>>>> main
import type { Page_Condition_ } from '../models/Page_Condition_';
import type { ReadConditionRequest } from '../models/ReadConditionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConditionControllerService {

    /**
     * findConditions
     * @returns Page_Condition_ OK
     * @throws ApiError
     */
    public static findConditionsUsingGet({
        authorization,
        page,
        size,
        sort,
    }: {
        authorization: any,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Condition_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/page-builder/api/v1/conditions/',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * createCondition
     * @returns Condition OK
     * @returns any Created
     * @throws ApiError
     */
    public static createConditionUsingPost({
        authorization,
        request,
    }: {
        authorization: any,
        /**
         * request
         */
        request: CreateConditionRequest,
    }): CancelablePromise<Condition | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/conditions/',
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
     * findAllConditions
     * @returns Condition OK
     * @throws ApiError
     */
    public static findAllConditionsUsingGet({
        authorization,
    }: {
        authorization: any,
    }): CancelablePromise<Array<Condition>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/page-builder/api/v1/conditions/all',
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
     * searchConditions
     * @returns Page_Condition_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchConditionsUsingPost({
        authorization,
        search,
        page,
        size,
        sort,
    }: {
        authorization: any,
        /**
         * search
         */
        search: ReadConditionRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Condition_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/conditions/search',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: search,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
