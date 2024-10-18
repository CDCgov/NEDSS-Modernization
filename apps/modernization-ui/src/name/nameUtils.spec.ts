import { matchesLegalName } from './nameUtils';
import { DisplayableName } from './types';

describe('matchesLegalName', () => {
    const legalName: DisplayableName = {
        first: 'John',
        middle: 'W',
        last: 'Wick',
        suffix: 'III'
    };

    it('should return false if legalName is null or undefined', () => {
        const name: DisplayableName = { first: 'John', last: 'Wick' };
        expect(matchesLegalName(name, null)).toBe(false);
        expect(matchesLegalName(name, undefined)).toBe(false);
    });

    it('should return true for matching first and last names in short format', () => {
        const name: DisplayableName = { first: 'John', last: 'Wick' };
        expect(matchesLegalName(name, legalName, 'short')).toBe(true);
    });

    it('should return false for non-matching first and last names in short format', () => {
        const name: DisplayableName = { first: 'Jane', last: 'Wick' };
        expect(matchesLegalName(name, legalName, 'short')).toBe(false);
    });

    it('should return true for matching full names in full format', () => {
        const name: DisplayableName = { first: 'John', middle: 'W', last: 'Wick', suffix: 'III' };
        expect(matchesLegalName(name, legalName, 'full')).toBe(true);
    });

    it('should return false for non-matching full names in full format', () => {
        const name: DisplayableName = { first: 'John', middle: 'A', last: 'Wick', suffix: 'III' };
        expect(matchesLegalName(name, legalName, 'full')).toBe(false);
    });

    it('should return true for matching full names in fullLastFirst format', () => {
        const name: DisplayableName = { first: 'John', middle: 'W', last: 'Wick', suffix: 'III' };
        expect(matchesLegalName(name, legalName, 'fullLastFirst')).toBe(true);
    });

    it('should return false for non-matching full names in fullLastFirst format', () => {
        const name: DisplayableName = { first: 'John', middle: 'A', last: 'Wick', suffix: 'III' };
        expect(matchesLegalName(name, legalName, 'fullLastFirst')).toBe(false);
    });

    it('should default to short format if no format is provided', () => {
        const name: DisplayableName = { first: 'John', last: 'Wick' };
        expect(matchesLegalName(name, legalName)).toBe(true);
    });
});
