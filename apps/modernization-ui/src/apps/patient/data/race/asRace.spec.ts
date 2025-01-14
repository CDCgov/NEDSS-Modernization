import { asRace } from './asRace';

describe('when mapping a race entry to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const entry = {
            id: 331,
            asOf: '04/13/2017',
            race: { value: 'race-value', name: 'race-name' },
            detailed: []
        };

        const actual = asRace(entry);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the race', () => {
        const entry = {
            id: 331,
            asOf: '04/13/2017',
            race: { value: 'race-value', name: 'race-name' },
            detailed: []
        };

        const actual = asRace(entry);

        expect(actual).toEqual(expect.objectContaining({ race: 'race-value' }));
    });

    it('should include the race details', () => {
        const entry = {
            id: 331,
            asOf: '04/13/2017',
            race: { value: 'race-value', name: 'race-name' },
            detailed: [
                { value: 'detail-one-value', name: 'detail-one-name' },
                { value: 'detail-two-value', name: 'detail-two-name' }
            ]
        };

        const actual = asRace(entry);

        expect(actual).toEqual(
            expect.objectContaining({ detailed: expect.arrayContaining(['detail-one-value', 'detail-two-value']) })
        );
    });

    it('should not map when race is null', () => {
        const entry = {
            id: 331,
            asOf: '04/13/2017',
            race: null,
            detailed: []
        };

        const actual = asRace(entry);

        expect(actual).toBeUndefined();
    });
});
