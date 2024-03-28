/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CodedQuestion } from './CodedQuestion';
import type { DateQuestion } from './DateQuestion';
import type { NumericQuestion } from './NumericQuestion';
import type { TextQuestion } from './TextQuestion';
export type GetQuestionResponse = {
    question?: (CodedQuestion | DateQuestion | NumericQuestion | TextQuestion);
    isInUse?: boolean;
};

