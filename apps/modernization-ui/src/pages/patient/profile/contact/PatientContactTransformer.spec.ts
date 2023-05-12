import { transform } from './PatientContactTransformer';

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
                    contactRecord: '1583',
                    createdOn: '2023-03-17T20:08:45Z',
                    condition: {
                        id: 'condition-id-one',
                        description: 'condition-description-one'
                    },
                    contact: {
                        id: 'patient-one-id',
                        name: 'patient-one-name'
                    },
                    namedOn: '2023-01-17T00:00:00Z',
                    event: 'event-one',
                    associatedWith: {
                        id: 'associated-id',
                        local: 'associated-local',
                        condition: 'associated-condition'
                    }
                },
                {
                    contactRecord: '727',
                    createdOn: '2022-11-07T20:07:15Z',
                    condition: {
                        id: 'condition-id-two',
                        description: 'condition-description-two'
                    },
                    contact: {
                        id: 'patient-other-id',
                        name: 'patient-other-name'
                    },
                    namedOn: '2018-10-27T00:00:00Z',
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
                    contactRecord: '1583',
                    createdOn: new Date('2023-03-17T20:08:45Z'),
                    condition: {
                        id: 'condition-id-one',
                        description: 'condition-description-one'
                    },
                    contact: {
                        id: 'patient-one-id',
                        name: 'patient-one-name'
                    },
                    namedOn: new Date('2023-01-17T00:00:00Z'),
                    event: 'event-one',
                    associatedWith: {
                        id: 'associated-id',
                        local: 'associated-local',
                        condition: 'associated-condition'
                    }
                }),
                expect.objectContaining({
                    contactRecord: '727',
                    createdOn: new Date('2022-11-07T20:07:15Z'),
                    condition: {
                        id: 'condition-id-two',
                        description: 'condition-description-two'
                    },
                    contact: {
                        id: 'patient-other-id',
                        name: 'patient-other-name'
                    },
                    namedOn: new Date('2018-10-27T00:00:00Z'),
                    event: 'event-other'
                })
            ])
        );
    });
});
