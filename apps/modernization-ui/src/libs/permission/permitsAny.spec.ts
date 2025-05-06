import { permitsAny } from './permitsAny';

describe('when testing that any permissions are permitted for a user', () => {
    it('should pass when at least one permissions is permitted', () => {
        const permitted = permitsAny('A', 'l', 'o', 'w');

        const actual = permitted(['a', 'b', 'c', 'm', 'o', 'w']);

        expect(actual).toBe(true);
    });

    it('should fail when there are no permissions', () => {
        const permitted = permitsAny('A', 'l', 'o', 'w');

        const actual = permitted([]);

        expect(actual).toBe(false);
    });

    it('should fail when no permissions are permitted', () => {
        const permitted = permitsAny('A', 'l', 'o', 'w');

        const actual = permitted(['d', 'e', 'n', 'y']);

        expect(actual).toBe(false);
    });
});
