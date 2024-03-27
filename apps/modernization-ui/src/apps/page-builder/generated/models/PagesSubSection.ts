/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PagesQuestion } from './PagesQuestion';

export type PagesSubSection = {
    id: number;
    name: string;
    order: number;
    visible: boolean;
    isGrouped: boolean;
    isGroupable: boolean;
    questionIdentifier: string;
    blockName?: string;
    questions: Array<PagesQuestion>;
};

