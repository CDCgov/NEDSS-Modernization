/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CodedQuestion } from '../models/CodedQuestion';
import type { CreateCodedQuestionRequest } from '../models/CreateCodedQuestionRequest';
import type { CreateDateQuestionRequest } from '../models/CreateDateQuestionRequest';
import type { CreateNumericQuestionRequest } from '../models/CreateNumericQuestionRequest';
import type { CreateTextQuestionRequest } from '../models/CreateTextQuestionRequest';
import type { DateQuestion } from '../models/DateQuestion';
import type { FindQuestionRequest } from '../models/FindQuestionRequest';
import type { GetQuestionResponse } from '../models/GetQuestionResponse';
import type { NumericQuestion } from '../models/NumericQuestion';
import type { PageQuestion } from '../models/PageQuestion';
import type { QuestionStatusRequest } from '../models/QuestionStatusRequest';
import type { TextQuestion } from '../models/TextQuestion';
import type { UpdateCodedQuestionRequest } from '../models/UpdateCodedQuestionRequest';
import type { UpdateDateQuestionRequest } from '../models/UpdateDateQuestionRequest';
import type { UpdateNumericQuestionRequest } from '../models/UpdateNumericQuestionRequest';
import type { UpdateTextQuestionRequest } from '../models/UpdateTextQuestionRequest';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class QuestionControllerService {
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static setQuestionStatus({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: QuestionStatusRequest,
    }): CancelablePromise<(CodedQuestion | DateQuestion | NumericQuestion | TextQuestion)> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/questions/{id}/status',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static updateTextQuestion({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: UpdateTextQuestionRequest,
    }): CancelablePromise<(CodedQuestion | DateQuestion | NumericQuestion | TextQuestion)> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/questions/text/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static updateNumericQuestion({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: UpdateNumericQuestionRequest,
    }): CancelablePromise<(CodedQuestion | DateQuestion | NumericQuestion | TextQuestion)> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/questions/numeric/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static updateDateQuestion({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: UpdateDateQuestionRequest,
    }): CancelablePromise<(CodedQuestion | DateQuestion | NumericQuestion | TextQuestion)> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/questions/date/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns any OK
     * @throws ApiError
     */
    public static updateCodedQuestion({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: UpdateCodedQuestionRequest,
    }): CancelablePromise<(CodedQuestion | DateQuestion | NumericQuestion | TextQuestion)> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/api/v1/questions/coded/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns TextQuestion Created
     * @throws ApiError
     */
    public static createTextQuestion({
        requestBody,
    }: {
        requestBody: CreateTextQuestionRequest,
    }): CancelablePromise<TextQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/text',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns PageQuestion OK
     * @throws ApiError
     */
    public static findQuestions({
        requestBody,
        page,
        size = 25,
        sort,
    }: {
        requestBody: FindQuestionRequest,
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
    }): CancelablePromise<PageQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/search',
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns NumericQuestion Created
     * @throws ApiError
     */
    public static createNumericQuestion({
        requestBody,
    }: {
        requestBody: CreateNumericQuestionRequest,
    }): CancelablePromise<NumericQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/numeric',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DateQuestion Created
     * @throws ApiError
     */
    public static createDateQuestion({
        requestBody,
    }: {
        requestBody: CreateDateQuestionRequest,
    }): CancelablePromise<DateQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/date',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns CodedQuestion Created
     * @throws ApiError
     */
    public static createCodedQuestion({
        requestBody,
    }: {
        requestBody: CreateCodedQuestionRequest,
    }): CancelablePromise<CodedQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/coded',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns PageQuestion OK
     * @throws ApiError
     */
    public static findAllQuestions({
        page,
        size = 25,
        sort,
    }: {
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
    }): CancelablePromise<PageQuestion> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/questions',
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
        });
    }
    /**
     * @returns GetQuestionResponse OK
     * @throws ApiError
     */
    public static getQuestion({
        id,
    }: {
        id: number,
    }): CancelablePromise<GetQuestionResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/questions/{id}',
            path: {
                'id': id,
            },
        });
    }
}
