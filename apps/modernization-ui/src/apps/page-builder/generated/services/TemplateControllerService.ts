/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Template } from '../models/Template';
import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';
export class TemplateControllerService {
    /**
     * Creates a new Template from an XML File.
     * Creates a new Template by importing an XML file that describes how the template should be created.
     * @returns Template OK
     * @throws ApiError
     */
    public static import({
        formData,
    }: {
        formData?: {
            file: Blob;
        },
    }): CancelablePromise<Template> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/api/v1/template/import',
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
    /**
     * @returns Template OK
     * @throws ApiError
     */
    public static findAllTemplates({
        type,
    }: {
        type?: string,
    }): CancelablePromise<Array<Template>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/api/v1/template/',
            query: {
                'type': type,
            },
        });
    }
}
