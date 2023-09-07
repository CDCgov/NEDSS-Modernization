/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import { Subsection } from './Subsection';

export type Section = {
    id: number;
    name: string;
    sectionSubSections: Subsection[];
    visible: string;
};
