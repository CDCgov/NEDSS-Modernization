/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Template } from '../models/Template';

import type { CancelablePromise } from '../core/CancelablePromise';
import { OpenAPI } from '../core/OpenAPI';
import { request as __request } from '../core/request';

export class TemplateControllerService {

    /**
     * findAllTemplates
     * @returns Template OK
     * @throws ApiError
     */
    public static findAllTemplatesUsingGet({
        authorization,
    }: {
        authorization: string,
    }): CancelablePromise<Array<Template>> {
        return __request(OpenAPI, {
            method: 'GET',
            url: '/nbs/page-builder/api/v1/template/',
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
     * importTemplate
     * @returns Template OK
     * @returns any Created
     * @throws ApiError
     */
    public static importTemplateUsingPost({
        authorization,
        fileInput,
    }: {
        authorization: string,
        fileInput?: Blob,
    }): CancelablePromise<Template | any> {
        return __request(OpenAPI, {
            method: 'POST',
            url: '/nbs/page-builder/api/v1/template/import',
            headers: {
                'Authorization': authorization,
            },
            formData: {
                'fileInput': fileInput,
            },
            errors: {
                401: `Unauthorized`,
                403: `Forbidden`,
                404: `Not Found`,
            },
        });
    }

}
