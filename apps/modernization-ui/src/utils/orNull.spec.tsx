import { orNull } from './orNull';

describe('orNull tests', () => {
    it('an undefiend object returns null', () => {
        const actual = orNull(undefined);
        expect(actual).toEqual(null);
    });

    it('a null object returns null', () => {
        const actual = orNull(null);
        expect(actual).toEqual(null);
    });

    it('an empty string returns null', () => {
        const actual = orNull('');
        expect(actual).toEqual(null);
    });

    it('a string is returned', () => {
        const actual = orNull('e');
        expect(actual).toEqual('e');
    });

    it('a number is returned', () => {
        const actual = orNull(1);
        expect(actual).toEqual(1);
    });

    it('0 returns 0', () => {
        const actual = orNull(0);
        expect(actual).toEqual(0);
    });

    it('an object is returned', () => {
        const actual = orNull({});
        expect(actual).toEqual({});
    });
});
