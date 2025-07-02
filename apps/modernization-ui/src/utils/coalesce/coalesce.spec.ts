import { coalesce } from './coalesce';

describe('coalesce', () => {
    it('should return the first non undefined or null value of an array', () => {
        const actual = coalesce(undefined, null, 3, 5, 7, 11);

        expect(actual).toBe(3);
    });

    it('should return undefined when only provided with undefined values', () => {
        const actual = coalesce(undefined, undefined, undefined);

        expect(actual).toBeUndefined();
    });

    it('should return undefined when given no values', () => {
        const actual = coalesce();

        expect(actual).toBeUndefined();
    });

    it('should return undefined when only provided with null values', () => {
        const actual = coalesce(null, null, null);

        expect(actual).toBeUndefined();
    });
});
