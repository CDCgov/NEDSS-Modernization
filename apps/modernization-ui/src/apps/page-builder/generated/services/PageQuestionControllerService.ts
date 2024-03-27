/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AddQuestionRequest } from '../models/AddQuestionRequest';
import type { AddQuestionResponse } from '../models/AddQuestionResponse';
import type { EditableQuestion } from '../models/EditableQuestion';
import type { UpdatePageCodedQuestionRequest } from '../models/UpdatePageCodedQuestionRequest';
import type { UpdatePageCodedQuestionValuesetRequest } from '../models/UpdatePageCodedQuestionValuesetRequest';
import type { UpdatePageDateQuestionRequest } from '../models/UpdatePageDateQuestionRequest';
import type { UpdatePageNumericQuestionRequest } from '../models/UpdatePageNumericQuestionRequest';
import type { UpdatePageQuestionRequiredRequest } from '../models/UpdatePageQuestionRequiredRequest';
import type { UpdatePageTextQuestionRequest } from '../models/UpdatePageTextQuestionRequest';
import type { ValidationResponse } from '../models/ValidationResponse';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class PageQuestionControllerService {

    /**
     * @returns EditableQuestion OK
     * @throws ApiError
     */
    public static updatePageQuestionRequired({
        page,
        questionId,
        requestBody,
    }: {
        page: number,
        questionId: number,
        requestBody: UpdatePageQuestionRequiredRequest,
    }): CancelablePromise<EditableQuestion> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/questions/{questionId}/required',
            path: {
                'page': page,
                'questionId': questionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns EditableQuestion OK
     * @throws ApiError
     */
    public static updatePageTextQuestion({
        page,
        questionId,
        requestBody,
    }: {
        page: number,
        questionId: number,
        requestBody: UpdatePageTextQuestionRequest,
    }): CancelablePromise<EditableQuestion> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/questions/text/{questionId}',
            path: {
                'page': page,
                'questionId': questionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns EditableQuestion OK
     * @throws ApiError
     */
    public static updatePageNumericQuestion({
        page,
        questionId,
        requestBody,
    }: {
        page: number,
        questionId: number,
        requestBody: UpdatePageNumericQuestionRequest,
    }): CancelablePromise<EditableQuestion> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/questions/numeric/{questionId}',
            path: {
                'page': page,
                'questionId': questionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns EditableQuestion OK
     * @throws ApiError
     */
    public static updatePageDateQuestion({
        page,
        questionId,
        requestBody,
    }: {
        page: number,
        questionId: number,
        requestBody: UpdatePageDateQuestionRequest,
    }): CancelablePromise<EditableQuestion> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/questions/date/{questionId}',
            path: {
                'page': page,
                'questionId': questionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns EditableQuestion OK
     * @throws ApiError
     */
    public static updatePageCodedQuestion({
        page,
        questionId,
        requestBody,
    }: {
        page: number,
        questionId: number,
        requestBody: UpdatePageCodedQuestionRequest,
    }): CancelablePromise<EditableQuestion> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/questions/coded/{questionId}',
            path: {
                'page': page,
                'questionId': questionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns EditableQuestion OK
     * @throws ApiError
     */
    public static updatePageCodedQuestionValueset({
        page,
        questionId,
        requestBody,
    }: {
        page: number,
        questionId: number,
        requestBody: UpdatePageCodedQuestionValuesetRequest,
    }): CancelablePromise<EditableQuestion> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/pages/{page}/questions/coded/{questionId}/valueset',
            path: {
                'page': page,
                'questionId': questionId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns AddQuestionResponse OK
     * @throws ApiError
     */
    public static addQuestionToPage({
        page,
        subsection,
        requestBody,
    }: {
        page: number,
        subsection: number,
        requestBody: AddQuestionRequest,
    }): CancelablePromise<AddQuestionResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/pages/{page}/subsection/{subsection}/questions',
            path: {
                'page': page,
                'subsection': subsection,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * @returns EditableQuestion OK
     * @throws ApiError
     */
    public static getEditableQuestion({
        page,
        questionId,
    }: {
        page: number,
        questionId: number,
    }): CancelablePromise<EditableQuestion> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{page}/questions/{questionId}/edit',
            path: {
                'page': page,
                'questionId': questionId,
            },
        });
    }

    /**
     * @returns ValidationResponse OK
     * @throws ApiError
     */
    public static validateDatamart({
        page,
        questionId,
        datamart,
    }: {
        page: number,
        questionId: number,
        datamart: string,
    }): CancelablePromise<ValidationResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/pages/{page}/questions/{questionId}/datamart/validate',
            path: {
                'page': page,
                'questionId': questionId,
            },
            query: {
                'datamart': datamart,
            },
        });
    }

    /**
     * @returns any OK
     * @throws ApiError
     */
    public static deleteQuestion({
        page,
        questionId,
    }: {
        page: number,
        questionId: number,
    }): CancelablePromise<any> {
        return __request(OpenAPI, {
            method: 'DELETE',
            url: '/api/v1/pages/{page}/questions/{questionId}',
            path: {
                'page': page,
                'questionId': questionId,
            },
        });
    }

}
