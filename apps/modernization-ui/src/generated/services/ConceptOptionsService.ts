/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ConceptOptionsResponse } from '../models/ConceptOptionsResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConceptOptionsService {

    /**
     * Concept Options by Value Set
     * Provides options from Concepts grouped into a value set.
     * @returns ConceptOptionsResponse OK
     * @throws ApiError
     */
    public static allUsingGet({
        authorization,
        name,
    }: {
        authorization: string,
        /**
         * name
         */
        name: string,
    }): CancelablePromise<ConceptOptionsResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/concepts/{name}',
            path: {
                'name': name,
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
     * Concept Option Autocomplete
     * Provides options from Concepts grouped into a value set that have a name matching a criteria.
     * @returns ConceptOptionsResponse OK
     * @throws ApiError
     */
    public static specificUsingGet({
        authorization,
        criteria,
        name,
        limit = 15,
    }: {
        authorization: string,
        /**
         * criteria
         */
        criteria: string,
        /**
         * name
         */
        name: string,
        /**
         * limit
         */
        limit?: number,
    }): CancelablePromise<ConceptOptionsResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/concepts/{name}/search',
            path: {
                'name': name,
            },
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
