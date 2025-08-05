import { asBirth } from './asBirth';

describe('when mapping a birth demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            asOf: '04/13/2017',
            bornOn: '11/05/2003'
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the born on', () => {
        const demographic = {
            asOf: '04/13/2017',
            bornOn: '11/05/2003'
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ bornOn: '11/05/2003' }));
    });

    it('should include the current sex', () => {
        const demographic = {
            asOf: '04/13/2017',
            sex: { value: 'birth-sex-value', name: 'birth-sex-name' }
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ sex: 'birth-sex-value' }));
    });

    it('should include the multiple birth', () => {
        const demographic = {
            asOf: '04/13/2017',
            multiple: { value: 'multiple-value', name: 'multiple-name' }
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ multiple: 'multiple-value' }));
    });

    it('should include the city', () => {
        const demographic = {
            asOf: '04/13/2017',
            city: 'city-value'
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ city: 'city-value' }));
    });

    it('should include the county', () => {
        const demographic = {
            asOf: '04/13/2017',
            county: { value: 'county-value', name: 'county-name' }
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ county: 'county-value' }));
    });

    it('should include the state', () => {
        const demographic = {
            asOf: '04/13/2017',
            state: { value: 'state-value', name: 'state-name' }
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ state: 'state-value' }));
    });

    it('should include the country', () => {
        const demographic = {
            asOf: '04/13/2017',
            country: { value: 'country-value', name: 'country-name' }
        };

        const actual = asBirth(demographic);

        expect(actual).toEqual(expect.objectContaining({ country: 'country-value' }));
    });

    it('should not map when only as of is present', () => {
        const demographic = {
            asOf: '04/13/2017'
        };

        const actual = asBirth(demographic);

        expect(actual).toBeUndefined();
    });
});
