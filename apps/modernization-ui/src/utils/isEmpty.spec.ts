import { isEmpty } from './isEmpty';

describe('when evaluating if a value is empty', () => {
    it('should resolve that an empty object containing a property not is empty', () => {
        const actual = isEmpty({ property: true });

        expect(actual).toBe(false);
    });

    it('should resolve that an empty object containing a non empty object not is empty', () => {
        const actual = isEmpty({ inner: { inner: true } });

        expect(actual).toBe(false);
    });

    it('should resolve that an empty object containing an empty object is empty', () => {
        const actual = isEmpty({ inner: {} });

        expect(actual).toBe(true);
    });

    it('should resolve that an empty object is empty', () => {
        const actual = isEmpty({});

        expect(actual).toBe(true);
    });

    it('should resolve that an array is containing items is not empty', () => {
        const actual = isEmpty([1, 2, 'e']);

        expect(actual).toBe(false);
    });

    it('should resolve that an empty array is empty', () => {
        const actual = isEmpty([]);

        expect(actual).toBe(true);
    });
});
