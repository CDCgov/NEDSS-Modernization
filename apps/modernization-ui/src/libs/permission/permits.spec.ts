import { permits } from './permits';

describe('when testing that a permission is permitted', () => {
    it('should pass when the permission is permitted', () => {
        const permitted = permits('l');

        const actual = permitted(['a', 'b', 'c', 'l', 'm', 'o', 'w']);

        expect(actual).toBe(true);
    });

    it('should fail when the permission is not permitted', () => {
        const permitted = permits('l');

        const actual = permitted(['a', 'b', 'c', 'm', 'o', 'w']);

        expect(actual).toBe(false);
    });
});
