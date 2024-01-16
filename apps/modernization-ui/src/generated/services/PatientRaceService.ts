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
     * @returns any Created
     * @throws ApiError
     */
    public static validateCategory({
        category,
        patient,
    }: {
        /**
         * category
         */
        category: string,
        /**
         * patient
         */
        patient: number,
    }): CancelablePromise<ExistingRaceCategory | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/api/patients/{patient}/demographics/race/categories/{category}/validate',
            path: {
                'category': category,
                'patient': patient,
            },
            errors: {
                400: `The race category is already present on the patient`,
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
