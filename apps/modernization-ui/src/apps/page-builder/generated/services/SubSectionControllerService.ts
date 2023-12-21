/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSubSectionRequest } from '../models/CreateSubSectionRequest';
import type { GroupSubSectionRequest } from '../models/GroupSubSectionRequest';
import type { SubSection } from '../models/SubSection';
import type { UnGroupSubSectionRequest } from '../models/UnGroupSubSectionRequest';
import type { UpdateSubSectionRequest } from '../models/UpdateSubSectionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class SubSectionControllerService {

    /**
     * createSubsection
     * @returns SubSection OK
     * @returns any Created
     * @throws ApiError
     */
    public static createSubsectionUsingPost({
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
        request: CreateSubSectionRequest,
    }): CancelablePromise<SubSection | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/subsections/',
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
     * groupSubSection
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static groupSubSectionUsingPost({
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
        request: GroupSubSectionRequest,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/subsections/group',
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
     * unGroupSubSection
     * @returns string OK
     * @returns any Created
     * @throws ApiError
     */
    public static unGroupSubSectionUsingPost({
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
        request: UnGroupSubSectionRequest,
    }): CancelablePromise<string | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/pages/{page}/subsections/un-group',
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
     * @returns SubSection OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateSubSectionUsingPut({
        authorization,
        page,
        request,
        subSectionId,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: UpdateSubSectionRequest,
        /**
         * subSectionId
         */
        subSectionId: number,
    }): CancelablePromise<SubSection | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{page}/subsections/{subSectionId}',
            path: {
                'page': page,
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
     * @returns any OK
     * @throws ApiError
     */
    public static deleteSubSectionUsingDelete({
        authorization,
        page,
        subSectionId,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * subSectionId
         */
        subSectionId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/nbs/page-builder/api/v1/pages/{page}/subsections/{subSectionId}',
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
