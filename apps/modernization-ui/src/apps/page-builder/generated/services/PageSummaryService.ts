/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
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
        page,
        size = 25,
        sort,
    }: {
        requestBody: PageSummaryRequest,
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
    }): CancelablePromise<PagePageSummary> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/search',
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
