import { transform } from './PatientVaccinationTransformer';

describe('when the result is empty', () => {
    it('should return an empty array of documents', () => {
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
    it('should return an array of tracing', () => {
        const result = {
            content: [
                {
                    vaccination: '1583',
                    createdOn: '2023-03-17T20:08:45Z',
                    provider: 'provider-one',
                    administeredOn: '2023-01-17T00:00:00Z',
                    administered: 'administered-one',
                    event: 'event-one',
                    associatedWith: {
                        id: 'associated-id',
                        local: 'associated-local',
                        condition: 'associated-condition'
                    }
                },
                {
                    vaccination: '727',
                    createdOn: '2022-11-07T20:07:15Z',
                    administered: 'administered-other',
                    event: 'event-other'
                }
            ],
            total: 1,
            number: 0
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    vaccination: '1583',
                    createdOn: new Date('2023-03-17T20:08:45Z'),
                    provider: 'provider-one',
                    administeredOn: new Date('2023-01-17T05:00:00Z'),
                    administered: 'administered-one',
                    event: 'event-one',
                    associatedWith: {
                        id: 'associated-id',
                        local: 'associated-local',
                        condition: 'associated-condition'
                    }
                }),
                expect.objectContaining({
                    vaccination: '727',
                    createdOn: new Date('2022-11-07T20:07:15Z'),
                    provider: null,
                    administeredOn: null,
                    administered: 'administered-other',
                    event: 'event-other'
                })
            ])
        );
    });
});
