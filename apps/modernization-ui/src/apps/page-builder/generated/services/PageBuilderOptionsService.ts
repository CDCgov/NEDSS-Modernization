/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageBuilderOption } from '../models/PageBuilderOption';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageBuilderOptionsService {

    /**
     * All Page name options
     * Provides Page name options from all Pages available.
     * @returns PageBuilderOption OK
     * @throws ApiError
     */
    public static pageNames({
        authorization,
    }: {
        authorization: string,
    }): CancelablePromise<Array<PageBuilderOption>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/options/page/names',
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
     * Page name options autocomplete
     * Provides Page name options from all Pages available that have a name starting with a term.
     * @returns PageBuilderOption OK
     * @throws ApiError
     */
    public static pageNamesAutocomplete({
        authorization,
        criteria,
        limit = 15,
    }: {
        authorization: string,
        /**
         * criteria
         */
        criteria: string,
        /**
         * limit
         */
        limit?: number,
    }): CancelablePromise<Array<PageBuilderOption>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/options/page/names/search',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'criteria': criteria,
                'limit': limit,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
