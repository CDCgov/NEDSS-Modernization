/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Page_Person_ } from '../models/Page_Person_';
import type { Person } from '../models/Person';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PersonControllerService {

    /**
     * getPeople
     * @returns Page_Person_ OK
     * @throws ApiError
     */
    public static getPeopleUsingGet({
        page,
        size,
        sort,
    }: {
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Person_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/person',
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
     * createPerson
     * @returns number OK
     * @returns any Created
     * @throws ApiError
     */
    public static createPersonUsingPost({
        person,
    }: {
        /**
         * person
         */
        person: Person,
    }): CancelablePromise<number | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/person',
            body: person,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
