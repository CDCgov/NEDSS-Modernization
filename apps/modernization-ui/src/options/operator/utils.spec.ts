import {
    asSelectableOperator,
    asTextCriteria,
    asTextCriteriaOperator,
    asTextCriteriaOrString,
    asTextCriteriaValue
} from './utils';
import { textOperators, defaultTextOperator } from './operators';
import { Selectable } from 'options/selectable';
import { TextCriteria } from './types';

describe('utils', () => {
    describe('asSelectableOperator', () => {
        it('should return the default operator when value is null', () => {
            expect(asSelectableOperator(null)).toBe(defaultTextOperator);
        });

        it('should return the default operator when value is undefined', () => {
            expect(asSelectableOperator(undefined)).toBe(defaultTextOperator);
        });

        it('should return the correct operator when value is found', () => {
            expect(asSelectableOperator('not')).toBe(textOperators.find((o) => o.value === 'not'));
        });
    });

    describe('asTextCriteriaOrString', () => {
        it('should return null when both value and operator are not provided', () => {
            expect(asTextCriteriaOrString()).toBeNull();
        });

        it('should return value when operator is not provided', () => {
            const value = 'testValue';
            expect(asTextCriteriaOrString(value)).toBe(value);
        });

        it('should return string when operator is not provided', () => {
            const value = 'hello';
            expect(asTextCriteriaOrString(value)).toEqual(value);
        });

        it('should return operation object when both value and operator are provided', () => {
            const value = 'testValue';
            const operator = 'startsWith';
            expect(asTextCriteriaOrString(value, operator)).toEqual({ [operator]: value });
        });

        it('should return operation object when operator is a Selectable object', () => {
            const value = 'testValue';
            const operator: Selectable = {
                value: 'contains',
                label: 'Contains',
                name: ''
            };
            expect(asTextCriteriaOrString(value, operator)).toEqual({ contains: value });
        });
    });

    describe('asTextCriteriaValue', () => {
        it('should return null when value is null', () => {
            expect(asTextCriteriaValue(null)).toBeNull();
        });

        it('should return undefined when value is undefined', () => {
            expect(asTextCriteriaValue(undefined)).toBeUndefined();
        });

        it('should return the string value when value is a string', () => {
            const value = 'testValue';
            expect(asTextCriteriaValue(value)).toBe(value);
        });

        it('should return the first operation value when value is an object', () => {
            const value: TextCriteria = { startsWith: 'testValue' };
            expect(asTextCriteriaValue(value)).toBe('testValue');
        });
    });

    describe('asTextCriteria', () => {
        it('should return null when value is null', () => {
            expect(asTextCriteria(null)).toBeNull();
        });

        it('should return undefined when value is undefined', () => {
            expect(asTextCriteria(undefined)).toBeUndefined();
        });

        it('should return the TextCriteria object with equals operator when value is a string', () => {
            const value = 'testValue';
            expect(asTextCriteria(value)).toStrictEqual({ equals: 'testValue' });
        });

        it('should return the TextCriteria object with contains operator when value is a string starting with %', () => {
            const value = '%testValue';
            expect(asTextCriteria(value)).toStrictEqual({ contains: 'testValue' });
        });

        it('should return the original object when input is an object', () => {
            const value: TextCriteria = { startsWith: 'testValue' };
            expect(asTextCriteria(value)).toStrictEqual({ startsWith: 'testValue' });
        });

        it('should return the object with the correct operator when it is specified', () => {
            const value = 'testValue';
            expect(asTextCriteria(value, 'not')).toStrictEqual({ not: 'testValue' });
        });
    });

    describe('asTextCriteriaOperator', () => {
        it('should return undefined when value undefined', () => {
            expect(asTextCriteriaOperator(undefined)).toBeUndefined();
        });
        it('should return undefined when value null', () => {
            expect(asTextCriteriaOperator(null)).toBeUndefined();
        });
        it('should return operator string for contains', () => {
            const value = { contains: 'test-value' };
            expect(asTextCriteriaOperator(value)).toBe('Contains');
        });
        it('should return operator string for equals', () => {
            const value = { equals: 'test-value' };
            expect(asTextCriteriaOperator(value)).toBe('Equals');
        });
        it('should return operator string for not equals', () => {
            const value = { not: 'test-value' };
            expect(asTextCriteriaOperator(value)).toBe('Not equal');
        });
        it('should return operator string for sounds like', () => {
            const value = { soundsLike: 'test-value' };
            expect(asTextCriteriaOperator(value)).toBe('Sounds like');
        });
        it('should return operator string for starts with', () => {
            const value = { startsWith: 'test-value' };
            expect(asTextCriteriaOperator(value)).toBe('Starts with');
        });
    });
});
