/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PagesQuestion } from './PagesQuestion';

export type PagesSubSection = {
    blockName?: string;
    id: number;
    isGroupable: boolean;
    isGrouped: boolean;
    name: string;
    order: number;
    questionIdentifier: string;
    questions: Array<PagesQuestion>;
    visible: boolean;
};

