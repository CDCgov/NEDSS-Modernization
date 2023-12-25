/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageRule } from './PageRule';
import type { PagesTab } from './PagesTab';

export type PagesResponse = {
    description?: string;
    id: number;
    name: string;
    root?: number;
    rules?: Array<PageRule>;
    status: string;
    tabs?: Array<PagesTab>;
};

