/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ConditionOptionsService {

    /**
     * Condition Option
     * Provides options from Conditions that have a name matching a criteria.
     * @returns Option OK
     * @throws ApiError
     */
    public static all(): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/conditions',
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * Condition Option Autocomplete
     * Provides options from Conditions that have a name matching a criteria.
     * @returns Option OK
     * @throws ApiError
     */
    public static complete({
        criteria,
        limit = 15,
    }: {
        /**
         * criteria
         */
        criteria: string,
        /**
         * limit
         */
        limit?: number,
    }): CancelablePromise<Array<Option>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/conditions/search',
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
