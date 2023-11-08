/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSectionRequest } from '../models/CreateSectionRequest';
import type { Section } from '../models/Section';
import type { UpdateSectionRequest } from '../models/UpdateSectionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class SectionControllerService {

    /**
     * createSection
     * @returns Section OK
     * @returns any Created
     * @throws ApiError
     */
    public static createSectionUsingPost({
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
        request: CreateSectionRequest,
    }): CancelablePromise<Section | any> {
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
     * deleteSection
     * @returns any OK
     * @throws ApiError
     */
    public static deleteSectionUsingDelete({
        authorization,
        page,
        sectionId,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * sectionId
         */
        sectionId: number,
    }): CancelablePromise<any> {
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

    /**
     * updateSection
     * @returns Section OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateSectionUsingPut({
        authorization,
        page,
        request,
        section,
    }: {
        authorization: string,
        /**
         * page
         */
        page: number,
        /**
         * request
         */
        request: UpdateSectionRequest,
        /**
         * section
         */
        section: number,
    }): CancelablePromise<Section | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/nbs/page-builder/api/v1/pages/{page}/sections/{section}',
            path: {
                'page': page,
                'section': section,
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
