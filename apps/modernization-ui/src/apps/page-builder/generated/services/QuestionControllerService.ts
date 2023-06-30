/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreateQuestionRequest } from '../models/CreateQuestionRequest';
import type { CreateQuestionResponse } from '../models/CreateQuestionResponse';
import type { FindQuestionRequest } from '../models/FindQuestionRequest';
import type { GetQuestionResponse } from '../models/GetQuestionResponse';
import type { Page_Question_ } from '../models/Page_Question_';
import type { Question } from '../models/Question';
import type { QuestionStatusRequest } from '../models/QuestionStatusRequest';
import type { UpdateQuestionRequest } from '../models/UpdateQuestionRequest';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class QuestionControllerService {

    /**
     * findAllQuestions
     * @returns Page_Question_ OK
     * @throws ApiError
     */
    public static findAllQuestionsUsingGet({
        authorization,
        page,
        size,
        sort,
    }: {
        authorization: any,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Question_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/pageBuilder/api/v1/questions',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * createQuestion
     * @returns CreateQuestionResponse Created
     * @throws ApiError
     */
    public static createQuestionUsingPost({
        authorization,
        request,
    }: {
        authorization: any,
        /**
         * request
         */
        request: CreateQuestionRequest,
    }): CancelablePromise<CreateQuestionResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/pageBuilder/api/v1/questions',
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
     * findQuestions
     * @returns Page_Question_ OK
     * @returns any Created
     * @throws ApiError
     */
    public static findQuestionsUsingPost({
        authorization,
        request,
        page,
        size,
        sort,
    }: {
        authorization: any,
        /**
         * request
         */
        request: FindQuestionRequest,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Question_ | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/pageBuilder/api/v1/questions/search',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'page': page,
                'size': size,
                'sort': sort,
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
     * getQuestion
     * @returns GetQuestionResponse OK
     * @throws ApiError
     */
    public static getQuestionUsingGet({
        authorization,
        id,
    }: {
        authorization: any,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<GetQuestionResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/pageBuilder/api/v1/questions/{id}',
            path: {
                'id': id,
            },
            headers: {
                'Authorization': authorization,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * updateQuestion
     * @returns Question OK
     * @returns any Created
     * @throws ApiError
     */
    public static updateQuestionUsingPut({
        authorization,
        id,
        request,
    }: {
        authorization: any,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: UpdateQuestionRequest,
    }): CancelablePromise<Question | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/pageBuilder/api/v1/questions/{id}',
            path: {
                'id': id,
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
     * setQuestionStatus
     * @returns Question OK
     * @returns any Created
     * @throws ApiError
     */
    public static setQuestionStatusUsingPut({
        authorization,
        id,
        request,
    }: {
        authorization: any,
        /**
         * id
         */
        id: number,
        /**
         * request
         */
        request: QuestionStatusRequest,
    }): CancelablePromise<Question | any> {
        return __request(OpenAPI, {
            method: 'PUT',
            url: '/pageBuilder/api/v1/questions/{id}/status',
            path: {
                'id': id,
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
