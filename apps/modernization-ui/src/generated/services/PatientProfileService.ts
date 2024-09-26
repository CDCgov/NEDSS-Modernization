/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CreatedPatient } from '../models/CreatedPatient';
import type { NewPatient } from '../models/NewPatient';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientProfileService {
    /**
     * PatientProfile
     * Allows creation of a patient
     * @returns CreatedPatient Created
     * @throws ApiError
     */
    public static create({
        requestBody,
    }: {
        requestBody: NewPatient,
    }): CancelablePromise<CreatedPatient> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/profile',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
