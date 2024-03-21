/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { PagesQuestion } from './PagesQuestion';

export type SourceQuestionRequest = {
    ruleFunction: SourceQuestionRequest.ruleFunction;
    targetQuestions?: Array<PagesQuestion>;
};

export namespace SourceQuestionRequest {

    export enum ruleFunction {
        DATE_COMPARE = 'DATE_COMPARE',
        DISABLE = 'DISABLE',
        ENABLE = 'ENABLE',
        HIDE = 'HIDE',
        REQUIRE_IF = 'REQUIRE_IF',
        UNHIDE = 'UNHIDE',
    }


}

