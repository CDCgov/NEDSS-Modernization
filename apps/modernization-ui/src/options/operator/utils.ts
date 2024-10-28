import { findByValue } from 'options/findByValue';
import { operators, defaultOperator } from './operators';
import { Selectable } from 'options/selectable';
import { Operation, OperationValue } from './types';

/**
 * Converts the value to a valid selectable operator.
 * @param {string} value The value of the operator to find
 * @return {Selectable} The selectable object, or the default operator if the value is not found
 */
export const asSelectableOperator = (value: string | null | undefined) =>
    (value && findByValue(operators, defaultOperator)(value)) || defaultOperator;

/**
 * Returns the value as an operation value or string.
 * @param {string} value The value of the operator to find
 * @param {string} operator A operator i.e. "equals" or selectable object containing the operator.
 * @return {OperationValue} The a valid OperationValue object, a string, null or undefined
 */
export const asOperationValueOrString = (
    value?: string | null,
    operator?: Operation | Selectable
): OperationValue | string | null | undefined => {
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
 * @param {string | OperationValue} value The operation value
 * @return {string} The string value
 */
export const asOperationValueOnly = (value?: string | OperationValue | null): string | null | undefined => {
    if (value != null && typeof value === 'object' && Object.keys(value).length >= 1) {
        return value[Object.keys(value)[0] as Operation] as string;
    }
    return value as string | null | undefined;
};
