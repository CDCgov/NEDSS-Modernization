import { asSelectableOperator, asOperationValueOrString, asOperationValueOnly } from './utils';
import { operators, defaultOperator } from './operators';
import { Selectable } from 'options/selectable';
import { OperationValue } from './types';

describe('utils', () => {
    describe('asSelectableOperator', () => {
        it('should return the default operator when value is null', () => {
            expect(asSelectableOperator(null)).toBe(defaultOperator);
        });

        it('should return the default operator when value is undefined', () => {
            expect(asSelectableOperator(undefined)).toBe(defaultOperator);
        });

        it('should return the correct operator when value is found', () => {
            expect(asSelectableOperator('not')).toBe(operators.find((o) => o.value === 'not'));
        });
    });

    describe('asOperationValueOrString', () => {
        it('should return null when both value and operator are not provided', () => {
            expect(asOperationValueOrString()).toBeNull();
        });

        it('should return value when operator is not provided', () => {
            const value = 'testValue';
            expect(asOperationValueOrString(value)).toBe(value);
        });

        it('should return string when operator is not provided', () => {
            const value = 'hello';
            expect(asOperationValueOrString(value)).toEqual(value);
        });

        it('should return operation object when both value and operator are provided', () => {
            const value = 'testValue';
            const operator = 'startsWith';
            expect(asOperationValueOrString(value, operator)).toEqual({ [operator]: value });
        });

        it('should return operation object when operator is a Selectable object', () => {
            const value = 'testValue';
            const operator: Selectable = {
                value: 'contains',
                label: 'Contains',
                name: ''
            };
            expect(asOperationValueOrString(value, operator)).toEqual({ contains: value });
        });
    });

    describe('asOperationValueOnly', () => {
        it('should return null when value is null', () => {
            expect(asOperationValueOnly(null)).toBeNull();
        });

        it('should return undefined when value is undefined', () => {
            expect(asOperationValueOnly(undefined)).toBeUndefined();
        });

        it('should return the string value when value is a string', () => {
            const value = 'testValue';
            expect(asOperationValueOnly(value)).toBe(value);
        });

        it('should return the first operation value when value is an object', () => {
            const value: OperationValue = { startsWith: 'testValue' };
            expect(asOperationValueOnly(value)).toBe('testValue');
        });
    });
});
