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
    public static pageNames(): CancelablePromise<Array<PageBuilderOption>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/options/page/names',
        });
    }

    /**
     * Page name options autocomplete
     * Provides Page name options from all Pages available that have a name starting with a term.
     * @returns PageBuilderOption OK
     * @throws ApiError
     */
    public static pageNamesAutocomplete({
        criteria,
        limit = 15,
    }: {
        criteria: string,
        limit?: number,
    }): CancelablePromise<Array<PageBuilderOption>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/options/page/names/search',
            query: {
                'criteria': criteria,
                'limit': limit,
            },
        });
    }

}
