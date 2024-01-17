/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { QuestionInfo } from './QuestionInfo';

export type ViewRuleResponse = {
    comparator?: string;
    errorMsgText?: string;
    ruleDescription?: string;
    ruleFunction?: string;
    ruleId?: number;
    sourceIdentifier?: string;
    sourceValue?: {
        sourceValueId: string[];
        sourceValueText: string[];
    };
    targetQuestions?: Array<QuestionInfo>;
    targetType?: string;
    templateUid?: number;
    anySourceValue?: boolean;
};
