/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddableQuestionCriteria } from '../models/AddableQuestionCriteria';
import type { Page_AddableQuestion_ } from '../models/Page_AddableQuestion_';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AddableQuestionControllerService {

    /**
     * findAddableQuestions
     * @returns Page_AddableQuestion_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static findAddableQuestionsUsingPost({
        authorization,
        pageId,
        request,
        page,
        size,
        sort,
    }: {
        authorization: string,
        /**
         * pageId
         */
        pageId: number,
        /**
         * request
         */
        request: AddableQuestionCriteria,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_AddableQuestion_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/questions/page/{pageId}/search',
            path: {
                'pageId': pageId,
            },
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
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
