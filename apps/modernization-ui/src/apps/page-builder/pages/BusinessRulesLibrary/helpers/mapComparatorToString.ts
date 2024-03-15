import { Rule } from 'apps/page-builder/generated';

export const mapComparatorToString = (comparator?: Rule.comparator) => {
    switch (comparator) {
        case Rule.comparator.LESS_THAN:
            return 'Less than';
        case Rule.comparator.LESS_THAN_OR_EQUAL_TO:
            return 'Less than or equal to';
        case Rule.comparator.GREATER_THAN:
            return 'Greater than';
        case Rule.comparator.GREATER_THAN_OR_EQUAL_TO:
            return 'Greater than or equal to';
        case Rule.comparator.EQUAL_TO:
            return 'Equal to';
        case Rule.comparator.NOT_EQUAL_TO:
            return 'Not equal to';
    }
};
