/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Pageable } from '../models/Pageable';
import type { PageRule } from '../models/PageRule';
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
     * @returns Rule OK
     * @throws ApiError
     */
    public static viewRuleResponse({
        ruleId,
    }: {
        ruleId: number,
    }): CancelablePromise<Rule> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{id}/rules/{ruleId}',
            path: {
                'ruleId': ruleId,
            },
        });
    }

    /**
     * @returns Rule OK
     * @throws ApiError
     */
    public static updatePageRule({
        ruleId,
        requestBody,
    }: {
        ruleId: number,
        requestBody: RuleRequest,
    }): CancelablePromise<Rule> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{id}/rules/{ruleId}',
            path: {
                'ruleId': ruleId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static deletePageRule({
        id,
        ruleId,
    }: {
        id: number,
        ruleId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/pages/{id}/rules/{ruleId}',
            path: {
                'id': id,
                'ruleId': ruleId,
            },
        });
    }

    /**
     * @returns Rule Created
     * @throws ApiError
     */
    public static createBusinessRule({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: RuleRequest,
    }): CancelablePromise<Rule> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{id}/rules',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns PagesResponse OK
     * @throws ApiError
     */
    public static getTargetQuestions({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: TargetQuestionRequest,
    }): CancelablePromise<PagesResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{id}/rules/target/questions',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns PagesResponse OK
     * @throws ApiError
     */
    public static getSourceQuestions({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: SourceQuestionRequest,
    }): CancelablePromise<PagesResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{id}/rules/source/questions',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns PageRule OK
     * @throws ApiError
     */
    public static findPageRule({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: {
            request?: SearchPageRuleRequest;
            pageable?: Pageable;
        },
    }): CancelablePromise<PageRule> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{id}/rules/search',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns string OK
     * @throws ApiError
     */
    public static downloadRulePdf({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: {
            request?: SearchPageRuleRequest;
            pageable?: Pageable;
        },
    }): CancelablePromise<Array<string>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{id}/rules/pdf',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns string OK
     * @throws ApiError
     */
    public static downloadRuleCsv({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: {
            request?: SearchPageRuleRequest;
            pageable?: Pageable;
        },
    }): CancelablePromise<Array<string>> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{id}/rules/csv',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns Rule OK
     * @throws ApiError
     */
    public static getAllRules({
        id,
    }: {
        id: number,
    }): CancelablePromise<Array<Rule>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{id}/rules/getAll',
            path: {
                'id': id,
            },
        });
    }

}
