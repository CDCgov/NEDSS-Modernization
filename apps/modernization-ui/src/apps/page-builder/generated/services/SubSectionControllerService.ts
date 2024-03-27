/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateSubSectionRequest } from '../models/CreateSubSectionRequest';
import type { GroupSubSectionRequest } from '../models/GroupSubSectionRequest';
import type { SubSection } from '../models/SubSection';
import type { UpdateSubSectionRequest } from '../models/UpdateSubSectionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class SubSectionControllerService {

    /**
     * @returns SubSection OK
     * @throws ApiError
     */
    public static updateSubSection({
        page,
        subSectionId,
        requestBody,
    }: {
        page: number,
        subSectionId: number,
        requestBody: UpdateSubSectionRequest,
    }): CancelablePromise<SubSection> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/subsections/{subSectionId}',
            path: {
                'page': page,
                'subSectionId': subSectionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static deleteSubSection({
        page,
        subSectionId,
    }: {
        page: number,
        subSectionId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/pages/{page}/subsections/{subSectionId}',
            path: {
                'page': page,
                'subSectionId': subSectionId,
            },
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static groupSubSection({
        page,
        subsection,
        requestBody,
    }: {
        page: number,
        subsection: number,
        requestBody: GroupSubSectionRequest,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/subsections/{subsection}/group',
            path: {
                'page': page,
                'subsection': subsection,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns SubSection OK
     * @throws ApiError
     */
    public static createSubsection({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: CreateSubSectionRequest,
    }): CancelablePromise<SubSection> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/subsections/',
            path: {
                'page': page,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static validateSubSection({
        page,
        subSectionId,
    }: {
        page: number,
        subSectionId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{page}/subsections/{subSectionId}/validate',
            path: {
                'page': page,
                'subSectionId': subSectionId,
            },
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static unGroupSubSection({
        page,
        subSectionId,
    }: {
        page: number,
        subSectionId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{page}/subsections/{subSectionId}/un-group',
            path: {
                'page': page,
                'subSectionId': subSectionId,
            },
        });
    }

}
