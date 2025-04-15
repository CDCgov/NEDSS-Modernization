import { isEmptyObject } from './isEmptyObject';

describe('when evaluating that a value that is not an empty object', () => {
    it('should resolve that string is not an empty object', () => {
        const actual = isEmptyObject('value');
        expect(actual).toBe(false);
    });
    it('should resolve that number is not an empty object', () => {
        const actual = isEmptyObject(12);
        expect(actual).toBe(false);
    });
    it('should resolve that an object with properties is not an empty object', () => {
        const actual = isEmptyObject({ name: 'John', age: 30 });
        expect(actual).toBe(false);
    });
    it('should resolve that empty array is not an empty object', () => {
        const actual = isEmptyObject([]);
        expect(actual).toBe(false);
    });
    it('should resolve that undefined is not an empty object', () => {
        const actual = isEmptyObject(undefined);
        expect(actual).toBe(false);
    });
    it('should resolve that null is not an empty object', () => {
        const actual = isEmptyObject(null);
        expect(actual).toBe(false);
    });
});

describe('when evaluating that a value that is an empty object', () => {
    it('should return true', () => {
        const actual = isEmptyObject({});

        expect(actual).toBe(true);
    });
});
