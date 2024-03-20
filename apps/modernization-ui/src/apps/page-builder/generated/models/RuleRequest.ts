/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { SourceValue } from './SourceValue';

export type RuleRequest = {
    anySourceValue: boolean;
    comparator: RuleRequest.comparator;
    description?: string;
    ruleFunction: RuleRequest.ruleFunction;
    sourceIdentifier: string;
    sourceText?: string;
    sourceValues?: Array<SourceValue>;
    targetIdentifiers: Array<string>;
    targetType: RuleRequest.targetType;
    targetValueText?: Array<string>;
};

export namespace RuleRequest {

    export enum comparator {
        EQUAL_TO = 'EQUAL_TO',
        GREATER_THAN = 'GREATER_THAN',
        GREATER_THAN_OR_EQUAL_TO = 'GREATER_THAN_OR_EQUAL_TO',
        LESS_THAN = 'LESS_THAN',
        LESS_THAN_OR_EQUAL_TO = 'LESS_THAN_OR_EQUAL_TO',
        NOT_EQUAL_TO = 'NOT_EQUAL_TO',
    }

    export enum ruleFunction {
        DATE_COMPARE = 'DATE_COMPARE',
        DISABLE = 'DISABLE',
        ENABLE = 'ENABLE',
        HIDE = 'HIDE',
        REQUIRE_IF = 'REQUIRE_IF',
        UNHIDE = 'UNHIDE',
    }

    export enum targetType {
        QUESTION = 'QUESTION',
        SUBSECTION = 'SUBSECTION',
    }


}

