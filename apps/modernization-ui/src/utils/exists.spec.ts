import { exists } from './exists';

describe('when given a actual value', () => {
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
        const actual = exists({});

        expect(actual).toBe(true);
    });

    it('should resolve that the array value exists', () => {
        const actual = exists([]);

        expect(actual).toBe(true);
    });
});

describe('when not given a value', () => {
    it('should resolve that the value does not exist', () => {
        const actual = exists();

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
});
