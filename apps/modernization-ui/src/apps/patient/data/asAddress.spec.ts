import { asAddress } from './asAddress';

describe('when mapping a address entry to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-address' },
            use: { value: 'use-value', name: 'use-address' }
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the type', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-address' },
            use: { value: 'use-value', name: 'use-address' }
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ type: 'type-value' }));
    });

    it('should include the use', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-address' },
            use: { value: 'use-value', name: 'use-address' }
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ use: 'use-value' }));
    });

    it('should include the address1', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            address1: 'address-1-value'
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ address1: 'address-1-value' }));
    });

    it('should include the address2', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            address2: 'address-2-value'
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ address2: 'address-2-value' }));
    });

    it('should include the city', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            city: 'city-value'
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ city: 'city-value' }));
    });

    it('should include the county', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            county: { value: 'county-value', name: 'county-name' }
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ county: 'county-value' }));
    });

    it('should include the state', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            state: { value: 'state-value', name: 'state-name' }
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ state: 'state-value' }));
    });

    it('should include the zicode', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            zicode: 'zicode-value'
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ zicode: 'zicode-value' }));
    });

    it('should include the country', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            country: { value: 'country-value', name: 'country-name' }
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ country: 'country-value' }));
    });

    it('should include the census tract', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-name' },
            use: { value: 'use-value', name: 'use-name' },
            censusTract: 'census-tract-value'
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ censusTract: 'census-tract-value' }));
    });

    it('should include the comment', () => {
        const entry = {
            asOf: '04/13/2017',
            type: { value: 'type-value', name: 'type-phone-email' },
            use: { value: 'use-value', name: 'use-phone-email' },
            comment: 'comment-value'
        };

        const actual = asAddress(entry);

        expect(actual).toEqual(expect.objectContaining({ comment: 'comment-value' }));
    });
});
