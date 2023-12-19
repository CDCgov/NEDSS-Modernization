/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PagesSection } from './PagesSection';

export type PagesTab = {
    id: number;
    name: string;
    order: number;
    sections: Array<PagesSection>;
    visible: boolean;
};

