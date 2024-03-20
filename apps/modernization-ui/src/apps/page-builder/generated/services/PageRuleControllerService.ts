/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Page_Rule_ } from '../models/Page_Rule_';
import type { PagesResponse } from '../models/PagesResponse';
import type { Rule } from '../models/Rule';
import type { RuleRequest } from '../models/RuleRequest';
import type { SearchPageRuleRequest } from '../models/SearchPageRuleRequest';
import type { SourceQuestionRequest } from '../models/SourceQuestionRequest';
import type { TargetQuestionRequest } from '../models/TargetQuestionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageRuleControllerService {

    /**
     * createBusinessRule
     * @returns Rule Created
     * @throws ApiError
     */
    public static createBusinessRuleUsingPost({
        authorization,
        id,
        request,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: RuleRequest,
    }): CancelablePromise<Rule> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules',
            path: {
                'id': id,
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
     * downloadRuleCsv
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static downloadRuleCsvUsingPost({
        authorization,
        id,
        request,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: SearchPageRuleRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/csv',
            path: {
                'id': id,
            },
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
     * getAllRules
     * @returns Rule OK
     * @throws ApiError
     */
    public static getAllRulesUsingGet({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<Array<Rule>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/getAll',
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

    /**
     * downloadRulePdf
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static downloadRulePdfUsingPost({
        authorization,
        id,
        request,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: SearchPageRuleRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/pdf',
            path: {
                'id': id,
            },
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
     * findPageRule
     * @returns Page_Rule_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static findPageRuleUsingPost({
        authorization,
        id,
        request,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: SearchPageRuleRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Rule_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/search',
            path: {
                'id': id,
            },
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
     * getSourceQuestions
     * @returns PagesResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static getSourceQuestionsUsingPost({
        authorization,
        id,
        request,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: SourceQuestionRequest,
    }): CancelablePromise<PagesResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/source/questions',
            path: {
                'id': id,
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
     * getTargetQuestions
     * @returns PagesResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static getTargetQuestionsUsingPost({
        authorization,
        id,
        request,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: TargetQuestionRequest,
    }): CancelablePromise<PagesResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/target/questions',
            path: {
                'id': id,
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
     * viewRuleResponse
     * @returns Rule OK
     * @throws ApiError
     */
    public static viewRuleResponseUsingGet({
        authorization,
        ruleId,
    }: {
        authorization: string,
        /**
         * ruleId
         */
        ruleId: number,
    }): CancelablePromise<Rule> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/{ruleId}',
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
     * @returns Rule OK
     * @returns any Created
     * @throws ApiError
     */
    public static updatePageRuleUsingPut({
        authorization,
        request,
        ruleId,
    }: {
        authorization: string,
        /**
         * request
         */
        request: RuleRequest,
        /**
         * ruleId
         */
        ruleId: number,
    }): CancelablePromise<Rule | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/{ruleId}',
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
     * @returns any OK
     * @throws ApiError
     */
    public static deletePageRuleUsingDelete({
        authorization,
        id,
        ruleId,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        /**
         * ruleId
         */
        ruleId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/pages/{id}/rules/{ruleId}',
            path: {
                'id': id,
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
