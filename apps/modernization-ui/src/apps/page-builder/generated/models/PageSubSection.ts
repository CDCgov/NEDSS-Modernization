/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PageQuestion } from './PageQuestion';

export type PageSubSection = {
    id?: number;
    name?: string;
    pageQuestions?: Array<PageQuestion>;
    visible?: string;
};

