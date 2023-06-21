import { maybeNumber } from './maybeNumber';

describe('given a number is passed', () => {
    it('then return the number', () => {
        const actual = maybeNumber(17);

        expect(actual).toEqual(17);
    });
});

describe('given a non number', () => {
    it('and the value is null then return null', () => {
        const actual = maybeNumber(null);

        expect(actual).toBeNull();
    });

    it('and the value is undefined then return null', () => {
        const actual = maybeNumber(undefined);

        expect(actual).toBeNull();
    });

    it('and the value is an empty string then return null', () => {
        const actual = maybeNumber('');

        expect(actual).toBeNull();
    });

    it('and the value is a string representation of a number then return the number', () => {
        const actual = maybeNumber('39');

        expect(actual).toEqual(39);
    });
});
