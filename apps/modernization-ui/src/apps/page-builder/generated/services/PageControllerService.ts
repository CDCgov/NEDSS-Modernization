/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Pageable } from '../models/Pageable';
import type { PageCreateRequest } from '../models/PageCreateRequest';
import type { PageCreateResponse } from '../models/PageCreateResponse';
import type { PageDeleteResponse } from '../models/PageDeleteResponse';
import type { PagePageHistory } from '../models/PagePageHistory';
import type { PageStateResponse } from '../models/PageStateResponse';
import type { PageValidationRequest } from '../models/PageValidationRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageControllerService {

    /**
     * @returns PageStateResponse OK
     * @throws ApiError
     */
    public static savePageDraft({
        id,
    }: {
        id: number,
    }): CancelablePromise<PageStateResponse> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{id}/draft',
            path: {
                'id': id,
            },
        });
    }

    /**
     * @returns PageCreateResponse OK
     * @throws ApiError
     */
    public static createPage({
        requestBody,
    }: {
        requestBody: PageCreateRequest,
    }): CancelablePromise<PageCreateResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns boolean OK
     * @throws ApiError
     */
    public static validatePageRequest({
        requestBody,
    }: {
        requestBody: PageValidationRequest,
    }): CancelablePromise<boolean> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/validate',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns PagePageHistory OK
     * @throws ApiError
     */
    public static getPageHistory({
        id,
        pageable,
    }: {
        id: number,
        pageable: Pageable,
    }): CancelablePromise<PagePageHistory> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{id}/page-history',
            path: {
                'id': id,
            },
            query: {
                'pageable': pageable,
            },
        });
    }

    /**
     * @returns PageDeleteResponse OK
     * @throws ApiError
     */
    public static deletePageDraft({
        id,
    }: {
        id: number,
    }): CancelablePromise<PageDeleteResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/pages/{id}/delete-draft',
            path: {
                'id': id,
            },
        });
    }

}
