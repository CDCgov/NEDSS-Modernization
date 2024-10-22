import { displayName } from './displayName';

describe('when given a name', () => {
    const name = {
        first: 'Bill',
        middle: 'S',
        last: 'Preston',
        suffix: 'Esquire'
    };

    it('should display the short name', () => {
        const actual = displayName('short')(name);

        expect(actual).toBe('Bill Preston');
    });

    it('should display the full name', () => {
        const actual = displayName('full')(name);

        expect(actual).toBe('Bill S Preston, Esquire');
    });

    it('should display the last name, first name middle name, suffix', () => {
        const actual = displayName('fullLastFirst')(name);

        expect(actual).toBe('Preston, Bill S, Esquire');
    });

    it('should default to the displaying the full name', () => {
        const actual = displayName()(name);

        expect(actual).toBe('Bill S Preston, Esquire');
    });
});

describe('when given a partial name', () => {
    it('should display the short name with first name only', () => {
        const name = {
            first: 'Bill',
            middle: 'S',
            suffix: 'Esquire'
        };

        const actual = displayName('short')(name);

        expect(actual).toBe('Bill');
    });

    it('should display the short name with last name only', () => {
        const name = {
            middle: 'S',
            last: 'Preston',
            suffix: 'Esquire'
        };

        const actual = displayName('short')(name);

        expect(actual).toBe('Preston');
    });

    it('should display the full name without a suffix', () => {
        const name = {
            first: 'Ted',
            middle: 'Theodore',
            last: 'Logan'
        };

        const actual = displayName('full')(name);

        expect(actual).toBe('Ted Theodore Logan');
    });

    it('should display the full name without a suffix in a last, first middle format', () => {
        const name = {
            first: 'Ted',
            middle: 'Theodore',
            last: 'Logan'
        };

        const actual = displayName('fullLastFirst')(name);

        expect(actual).toBe('Logan, Ted Theodore');
    });

    it('should display the full name without a suffix or middle in a last, first format', () => {
        const name = {
            first: 'Ted',
            last: 'Logan'
        };

        const actual = displayName('fullLastFirst')(name);

        expect(actual).toBe('Logan, Ted');
    });

    it('should display the first name with fullLastFirst format', () => {
        const name = {
            first: 'Ted'
        };

        const actual = displayName('fullLastFirst')(name);

        expect(actual).toBe('--, Ted');
    });

    it('should display the last name with fullLastFirst format', () => {
        const name = {
            last: 'Logan'
        };

        const actual = displayName('fullLastFirst')(name);

        expect(actual).toBe('Logan, --');
    });
});
