import { allOf } from './allOf';

describe('allOf', () => {
    it('should return true no predicates are given', () => {
        const actual = allOf()(3);

        expect(actual).toBe(true);
    });

    it('should return true if all predicates pass', () => {
        const first = jest.fn().mockReturnValue(true);
        const second = jest.fn().mockReturnValue(true);
        const third = jest.fn().mockReturnValue(true);
        const fourth = jest.fn().mockReturnValue(true);

        const actual = allOf<number>(first, second, third, fourth, () => true)(6);

        expect(actual).toBe(true);

        expect(first).toBeCalledWith(6);
        expect(second).toBeCalledWith(6);
        expect(third).toBeCalledWith(6);
        expect(fourth).toBeCalledWith(6);
    });

    it('should return false, failing fast,  if any predicates fails', () => {
        const first = jest.fn().mockReturnValue(true);
        const second = jest.fn().mockReturnValue(false);
        const third = jest.fn().mockReturnValue(true);
        const fourth = jest.fn().mockReturnValue(true);

        const actual = allOf<number>(first, second, third, fourth, () => true)(6);

        expect(actual).toBe(false);

        expect(first).toBeCalledWith(6);
        expect(second).toBeCalledWith(6);
        expect(third).not.toBeCalled();
        expect(fourth).not.toBeCalled();
    });
});
