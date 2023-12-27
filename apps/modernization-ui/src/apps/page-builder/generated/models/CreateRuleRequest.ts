/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { SourceValues } from './SourceValues';

export type CreateRuleRequest = {
    anySourceValue?: boolean;
    comparator?: string;
    ruleDescription?: string;
    ruleFunction?: string;
    sourceIdentifier?: string;
    sourceText?: string;
    sourceValue?: SourceValues;
    targetType?: string;
    targetValueIdentifier?: Array<string>;
    targetValueText?: Array<string>;
};

