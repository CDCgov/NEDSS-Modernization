import { transform } from './PatientInvestigationTransformer';

describe('when the result is empty', () => {
    it('should return an empty array of investigations', () => {
        const result = {
            content: [],
            total: 0,
            number: 0
        };

        const actual = transform(result);

        expect(actual).toHaveLength(0);
    });
});

describe('when the result has content', () => {
    it('should return an array of investigations', () => {
        const result = {
            content: [
                {
                    investigation: '691',
                    startedOn: null,
                    condition: 'condition-691',
                    status: 'status-691',
                    caseStatus: null,
                    jurisdiction: 'jurisdiction-691',
                    event: 'event-691',
                    coInfection: null,
                    notification: null,
                    investigator: 'Nvesti Gator',
                    comparable: false
                },
                {
                    investigation: '829',
                    startedOn: '2023-03-27T00:00:00Z',
                    condition: 'condition-829',
                    status: 'status-829',
                    caseStatus: 'case-status-829',
                    jurisdiction: 'jurisdiction-829',
                    event: 'event-829',
                    coInfection: 'co-infection-829',
                    notification: 'notification-829',
                    investigator: 'investigator-829',
                    comparable: true
                }
            ],
            total: 1,
            number: 0
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    investigation: '691',
                    startedOn: null,
                    condition: 'condition-691',
                    status: 'status-691',
                    caseStatus: null,
                    jurisdiction: 'jurisdiction-691',
                    event: 'event-691',
                    coInfection: null,
                    notification: null,
                    investigator: 'Nvesti Gator',
                    comparable: false
                }),
                expect.objectContaining({
                    investigation: '829',
                    startedOn: new Date('2023-03-27T05:00:00Z'),
                    condition: 'condition-829',
                    status: 'status-829',
                    caseStatus: 'case-status-829',
                    jurisdiction: 'jurisdiction-829',
                    event: 'event-829',
                    coInfection: 'co-infection-829',
                    notification: 'notification-829',
                    investigator: 'investigator-829',
                    comparable: true
                })
            ])
        );
    });
});
