import { anyOf } from './anyOf';

describe('anyOf', () => {
    it('should return false when no predicates are given', () => {
        const actual = anyOf()(3);

        expect(actual).toBe(false);
    });

    it('should return false if all predicates fail', () => {
        const first = jest.fn().mockReturnValue(false);
        const second = jest.fn().mockReturnValue(false);
        const third = jest.fn().mockReturnValue(false);
        const fourth = jest.fn().mockReturnValue(false);

        const actual = anyOf<number>(first, second, third, fourth)(6);

        expect(actual).toBe(false);

        expect(first).toBeCalledWith(6);
        expect(second).toBeCalledWith(6);
        expect(third).toBeCalledWith(6);
        expect(fourth).toBeCalledWith(6);
    });

    it('should return true,  when the first predicate succeeds', () => {
        const first = jest.fn().mockReturnValue(false);
        const second = jest.fn().mockReturnValue(true);
        const third = jest.fn().mockReturnValue(true);
        const fourth = jest.fn().mockReturnValue(true);

        const actual = anyOf<number>(first, second, third, fourth, () => true)(6);

        expect(actual).toBe(true);

        expect(first).toBeCalledWith(6);
        expect(second).toBeCalledWith(6);
        expect(third).not.toBeCalled();
        expect(fourth).not.toBeCalled();
    });
});
