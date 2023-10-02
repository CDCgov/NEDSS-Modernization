/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageSection } from './PageSection';

export type PageTab = {
    id?: number;
    name?: string;
    tabSections?: Array<PageSection>;
    visible?: string;
};

