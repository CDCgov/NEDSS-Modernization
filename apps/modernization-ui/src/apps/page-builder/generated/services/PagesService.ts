/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateTemplateRequest } from '../models/CreateTemplateRequest';
import type { PagesResponse } from '../models/PagesResponse';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PagesService {
    /**
     * Creates a Template from the Page that can be used as a starting point for new Pages.
     * @returns any OK
     * @throws ApiError
     */
    public static createTemplate({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: CreateTemplateRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/template',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Pages
     * Provides the details of a Page including the components and the rules
     * @returns PagesResponse OK
     * @throws ApiError
     */
    public static details({
        id,
    }: {
        id: number,
    }): CancelablePromise<PagesResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{id}',
            path: {
                'id': id,
            },
        });
    }
}
