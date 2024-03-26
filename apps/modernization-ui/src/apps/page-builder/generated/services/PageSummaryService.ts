/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Pageable } from '../models/Pageable';
import type { PagePageSummary } from '../models/PagePageSummary';
import type { PageSummaryRequest } from '../models/PageSummaryRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageSummaryService {

    /**
     * Allows paginated searching of Page Summaries with filters.
     * @returns PagePageSummary OK
     * @throws ApiError
     */
    public static search({
        requestBody,
    }: {
        requestBody: {
            request?: PageSummaryRequest;
            pageable?: Pageable;
        },
    }): CancelablePromise<PagePageSummary> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/search',
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}
