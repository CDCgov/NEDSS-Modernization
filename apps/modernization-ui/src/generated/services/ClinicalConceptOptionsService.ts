/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ConceptOption } from '../models/ConceptOption';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class ClinicalConceptOptionsService {
    /**
     * Clinical concept options by value set
     * Provides options from clinical concepts grouped into a value set.
     * @returns ConceptOption OK
     * @throws ApiError
     */
    public static clinicalConcepts({
        name,
    }: {
        name: string,
    }): CancelablePromise<Array<ConceptOption>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/api/options/clinical/concepts/{name}',
            path: {
                'name': name,
            },
        });
    }
}
