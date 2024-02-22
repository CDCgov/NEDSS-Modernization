import { Rule } from 'apps/page-builder/generated';

export const mapRuleFunctionToString = (ruleFunction: Rule.ruleFunction) => {
    switch (ruleFunction) {
        case Rule.ruleFunction.DATE_COMPARE:
            return 'Date validation';
        case Rule.ruleFunction.ENABLE:
            return 'Enable';
        case Rule.ruleFunction.DISABLE:
            return 'Disable';
        case Rule.ruleFunction.HIDE:
            return 'Hide';
        case Rule.ruleFunction.UNHIDE:
            return 'Unhide';
        case Rule.ruleFunction.REQUIRE_IF:
            return 'Require if';
    }
};
