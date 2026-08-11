import { allOf } from './allOf';

describe('allOf', () => {
    it('should return true when no predicates are given', () => {
        const actual = allOf()(3);

        expect(actual).toBe(false);
    });

    it('should return true if all predicates pass', () => {
        const first = vi.fn().mockReturnValue(true);
        const second = vi.fn().mockReturnValue(true);
        const third = vi.fn().mockReturnValue(true);
        const fourth = vi.fn().mockReturnValue(true);

        const actual = allOf<number>(first, second, third, fourth, () => true)(6);

        expect(actual).toBe(true);

        expect(first).toHaveBeenCalledWith(6);
        expect(second).toHaveBeenCalledWith(6);
        expect(third).toHaveBeenCalledWith(6);
        expect(fourth).toHaveBeenCalledWith(6);
    });

    it('should return false, failing fast,  if any predicates fails', () => {
        const first = vi.fn().mockReturnValue(true);
        const second = vi.fn().mockReturnValue(false);
        const third = vi.fn().mockReturnValue(true);
        const fourth = vi.fn().mockReturnValue(true);

        const actual = allOf<number>(first, second, third, fourth, () => true)(6);

        expect(actual).toBe(false);

        expect(first).toHaveBeenCalledWith(6);
        expect(second).toHaveBeenCalledWith(6);
        expect(third).not.toHaveBeenCalled();
        expect(fourth).not.toHaveBeenCalled();
    });
});
