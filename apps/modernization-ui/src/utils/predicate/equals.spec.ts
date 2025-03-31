import { equals } from './equals';

describe('equals', () => {
    it.each([2, 'string', false])('should return true when the values are equal', (value) => {
        const actual = equals(value)(value);

        expect(actual).toBe(true);
    });

    it.each([
        [2, 3],
        ['string', 'other'],
        [false, true]
    ])('should return false when the values are not equal', (criteria, value) => {
        const actual = equals(criteria)(value);

        expect(actual).toBe(false);
    });
});
