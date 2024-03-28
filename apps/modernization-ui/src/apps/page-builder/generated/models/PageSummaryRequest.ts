/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Date } from './Date';
import type { DateRange } from './DateRange';
import type { MultiValue } from './MultiValue';
import type { SingleValue } from './SingleValue';
export type PageSummaryRequest = {
    search?: string;
    filters?: Array<(Date | DateRange | MultiValue | SingleValue)>;
};

