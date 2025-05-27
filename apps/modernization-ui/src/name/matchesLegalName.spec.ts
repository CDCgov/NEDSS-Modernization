import { matchesLegalName } from './matchesLegalName';

describe('matchesLegalName', () => {
    it.each([
        {
            first: 'John',
            middle: 'W',
            last: 'Wick',
            suffix: 'III'
        },
        {
            first: 'John',
            middle: 'W',
            last: 'Wick'
        },
        {
            first: 'John',
            middle: 'W'
        },
        {
            first: 'John'
        },
        {
            first: 'John',

            last: 'Wick'
        }
    ])('should return true when names match exactly', (value) => {
        expect(matchesLegalName(value, value)).toBe(true);
    });

    it('should return false if legalName is null or undefined', () => {
        const name = { first: 'John', last: 'Wick' };
        expect(matchesLegalName(name, null)).toBe(false);
        expect(matchesLegalName(name, undefined)).toBe(false);
    });

    it('should return false if first names differ', () => {
        const name = {
            first: 'Nick',
            middle: 'W',
            last: 'Wick',
            suffix: 'III'
        };
        const legal = {
            first: 'John',
            middle: 'W',
            last: 'Wick',
            suffix: 'III'
        };
        expect(matchesLegalName(name, legal)).toBe(false);
    });

    it('should return false if last names differ', () => {
        const name = {
            first: 'John',
            middle: 'W',
            last: 'Wick',
            suffix: 'III'
        };
        const legal = {
            first: 'John',
            middle: 'W',
            last: 'Constantine',
            suffix: 'III'
        };
        expect(matchesLegalName(name, legal)).toBe(false);
    });
});
