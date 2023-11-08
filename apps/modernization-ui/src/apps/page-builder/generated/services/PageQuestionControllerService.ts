/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddQuestionRequest } from '../models/AddQuestionRequest';
import type { AddQuestionResponse } from '../models/AddQuestionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageQuestionControllerService {

    /**
     * addQuestionToPage
     * @returns AddQuestionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static addQuestionToPageUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: AddQuestionRequest,
    }): CancelablePromise<AddQuestionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/questions/',
            path: {
                'page': page,
            },
            headers: {
                'Authorization': authorization,
            },
            body: request,
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
