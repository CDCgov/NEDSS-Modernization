/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PagesQuestion } from './PagesQuestion';
export type TargetQuestionRequest = {
    ruleFunction: TargetQuestionRequest.ruleFunction;
    sourceQuestion?: PagesQuestion;
    targetQuestion?: Array<PagesQuestion>;
};
export namespace TargetQuestionRequest {
    export enum ruleFunction {
        DATE_COMPARE = 'DATE_COMPARE',
        DISABLE = 'DISABLE',
        ENABLE = 'ENABLE',
        HIDE = 'HIDE',
        REQUIRE_IF = 'REQUIRE_IF',
        UNHIDE = 'UNHIDE',
    }
}

