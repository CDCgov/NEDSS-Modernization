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
     * @returns any Created
     * @throws ApiError
     */
    public static import({
        authorization,
        file,
    }: {
        authorization: string,
        file?: Blob,
    }): CancelablePromise<Template | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/template/import',
            headers: {
                'Authorization': authorization,
            },
            formData: {
                'file': file,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

    /**
     * findAllTemplates
     * @returns Template OK
     * @throws ApiError
     */
    public static findAllTemplatesUsingGet({
        authorization,
        type,
    }: {
        authorization: string,
        /**
         * type
         */
        type?: string,
    }): CancelablePromise<Array<Template>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/template/{type}/',
            headers: {
                'Authorization': authorization,
            },
            query: {
                'type': type,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
