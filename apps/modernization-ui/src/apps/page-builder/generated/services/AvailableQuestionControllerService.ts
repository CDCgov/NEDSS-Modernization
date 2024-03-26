/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AvailableQuestionCriteria } from '../models/AvailableQuestionCriteria';
import type { Pageable } from '../models/Pageable';
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
    }: {
        pageId: number,
        requestBody: {
            request?: AvailableQuestionCriteria;
            pageable?: Pageable;
        },
    }): CancelablePromise<PageAvailableQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/page/{pageId}/search',
            path: {
                'pageId': pageId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
