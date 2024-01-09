import { transform } from './PatientDocumentTransformer';

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
    it('should return an array of documents', () => {
        const result = {
            content: [
                {
                    document: '1583',
                    receivedOn: '2021-10-07T15:01:10Z',
                    type: 'document-type-value',
                    sendingFacility: 'sending-facility-value',
                    reportedOn: '2021-09-21T17:04:11Z',
                    condition: 'condition-value',
                    event: 'event-value',
                    associatedWith: {
                        id: 'associated-id',
                        local: 'associated-local-value',
                        condition: 'associated-condition-value'
                    }
                },
                {
                    document: '727',
                    receivedOn: '2022-03-05T01:41:47Z',
                    type: 'document-type-other',
                    sendingFacility: 'sending-facility-other',
                    reportedOn: '2023-12-15T23:04:00Z',
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
                    document: '1583',
                    receivedOn: new Date('2021-10-07T15:01:10Z'),
                    type: 'document-type-value',
                    sendingFacility: 'sending-facility-value',
                    reportedOn: new Date('2021-09-21T17:04:11Z'),
                    condition: 'condition-value',
                    event: 'event-value',
                    associatedWith: {
                        id: 'associated-id',
                        local: 'associated-local-value',
                        condition: 'associated-condition-value'
                    }
                }),
                expect.objectContaining({
                    document: '727',
                    receivedOn: new Date('2022-03-05T01:41:47Z'),
                    type: 'document-type-other',
                    sendingFacility: 'sending-facility-other',
                    reportedOn: new Date('2023-12-15T23:04:00Z'),
                    event: 'event-other'
                })
            ])
        );
    });
});
