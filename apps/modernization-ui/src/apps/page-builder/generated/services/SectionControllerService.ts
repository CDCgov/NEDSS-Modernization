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
     * @returns Section OK
     * @throws ApiError
     */
    public static updateSection({
        page,
        section,
        requestBody,
    }: {
        page: number,
        section: number,
        requestBody: UpdateSectionRequest,
    }): CancelablePromise<Section> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/sections/{section}',
            path: {
                'page': page,
                'section': section,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns Section OK
     * @throws ApiError
     */
    public static createSection({
        page,
        requestBody,
    }: {
        page: number,
        requestBody: CreateSectionRequest,
    }): CancelablePromise<Section> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/sections/',
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
    public static deleteSection({
        page,
        sectionId,
    }: {
        page: number,
        sectionId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/pages/{page}/sections/{sectionId}',
            path: {
                'page': page,
                'sectionId': sectionId,
            },
        });
    }

}
