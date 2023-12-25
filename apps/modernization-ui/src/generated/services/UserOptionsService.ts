/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class UserOptionsService {

    /**
     * NBS User Option Autocomplete
     * Provides options from Users that have a name matching a criteria.
     * @returns Option OK
     * @throws ApiError
     */
    public static userAutocomplete({
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
            url: '/nbs/api/options/users/search',
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
