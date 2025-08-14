import { exists } from './exists';

describe('when evaluating that a value exists', () => {
    it('should resolve that the string value exists', () => {
        const actual = exists('value');

        expect(actual).toBe(true);
    });

    it('should resolve that the number value exists', () => {
        const actual = exists(5);

        expect(actual).toBe(true);
    });

    it('should resolve that the boolean value exists', () => {
        const actual = exists(false);

        expect(actual).toBe(true);
    });

    it('should resolve that the object value exists', () => {
        const actual = exists({ value: null });

        expect(actual).toBe(true);
    });

    it('should resolve that an empty object value does not exist', () => {
        const actual = exists({});

        expect(actual).toBe(false);
    });

    it('should resolve that the undefined value does not exist', () => {
        const actual = exists(undefined);

        expect(actual).toBe(false);
    });

    it('should resolve that the null value does not exist', () => {
        const actual = exists(null);

        expect(actual).toBe(false);
    });

    it('should resolve that an empty string value does not exist', () => {
        const actual = exists('');

        expect(actual).toBe(false);
    });

    it('should resolve that an empty array value does not exists', () => {
        const actual = exists([]);

        expect(actual).toBe(false);
    });
});
