/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { InputStream } from './InputStream';

export type Resource = {
    description?: string;
    file?: Blob;
    filename?: string;
    inputStream?: InputStream;
    open?: boolean;
    readable?: boolean;
    uri?: string;
    url?: string;
};

