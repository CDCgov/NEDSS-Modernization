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
import type { Page_Question_ } from '../models/Page_Question_';
import type { Question } from '../models/Question';
import type { QuestionStatusRequest } from '../models/QuestionStatusRequest';
import type { TextQuestion } from '../models/TextQuestion';
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
        authorization: string,
        page?: number,
        size?: number,
        sort?: string,
    }): CancelablePromise<Page_Question_> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/questions',
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
     * createCodedQuestion
     * @returns CodedQuestion Created
     * @throws ApiError
     */
    public static createCodedQuestionUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: CreateCodedQuestionRequest,
    }): CancelablePromise<CodedQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/questions/coded',
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
     * createDateQuestion
     * @returns DateQuestion Created
     * @throws ApiError
     */
    public static createDateQuestionUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: CreateDateQuestionRequest,
    }): CancelablePromise<DateQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/questions/date',
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
     * createNumericQuestion
     * @returns NumericQuestion Created
     * @throws ApiError
     */
    public static createNumericQuestionUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: CreateNumericQuestionRequest,
    }): CancelablePromise<NumericQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/questions/numeric',
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
        authorization: string,
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
            url: '/nbs/page-builder/api/v1/questions/search',
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
     * createTextQuestion
     * @returns TextQuestion Created
     * @throws ApiError
     */
    public static createTextQuestionUsingPost({
        authorization,
        request,
    }: {
        authorization: string,
        /**
         * request
         */
        request: CreateTextQuestionRequest,
    }): CancelablePromise<TextQuestion> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/questions/text',
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
     * getQuestion
     * @returns GetQuestionResponse OK
     * @throws ApiError
     */
    public static getQuestionUsingGet({
        authorization,
        id,
    }: {
        authorization: string,
        /**
         * id
         */
        id: number,
    }): CancelablePromise<GetQuestionResponse> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/questions/{id}',
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
        authorization: string,
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
            url: '/nbs/page-builder/api/v1/questions/{id}',
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
        authorization: string,
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
            url: '/nbs/page-builder/api/v1/questions/{id}/status',
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
