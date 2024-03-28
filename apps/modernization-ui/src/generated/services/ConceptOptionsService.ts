/* generated using openapi-typescript-codegen -- do not edit */
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
    public static concepts({
        name,
    }: {
        name: string,
    }): CancelablePromise<ConceptOptionsResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/concepts/{name}',
            path: {
                'name': name,
            },
        });
    }
    /**
     * Concept Option Autocomplete
     * Provides options from Concepts grouped into a value set that have a name matching a criteria.
     * @returns ConceptOptionsResponse OK
     * @throws ApiError
     */
    public static conceptSearch({
        name,
        criteria,
        limit = 15,
    }: {
        name: string,
        criteria: string,
        limit?: number,
    }): CancelablePromise<ConceptOptionsResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/concepts/{name}/search',
            path: {
                'name': name,
            },
            query: {
                'criteria': criteria,
                'limit': limit,
            },
        });
    }
}
