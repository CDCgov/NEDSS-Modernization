/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageRule } from './PageRule';
import type { PageTab } from './PageTab';

export type PagedDetail = {
    Name?: string;
    id?: number;
    pageDescription?: string;
    pageRules?: Array<PageRule>;
    pageTabs?: Array<PageTab>;
};

