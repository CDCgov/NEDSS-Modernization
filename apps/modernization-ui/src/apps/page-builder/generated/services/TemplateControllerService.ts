/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Page_Template_ } from '../models/Page_Template_';
import type { TemplateSearchRequest } from '../models/TemplateSearchRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TemplateControllerService {

    /**
     * findAllTemplates
     * @returns Page_Template_ OK
     * @throws ApiError
     */
    public static findAllTemplatesUsingGet({
        authorization,
        page,
        size,
        sort,
    }: {
        authorization: any,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Template_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/template/',
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
     * searchTemplate
     * @returns Page_Template_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static searchTemplateUsingPost({
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
        search: TemplateSearchRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Template_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/template/search',
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
