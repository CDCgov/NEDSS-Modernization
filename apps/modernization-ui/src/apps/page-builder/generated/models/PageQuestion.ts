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
    totalPages?: number;
    totalElements?: number;
    pageable?: PageableObject;
    numberOfElements?: number;
    number?: number;
    first?: boolean;
    last?: boolean;
    sort?: SortObject;
    size?: number;
    content?: Array<(CodedQuestion | DateQuestion | NumericQuestion | TextQuestion)>;
    empty?: boolean;
};

