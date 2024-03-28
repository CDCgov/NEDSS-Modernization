/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AvailableQuestionCriteria } from '../models/AvailableQuestionCriteria';
import type { PageAvailableQuestion } from '../models/PageAvailableQuestion';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class AvailableQuestionControllerService {
    /**
     * @returns PageAvailableQuestion OK
     * @throws ApiError
     */
    public static findAvailableQuestions({
        pageId,
        requestBody,
        page,
        size = 25,
        sort,
    }: {
        pageId: number,
        requestBody: AvailableQuestionCriteria,
        /**
         * Zero-based page index (0..N)
         */
        page?: number,
        /**
         * The size of the page to be returned
         */
        size?: number,
        /**
         * Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.
         */
        sort?: Array<string>,
    }): CancelablePromise<PageAvailableQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/page/{pageId}/search',
            path: {
                'pageId': pageId,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
