/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PagesSubSection } from './PagesSubSection';

export type PageSection = {
    id: number;
    name?: string;
    sectionSubSections?: Array<PagesSubSection>;
    visible?: boolean;
};

