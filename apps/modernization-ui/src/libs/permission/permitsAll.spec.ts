import { permitsAll } from './permitsAll';

describe('when testing that all permissions are permitted for a user', () => {
    it('should pass when all permissions are permitted', () => {
        const permitted = permitsAll('A', 'l', 'o', 'w');

        const actual = permitted(['a', 'b', 'c', 'l', 'm', 'o', 'w']);

        expect(actual).toBe(true);
    });

    it('should fail when at least one permissions is not permitted', () => {
        const permitted = permitsAll('A', 'l', 'o', 'w');

        const actual = permitted(['a', 'b', 'c', 'm', 'o', 'w']);

        expect(actual).toBe(false);
    });

    it('should fail when there are no permissions', () => {
        const permitted = permitsAll('A', 'l', 'o', 'w');

        const actual = permitted([]);

        expect(actual).toBe(false);
    });

    it('should fail when no permissions are permitted', () => {
        const permitted = permitsAll('A', 'l', 'o', 'w');

        const actual = permitted(['d', 'e', 'n', 'y']);

        expect(actual).toBe(false);
    });
});
