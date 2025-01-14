/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class RaceOptionsService {
    /**
     * Race Option
     * Provides all Race options.
     * @returns Option OK
     * @throws ApiError
     */
    public static races(): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/races',
        });
    }
    /**
     * Detailed Race Option
     * Provides all Detailed Race options for the given category.
     * @returns Option OK
     * @throws ApiError
     */
    public static detailedRaces({
        category,
    }: {
        category: string,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/races/{category}',
            path: {
                'category': category,
            },
        });
    }
    /**
     * Detailed Race Option Autocomplete
     * Provides options from Detailed Race options for the given category that have a name matching a criteria.
     * @returns Option OK
     * @throws ApiError
     */
    public static detailsComplete({
        criteria,
        category,
        limit = 15,
    }: {
        criteria: string,
        category: string,
        limit?: number,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/races/{category}/search',
            path: {
                'category': category,
            },
            query: {
                'criteria': criteria,
                'limit': limit,
            },
        });
    }
    /**
     * Race Option Autocomplete
     * Provides options from Races that have a name matching a criteria.
     * @returns Option OK
     * @throws ApiError
     */
    public static racesComplete({
        criteria,
        limit = 15,
    }: {
        criteria: string,
        limit?: number,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/races/search',
            query: {
                'criteria': criteria,
                'limit': limit,
            },
        });
    }
}
