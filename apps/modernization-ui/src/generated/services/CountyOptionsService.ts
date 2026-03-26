/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class CountyOptionsService {
    /**
     * Counties Options by state
     * Provides all Counties options for a specific state.
     * @returns Option OK
     * @throws ApiError
     */
    public static counties({
        state,
    }: {
        state: string,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/counties/{state}',
            path: {
                'state': state,
            },
        });
    }
    /**
     * NBS County Option Autocomplete
     * Provides options from Counties that have a name matching a criteria.
     * @returns Option OK
     * @throws ApiError
     */
    public static countyAutocomplete({
        criteria,
        state,
        limit = 15,
    }: {
        criteria: string,
        state: string,
        limit?: number,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/counties/{state}/search',
            path: {
                'state': state,
            },
            query: {
                'criteria': criteria,
                'limit': limit,
            },
        });
    }
}
