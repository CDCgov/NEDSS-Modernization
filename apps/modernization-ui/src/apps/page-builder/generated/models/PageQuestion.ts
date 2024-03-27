/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CodedQuestion } from './CodedQuestion';
import type { DateQuestion } from './DateQuestion';
import type { NumericQuestion } from './NumericQuestion';
import type { PageableObject } from './PageableObject';
import type { SortObject } from './SortObject';
import type { TextQuestion } from './TextQuestion';

export type PageQuestion = {
    totalElements?: number;
    totalPages?: number;
    pageable?: PageableObject;
    size?: number;
    content?: Array<(CodedQuestion | DateQuestion | NumericQuestion | TextQuestion)>;
    numberOfElements?: number;
    sort?: SortObject;
    first?: boolean;
    last?: boolean;
    number?: number;
    empty?: boolean;
};

