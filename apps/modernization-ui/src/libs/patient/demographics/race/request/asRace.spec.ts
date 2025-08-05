import { asRace } from './asRace';

describe('when mapping a race demographic to a format accepted by the API', () => {
    it('should include the as of date', () => {
        const demographic = {
            id: 331,
            asOf: '04/13/2017',
            race: { value: 'race-value', name: 'race-name' },
            detailed: []
        };

        const actual = asRace(demographic);

        expect(actual).toEqual(expect.objectContaining({ asOf: '04/13/2017' }));
    });

    it('should include the race', () => {
        const demographic = {
            id: 331,
            asOf: '04/13/2017',
            race: { value: 'race-value', name: 'race-name' },
            detailed: []
        };

        const actual = asRace(demographic);

        expect(actual).toEqual(expect.objectContaining({ race: 'race-value' }));
    });

    it('should include the race details', () => {
        const demographic = {
            id: 331,
            asOf: '04/13/2017',
            race: { value: 'race-value', name: 'race-name' },
            detailed: [
                { value: 'detail-one-value', name: 'detail-one-name' },
                { value: 'detail-two-value', name: 'detail-two-name' }
            ]
        };

        const actual = asRace(demographic);

        expect(actual).toEqual(
            expect.objectContaining({ detailed: expect.arrayContaining(['detail-one-value', 'detail-two-value']) })
        );
    });

    it('should not map when race is null', () => {
        const demographic = {
            id: 331,
            asOf: '04/13/2017',

            detailed: []
        };

        const actual = asRace(demographic);

        expect(actual).toBeUndefined();
    });
});
