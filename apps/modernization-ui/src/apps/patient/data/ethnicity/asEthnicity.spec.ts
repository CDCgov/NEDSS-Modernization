import { asEthnicity } from './asEthnicity';

describe('when mapping a ethnicity entry to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const entry = {
            asOf: '04/13/2017',
            ethnicity: { value: 'ethnicity-value', name: 'ethnicity-name' },
            detailed: []
        };

        const actual = asEthnicity(entry);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the ethnicity', () => {
        const entry = {
            asOf: '04/13/2017',
            ethnicity: { value: 'ethnicity-value', name: 'ethnicity-name' },
            detailed: []
        };

        const actual = asEthnicity(entry);

        expect(actual).toEqual(expect.objectContaining({ ethnicity: 'ethnicity-value' }));
    });

    it('should include the ethnicity details', () => {
        const entry = {
            asOf: '04/13/2017',
            ethnicity: { value: 'ethnicity-value', name: 'ethnicity-name' },
            detailed: [
                { value: 'detail-one-value', name: 'detail-one-name' },
                { value: 'detail-two-value', name: 'detail-two-name' }
            ]
        };

        const actual = asEthnicity(entry);

        expect(actual).toEqual(
            expect.objectContaining({ detailed: expect.arrayContaining(['detail-one-value', 'detail-two-value']) })
        );
    });
});
