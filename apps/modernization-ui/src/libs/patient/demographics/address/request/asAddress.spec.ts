import { asAddress } from './asAddress';

describe('when mapping an address demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-address' },
            use: { value: 'use-value', name: 'use-address' }
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the type', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-address' },
            use: { value: 'use-value', name: 'use-address' }
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ type: 'type-value' }));
    });

    it('should include the use', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-address' },
            use: { value: 'use-value', name: 'use-address' }
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ use: 'use-value' }));
    });

    it('should include the address1', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            address1: 'address-1-value'
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ address1: 'address-1-value' }));
    });

    it('should include the address2', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            address2: 'address-2-value'
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ address2: 'address-2-value' }));
    });

    it('should include the city', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            city: 'city-value'
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ city: 'city-value' }));
    });

    it('should include the county', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            county: { value: 'county-value', name: 'county-name' }
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ county: 'county-value' }));
    });

    it('should include the state', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            state: { value: 'state-value', name: 'state-name' }
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ state: 'state-value' }));
    });

    it('should include the zipcode', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            zipcode: 'zipcode-value'
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ zipcode: 'zipcode-value' }));
    });

    it('should include the country', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            country: { value: 'country-value', name: 'country-name' }
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ country: 'country-value' }));
    });

    it('should include the census tract', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            censusTract: 'census-tract-value'
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ censusTract: 'census-tract-value' }));
    });

    it('should include the comment', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            comment: 'comment-value'
        };

        const actual = asAddress(demographic);

        expect(actual).toEqual(expect.objectContaining({ comment: 'comment-value' }));
    });

    it('should not map when type is null', () => {
        const demographic = {
            asOf: '04/13/2017',
            use: { value: 'use-value', name: 'use-value' }
        };

        const actual = asAddress(demographic);

        expect(actual).toBeUndefined();
    });

    it('should not map when use is null', () => {
        const demographic = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-value' }
        };

        const actual = asAddress(demographic);

        expect(actual).toBeUndefined();
    });
});
