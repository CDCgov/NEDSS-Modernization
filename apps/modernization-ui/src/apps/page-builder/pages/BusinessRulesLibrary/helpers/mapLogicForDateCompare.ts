import { Rule } from 'apps/page-builder/generated';

export const mapLogicForDateCompare = (logic: Rule.comparator) => {
    switch (logic) {
        case Rule.comparator.EQUAL_TO:
            return '=';
        case Rule.comparator.LESS_THAN:
            return '<';
        case Rule.comparator.LESS_THAN_OR_EQUAL_TO:
            return '<=';
        case Rule.comparator.GREATER_THAN:
            return '>';
        case Rule.comparator.GREATER_THAN_OR_EQUAL_TO:
            return '>=';
        default:
            return '';
    }
};
