/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSubSectionRequest } from '../models/CreateSubSectionRequest';
import type { CreateSubSectionResponse } from '../models/CreateSubSectionResponse';
import type { DeleteSubSectionResponse } from '../models/DeleteSubSectionResponse';
import type { UpdateSubSectionRequest } from '../models/UpdateSubSectionRequest';
import type { UpdateSubSectionResponse } from '../models/UpdateSubSectionResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class SubSectionControllerService {

    /**
     * createSubSection
     * @returns CreateSubSectionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static createSubSectionUsingPost({
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
        request: CreateSubSectionRequest,
    }): CancelablePromise<CreateSubSectionResponse | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/page-builder/api/v1/pages/{page}/subsections/',
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
     * updateSubSection
     * @returns UpdateSubSectionResponse OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateSubSectionUsingPut({
        authorization,
        request,
        subSectionId,
    }: {
        authorization: any,
        /**
         * request
         */
        request: UpdateSubSectionRequest,
        /**
         * subSectionId
         */
        subSectionId: number,
    }): CancelablePromise<UpdateSubSectionResponse | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/page-builder/api/v1/pages/{page}/subsections/{subSectionId}',
            path: {
                'subSectionId': subSectionId,
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
     * deleteSubSection
     * @returns DeleteSubSectionResponse OK
     * @throws ApiError
     */
    public static deleteSubSectionUsingDelete({
        authorization,
        page,
        subSectionId,
    }: {
        authorization: any,
        /**
         * page
         */
        page: number,
        /**
         * subSectionId
         */
        subSectionId: number,
    }): CancelablePromise<DeleteSubSectionResponse> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/page-builder/api/v1/pages/{page}/subsections/{subSectionId}',
            path: {
                'page': page,
                'subSectionId': subSectionId,
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
