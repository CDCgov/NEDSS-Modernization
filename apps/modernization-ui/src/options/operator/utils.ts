import { findByValue } from 'options/findByValue';
import { textOperators, defaultTextOperator } from './operators';
import { Selectable } from 'options/selectable';
import { TextOperation, TextCriteria } from './types';

const hasLegacyOperators = (value: string) => value && value.startsWith('%') && value.length > 1;

// strip leading % if it exists as it is a contains operator
const stripLegacyOperators = (value: string) => (hasLegacyOperators(value) ? value.substring(1) : value);

/**
 * Converts the value to a valid selectable operator.
 * @param {string} value The value of the operator to find
 * @return {Selectable} The selectable object, or the default operator if the value is not found
 */
export const asSelectableOperator = (value: string | null | undefined) =>
    (value && findByValue(textOperators, defaultTextOperator)(value)) || defaultTextOperator;

/**
 * Returns the value as an operation value or string.
 * @param {string} value The value of the operator to find
 * @param {string} operator A operator i.e. "equals" or selectable object containing the operator.
 * @return {TextCriteria} The a valid TextCriteria object, a string, null or undefined
 */
export const asTextCriteriaOrString = (
    value?: string | null,
    operator?: TextOperation | Selectable
): TextCriteria | string | null | undefined => {
    const operation = operator && typeof operator === 'object' ? operator.value : operator;
    if (!operation && !value) {
        return null;
    } else if (!operation) {
        return value;
    } else {
        return { [operation]: value };
    }
};

/**
 * Extracts the value from an OperationValue object, or returns the value if it is a string.
 * For example, { "equals": "Bob" } would return "Bob".
 * @param {string | TextCriteria} value The operation value
 * @return {string} The string value
 */
export const asTextCriteriaValue = (value?: string | TextCriteria | null): string | null | undefined => {
    if (value != null && typeof value === 'object' && Object.keys(value).length >= 1) {
        return value[Object.keys(value)[0] as TextOperation] as string;
    }
    if (value != null && typeof value === 'string') {
        return stripLegacyOperators(value);
    }
    return value as string | null | undefined;
};

/**
 * Returns the value as a TextCriteria object. If the string starts with '%', treats it as a contains operator.
 * @param {string} value The value of the operator to find
 * @param {string} operation A operator i.e. "equals" or selectable object containing the operator.
 * @return {TextCriteria} The a valid TextCriteria object, or undefined
 */
export const asTextCriteria = (
    value?: string | TextCriteria | null,
    operation?: TextOperation
): TextCriteria | null | undefined => {
    if (typeof value === 'string') {
        if (hasLegacyOperators(value)) {
            return { contains: stripLegacyOperators(value) };
        } else if (operation) {
            return { [operation]: value };
        }
        return { equals: value };
    } else {
        return value;
    }
};

/**
 * Returns the operator string, if no operator then will return undefined
 * @param {string | TextCriteria} value
 * @return {string} or undefined
 */
export const asTextCriteriaOperator = (value?: string | TextCriteria | null): string | undefined => {
    if (value != null && typeof value === 'string') {
        return undefined;
    }
    if (value != null && typeof value === 'object') {
        return transformOperator(Object.keys(value)[0] as TextOperation);
    }

    return undefined;
};

const transformOperator = (value: TextOperation): string => {
    switch (value) {
        case 'contains':
            return 'Contains';
        case 'equals':
            return 'Equals';
        case 'soundsLike':
            return 'Sounds like';
        case 'startsWith':
            return 'Starts with';
        case 'not':
            return 'Not equal';
        default:
            return '';
    }
};
