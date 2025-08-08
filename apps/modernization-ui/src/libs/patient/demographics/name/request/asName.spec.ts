import { asName } from './asName';

describe('when mapping a name demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = { asOf: '04/13/2017', type: { value: 'type-value', name: 'type-name' } };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the type', () => {
        const demographic = { asOf: '04/13/2017', type: { value: 'type-value', name: 'type-name' } };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ type: 'type-value' }));
    });

    it('should include the prefix', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            prefix: { value: 'prefix-value', name: 'prefix-name' }
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ prefix: 'prefix-value' }));
    });

    it('should include the first name', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            first: 'first-value'
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ first: 'first-value' }));
    });

    it('should include the middle name', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            middle: 'middle-value'
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ middle: 'middle-value' }));
    });

    it('should include the second middle name', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            secondMiddle: 'second-middle-value'
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ secondMiddle: 'second-middle-value' }));
    });

    it('should include the last name', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            last: 'last-value'
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ last: 'last-value' }));
    });

    it('should include the second last name', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            secondLast: 'second-last-value'
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ secondLast: 'second-last-value' }));
    });

    it('should include the suffix', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            suffix: { value: 'suffix-value', name: 'suffix-name' }
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ suffix: 'suffix-value' }));
    });

    it('should include the degree', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            degree: { value: 'degree-value', name: 'degree-name' }
        };

        const actual = asName(demographic);

        expect(actual).toEqual(expect.objectContaining({ degree: 'degree-value' }));
    });

    it('should not map when type is null', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: null
        };

        const actual = asName(demographic);

        expect(actual).toBeUndefined();
    });
});
