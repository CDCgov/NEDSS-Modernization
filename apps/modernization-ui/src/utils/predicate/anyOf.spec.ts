import { vi } from 'vitest';
import { anyOf } from './anyOf';

describe('anyOf', () => {
    it('should return false when no predicates are given', () => {
        const actual = anyOf()(3);

        expect(actual).toBe(false);
    });

    it('should return false if all predicates fail', () => {
        const first = vi.fn().mockReturnValue(false);
        const second = vi.fn().mockReturnValue(false);
        const third = vi.fn().mockReturnValue(false);
        const fourth = vi.fn().mockReturnValue(false);

        const actual = anyOf<number>(first, second, third, fourth)(6);

        expect(actual).toBe(false);

        expect(first).toBeCalledWith(6);
        expect(second).toBeCalledWith(6);
        expect(third).toBeCalledWith(6);
        expect(fourth).toBeCalledWith(6);
    });

    it('should return true,  when the first predicate succeeds', () => {
        const first = vi.fn().mockReturnValue(false);
        const second = vi.fn().mockReturnValue(true);
        const third = vi.fn().mockReturnValue(true);
        const fourth = vi.fn().mockReturnValue(true);

        const actual = anyOf<number>(first, second, third, fourth, () => true)(6);

        expect(actual).toBe(true);

        expect(first).toBeCalledWith(6);
        expect(second).toBeCalledWith(6);
        expect(third).not.toBeCalled();
        expect(fourth).not.toBeCalled();
    });
});
