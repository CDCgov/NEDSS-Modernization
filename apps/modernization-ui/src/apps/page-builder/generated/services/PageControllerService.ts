/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Page_PageHistory_ } from '../models/Page_PageHistory_';
import type { PageCreateRequest } from '../models/PageCreateRequest';
import type { PageCreateResponse } from '../models/PageCreateResponse';
import type { PageDeleteResponse } from '../models/PageDeleteResponse';
import type { PageStateResponse } from '../models/PageStateResponse';
import type { PageValidationRequest } from '../models/PageValidationRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageControllerService {

    /**
     * createPage
     * @returns PageCreateResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createPageUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: PageCreateRequest,
    }): CancelablePromise<PageCreateResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages',
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
     * validatePageRequest
     * @returns boolean OK
     * @returns any Created
     * @throws ApiError
     */
    public static validatePageRequestUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: PageValidationRequest,
    }): CancelablePromise<boolean | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/validate',
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
     * deletePageDraft
     * @returns PageDeleteResponse OK
     * @throws ApiError
     */
    public static deletePageDraftUsingDelete({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<PageDeleteResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/pages/{id}/delete-draft',
            path: {
                'id': id,
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

    /**
     * savePageDraft
     * @returns PageStateResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static savePageDraftUsingPut({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<PageStateResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{id}/draft',
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
     * getPageHistory
     * @returns Page_PageHistory_ OK
     * @throws ApiError
     */
    public static getPageHistoryUsingGet({
        authorization,
        id,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_PageHistory_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{id}/page-history',
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
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
