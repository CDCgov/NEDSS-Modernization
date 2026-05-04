/* generated using openapi-typescript-codegen -- do not edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Rule } from './Rule';
export type RuleGroup = {
    combinator: RuleGroup.combinator;
    rules: Array<(Rule | RuleGroup)>;
};
export namespace RuleGroup {
    export enum combinator {
        OR = 'or',
        AND = 'and',
    }
}

