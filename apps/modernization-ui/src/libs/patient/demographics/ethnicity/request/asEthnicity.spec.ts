import { asEthnicity } from './asEthnicity';

describe('when mapping a ethnicity demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            asOf: '04/13/2017',
            ethnicGroup: { value: 'ethnicity-value', name: 'ethnicity-name' },
            detailed: []
        };

        const actual = asEthnicity(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the ethnicity', () => {
        const demographic = {
            asOf: '04/13/2017',
            ethnicGroup: { value: 'ethnicity-value', name: 'ethnicity-name' },
            detailed: []
        };

        const actual = asEthnicity(demographic);

        expect(actual).toEqual(expect.objectContaining({ ethnicGroup: 'ethnicity-value' }));
    });

    it('should include the unknown reason', () => {
        const demographic = {
            asOf: '04/13/2017',
            ethnicGroup: { value: 'ethnicity-value', name: 'ethnicity-name' },
            detailed: [],
            unknownReason: { value: 'reason-value', name: 'reason-name' }
        };

        const actual = asEthnicity(demographic);

        expect(actual).toEqual(expect.objectContaining({ unknownReason: 'reason-value' }));
    });

    it('should include the ethnicity details', () => {
        const demographic = {
            asOf: '04/13/2017',
            ethnicGroup: { value: 'ethnicity-value', name: 'ethnicity-name' },
            detailed: [
                { value: 'detail-one-value', name: 'detail-one-name' },
                { value: 'detail-two-value', name: 'detail-two-name' }
            ]
        };

        const actual = asEthnicity(demographic);

        expect(actual).toEqual(
            expect.objectContaining({ detailed: expect.arrayContaining(['detail-one-value', 'detail-two-value']) })
        );
    });

    it('should not map when ethnicity is undefined', () => {
        const demographic = {
            asOf: '04/13/2017',
            ethnicGroup: undefined,
            detailed: [
                { value: 'detail-one-value', name: 'detail-one-name' },
                { value: 'detail-two-value', name: 'detail-two-name' }
            ]
        };

        const actual = asEthnicity(demographic);

        expect(actual).toBeUndefined();
    });

    it('should not map when ethnicity is null', () => {
        const demographic = {
            asOf: '04/13/2017',
            detailed: [
                { value: 'detail-one-value', name: 'detail-one-name' },
                { value: 'detail-two-value', name: 'detail-two-name' }
            ]
        };

        const actual = asEthnicity(demographic);

        expect(actual).toBeUndefined();
    });
});
