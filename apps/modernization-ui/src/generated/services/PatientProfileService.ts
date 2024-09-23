/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NewPatient } from '../models/NewPatient';
import type { PatientIdentifier } from '../models/PatientIdentifier';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientProfileService {
    /**
     * PatientProfile
     * Allows creation of a patient
     * @returns PatientIdentifier Created
     * @throws ApiError
     */
    public static create({
        requestBody,
    }: {
        requestBody: NewPatient,
    }): CancelablePromise<PatientIdentifier> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/profile',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
