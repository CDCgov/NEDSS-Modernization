/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PagesQuestion } from './PagesQuestion';

export type PagesSubSection = {
    id?: number;
    name?: string;
    order?: number;
    questions?: Array<PagesQuestion>;
    visible?: boolean;
};

