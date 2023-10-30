/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSectionRequest } from '../models/CreateSectionRequest';
import type { CreateSectionResponse } from '../models/CreateSectionResponse';
import type { DeleteSectionResponse } from '../models/DeleteSectionResponse';
import type { UpdateSectionRequest } from '../models/UpdateSectionRequest';
import type { UpdateSectionResponse } from '../models/UpdateSectionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class SectionControllerService {

    /**
     * createSection
     * @returns CreateSectionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createSectionUsingPost({
        authorization,
        page,
        request,
    }: {
        authorization: any,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: CreateSectionRequest,
    }): CancelablePromise<CreateSectionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/sections/',
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

    /**
     * updateSection
     * @returns UpdateSectionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateSectionUsingPut({
        authorization,
        request,
        sectionId,
    }: {
        authorization: any,
        /**
         * request
         */
        request: UpdateSectionRequest,
        /**
         * sectionId
         */
        sectionId: number,
    }): CancelablePromise<UpdateSectionResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{page}/sections/{sectionId}',
            path: {
                'sectionId': sectionId,
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

    /**
     * deleteSection
     * @returns DeleteSectionResponse OK
     * @throws ApiError
     */
    public static deleteSectionUsingDelete({
        authorization,
        page,
        sectionId,
    }: {
        authorization: any,
        /**
         * page
         */
        page: number,
        /**
         * sectionId
         */
        sectionId: number,
    }): CancelablePromise<DeleteSectionResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/pages/{page}/sections/{sectionId}',
            path: {
                'page': page,
                'sectionId': sectionId,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
            },
        });
    }

}
