/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AvailableQuestionCriteria } from '../models/AvailableQuestionCriteria';
import type { Page_AvailableQuestion_ } from '../models/Page_AvailableQuestion_';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class AvailableQuestionControllerService {

    /**
     * findAvailableQuestions
     * @returns Page_AvailableQuestion_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static findAvailableQuestionsUsingPost({
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
        request: AvailableQuestionCriteria,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_AvailableQuestion_ | any> {
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
