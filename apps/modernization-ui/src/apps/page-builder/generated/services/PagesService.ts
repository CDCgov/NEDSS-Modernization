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
     * Pages
     * Provides the details of a Page including the components and the rules
     * @returns PagesResponse OK
     * @throws ApiError
     */
    public static detailsUsingGet({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<PagesResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/pages/{id}',
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
     * Creates a Template from the Page that can be used as a starting point for new Pages.
     * @returns any OK
     * @throws ApiError
     */
    public static createTemplate({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: CreateTemplateRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/template',
            path: {
                'page': page,
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

}
