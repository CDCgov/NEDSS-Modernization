/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ExistingRaceCategory } from '../models/ExistingRaceCategory';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class PatientRaceService {
    /**
     * Validates that a patient can accept a race demographic for the given category.
     * @returns ExistingRaceCategory Allowable race category for the patient
     * @throws ApiError
     */
    public static validateCategory({
        patient,
        category,
    }: {
        patient: number,
        category: string,
    }): CancelablePromise<ExistingRaceCategory> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/patients/{patient}/demographics/race/categories/{category}/validate',
            path: {
                'patient': patient,
                'category': category,
            },
            errors: {
                400: `The race category is already present on the patient`,
            },
        });
    }
}
