/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class ReorderControllerService {

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static orderComponentAfter({
        page,
        component,
        after,
    }: {
        page: number,
        component: number,
        after: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/components/{component}/after/{after}',
            path: {
                'page': page,
                'component': component,
                'after': after,
            },
        });
    }

}
