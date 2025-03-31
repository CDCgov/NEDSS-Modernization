import { not } from './not';

describe('not', () => {
    it('should return false when the given predicate returns true', () => {
        const predicate = jest.fn(() => true);

        const actual = not(predicate)(7);

        expect(actual).toBe(false);

        expect(predicate).toBeCalledWith(7);
    });

    it('should return true when the given predicate returns false', () => {
        const predicate = jest.fn(() => false);

        const actual = not(predicate)(7);

        expect(actual).toBe(true);

        expect(predicate).toBeCalledWith(7);
    });
});
