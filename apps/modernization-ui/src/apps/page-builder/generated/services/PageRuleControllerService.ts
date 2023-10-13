/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateRuleRequest } from '../models/CreateRuleRequest';
import type { CreateRuleResponse } from '../models/CreateRuleResponse';
import type { ViewRuleResponse } from '../models/ViewRuleResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
import {Page_Question_} from "../models/Page_Question_";

export class PageRuleControllerService {

    /**
     * createBusinessRule
     * @returns CreateRuleResponse Created
     * @throws ApiError
     */
    public static createBusinessRuleUsingPost({
        authorization,
        request,
    }: {
        authorization: any,
        /**
         * request
         */
        request: CreateRuleRequest,
    }): CancelablePromise<CreateRuleResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/rule',
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
     * findAllRuleResponse
     * @returns ViewRuleResponse OK
     * @throws ApiError
     */
    public static findAllRuleResponseUsingGet({
          authorization,
          page,
          size,
          sort,
      }: {
        authorization: any,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<ViewRuleResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/rules',
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
     * viewRuleResponse
     * @returns ViewRuleResponse OK
     * @throws ApiError
     */
    public static viewRuleResponseUsingGet({
        authorization,
        ruleId,
    }: {
        authorization: any,
        /**
         * ruleId
         */
        ruleId: number,
    }): CancelablePromise<ViewRuleResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/rule/{ruleId}',
            path: {
                'ruleId': ruleId,
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
     * updatePageRule
     * @returns CreateRuleResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static updatePageRuleUsingPut({
        authorization,
        request,
        ruleId,
    }: {
        authorization: any,
        /**
         * request
         */
        request: CreateRuleRequest,
        /**
         * ruleId
         */
        ruleId: number,
    }): CancelablePromise<CreateRuleResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/rule/{ruleId}',
            path: {
                'ruleId': ruleId,
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
     * deletePageRule
     * @returns CreateRuleResponse OK
     * @throws ApiError
     */
    public static deletePageRuleUsingDelete({
        authorization,
        ruleId,
    }: {
        authorization: any,
        /**
         * ruleId
         */
        ruleId: number,
    }): CancelablePromise<CreateRuleResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/rule/{ruleId}',
            path: {
                'ruleId': ruleId,
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
