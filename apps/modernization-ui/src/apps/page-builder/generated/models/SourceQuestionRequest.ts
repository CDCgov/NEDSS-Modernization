/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

export type SourceQuestionRequest = {
    ruleFunction: SourceQuestionRequest.ruleFunction;
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

