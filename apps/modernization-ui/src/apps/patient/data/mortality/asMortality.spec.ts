import { asMortality } from './asMortality';

describe('when mapping a mortality entry to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const entry = {
            asOf: '04/13/2017',
            deceasedOn: '10/08/1927'
        };

        const actual = asMortality(entry);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the deceased on', () => {
        const entry = {
            asOf: '04/13/2017',
            deceasedOn: '10/08/1927'
        };

        const actual = asMortality(entry);

        expect(actual).toEqual(expect.objectContaining({ deceasedOn: '10/08/1927' }));
    });

    it('should include the city', () => {
        const entry = {
            asOf: '04/13/2017',
            city: 'city-value'
        };

        const actual = asMortality(entry);

        expect(actual).toEqual(expect.objectContaining({ city: 'city-value' }));
    });

    it('should include the county', () => {
        const entry = {
            asOf: '04/13/2017',
            county: { value: 'county-value', name: 'county-name' }
        };

        const actual = asMortality(entry);

        expect(actual).toEqual(expect.objectContaining({ county: 'county-value' }));
    });

    it('should include the state', () => {
        const entry = {
            asOf: '04/13/2017',
            state: { value: 'state-value', name: 'state-name' }
        };

        const actual = asMortality(entry);

        expect(actual).toEqual(expect.objectContaining({ state: 'state-value' }));
    });

    it('should include the country', () => {
        const entry = {
            asOf: '04/13/2017',
            country: { value: 'country-value', name: 'country-name' }
        };

        const actual = asMortality(entry);

        expect(actual).toEqual(expect.objectContaining({ country: 'country-value' }));
    });

    it('should not map when only as of is present', () => {
        const entry = {
            asOf: '04/13/2017'
        };

        const actual = asMortality(entry);

        expect(actual).toBeUndefined();
    });
});
