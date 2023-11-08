/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ReorderControllerService {

    /**
     * orderComponentAfter
     * @returns any OK
     * @throws ApiError
     */
    public static orderComponentAfterUsingPut({
        after,
        authorization,
        component,
        page,
    }: {
        /**
         * after
         */
        after: number,
        authorization: string,
        /**
         * component
         */
        component: number,
        /**
         * page
         */
        page: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{page}/components/{component}/after/{after}',
            path: {
                'after': after,
                'component': component,
                'page': page,
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

}
