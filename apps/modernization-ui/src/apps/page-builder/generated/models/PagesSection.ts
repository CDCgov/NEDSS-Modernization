/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PagesSubSection } from './PagesSubSection';

export type PagesSection = {
    id: number;
    name: string;
    order: number;
    subSections: Array<PagesSubSection>;
    visible: boolean;
};

