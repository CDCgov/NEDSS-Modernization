/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SourceValue } from './SourceValue';
export type RuleRequest = {
    ruleFunction: RuleRequest.ruleFunction;
    description?: string;
    sourceIdentifier: string;
    anySourceValue: boolean;
    sourceValues?: Array<SourceValue>;
    comparator: RuleRequest.comparator;
    targetType: RuleRequest.targetType;
    targetIdentifiers: Array<string>;
    sourceText?: string;
    targetValueText?: Array<string>;
};
export namespace RuleRequest {
    export enum ruleFunction {
        DATE_COMPARE = 'DATE_COMPARE',
        DISABLE = 'DISABLE',
        ENABLE = 'ENABLE',
        HIDE = 'HIDE',
        REQUIRE_IF = 'REQUIRE_IF',
        UNHIDE = 'UNHIDE',
    }
    export enum comparator {
        EQUAL_TO = 'EQUAL_TO',
        NOT_EQUAL_TO = 'NOT_EQUAL_TO',
        GREATER_THAN = 'GREATER_THAN',
        GREATER_THAN_OR_EQUAL_TO = 'GREATER_THAN_OR_EQUAL_TO',
        LESS_THAN = 'LESS_THAN',
        LESS_THAN_OR_EQUAL_TO = 'LESS_THAN_OR_EQUAL_TO',
    }
    export enum targetType {
        QUESTION = 'QUESTION',
        SUBSECTION = 'SUBSECTION',
    }
}

