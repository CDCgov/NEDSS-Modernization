/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { SourceQuestion } from './SourceQuestion';
import type { Target } from './Target';

export type Rule = {
    anySourceValue: boolean;
    comparator: Rule.comparator;
    description?: string;
    id: number;
    ruleFunction: Rule.ruleFunction;
    sourceQuestion: SourceQuestion;
    sourceValues?: Array<string>;
    targetType: Rule.targetType;
    targets: Array<Target>;
    template: number;
};

export namespace Rule {

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

