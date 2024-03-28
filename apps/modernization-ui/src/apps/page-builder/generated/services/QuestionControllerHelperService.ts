/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DisplayControlOptions } from '../models/DisplayControlOptions';
import type { QuestionValidationRequest } from '../models/QuestionValidationRequest';
import type { QuestionValidationResponse } from '../models/QuestionValidationResponse';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class QuestionControllerHelperService {
    /**
     * @returns QuestionValidationResponse OK
     * @throws ApiError
     */
    public static validate({
        requestBody,
    }: {
        requestBody: QuestionValidationRequest,
    }): CancelablePromise<QuestionValidationResponse> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/questions/validate',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns DisplayControlOptions OK
     * @throws ApiError
     */
    public static getDisplayControlOptions(): CancelablePromise<DisplayControlOptions> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/questions/displayControlOptions',
        });
    }
}
