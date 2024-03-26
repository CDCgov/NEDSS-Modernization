/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { Batch } from './Batch';

export type GroupSubSectionRequest = {
    blockName: string;
    batches: Array<Batch>;
    repeatingNbr: number;
};

