/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageSubSection } from './PageSubSection';

export type PageSection = {
    id?: number;
    name?: string;
    sectionSubSections?: Array<PageSubSection>;
    visible?: string;
};

