import { Direction } from 'sorting';
import { sort } from './PatientDocumentSorter';
import { Headers } from './PatientDocuments';

describe('when sorting documents', () => {
    it('should default sorting to by id', () => {
        const documents = [
            {
                document: '1583',
                receivedOn: new Date('2021-10-07T15:01:10Z'),
                type: 'type',
                sendingFacility: 'sending-facility',
                reportedOn: new Date('2021-09-21T17:04:11Z'),
                event: 'event'
            },
            {
                document: '617',
                receivedOn: new Date('2021-10-07T15:01:10Z'),
                type: 'type',
                sendingFacility: 'sending-facility',
                reportedOn: new Date('2021-09-21T17:04:11Z'),
                event: 'event'
            },
            {
                document: '727',
                receivedOn: new Date('2021-10-07T15:01:10Z'),
                type: 'type',
                sendingFacility: 'sending-facility',
                reportedOn: new Date('2021-09-21T17:04:11Z'),
                event: 'event'
            }
        ];

        const actual = sort(documents, {});

        expect(actual).toEqual([
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '1583' })
        ]);
    });
});

describe('when sorting documents by Date received', () => {
    const documents = [
        {
            document: '1583',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'document-type-value',
            sendingFacility: 'sending-facility-value',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event-value'
        },
        {
            document: '727',
            receivedOn: new Date('2023-08-15T00:00:00Z'),
            type: 'document-type-other',
            sendingFacility: 'sending-facility-other',
            reportedOn: new Date('2023-12-15T23:04:00Z'),
            event: 'event-other'
        },
        {
            document: '617',
            receivedOn: new Date('2022-03-05T01:41:47Z'),
            type: 'document-type-other',
            sendingFacility: 'sending-facility-other',
            reportedOn: new Date('2023-12-15T23:04:00Z'),
            event: 'event-other'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Headers.DateReceived, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Headers.DateReceived, type: Direction.Decending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '1583' })
        ]);
    });
});

describe('when sorting documents by Type', () => {
    const documents = [
        {
            document: '1583',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'B',
            sendingFacility: 'sending-facility-value',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event-value'
        },
        {
            document: '727',
            receivedOn: new Date('2023-08-15T00:00:00Z'),
            type: 'C',
            sendingFacility: 'sending-facility-other',
            reportedOn: new Date('2023-12-15T23:04:00Z'),
            event: 'event-other'
        },
        {
            document: '617',
            receivedOn: new Date('2022-03-05T01:41:47Z'),
            type: 'A',
            sendingFacility: 'sending-facility-other',
            reportedOn: new Date('2023-12-15T23:04:00Z'),
            event: 'event-other'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Headers.Type, type: Direction.Ascending });
        expect(actual).toEqual([
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '617' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Headers.Type, type: Direction.Decending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '727' })
        ]);
    });
});

describe('when sorting documents by Sending facility', () => {
    const documents = [
        {
            document: '1583',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'document-type-value',
            sendingFacility: 'B',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event-value'
        },
        {
            document: '727',
            receivedOn: new Date('2023-08-15T00:00:00Z'),
            type: 'document-type-other',
            sendingFacility: 'C',
            reportedOn: new Date('2023-12-15T23:04:00Z'),
            event: 'event-other'
        },
        {
            document: '617',
            receivedOn: new Date('2022-03-05T01:41:47Z'),
            type: 'document-type-other',
            sendingFacility: 'A',
            reportedOn: new Date('2023-12-15T23:04:00Z'),
            event: 'event-other'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Headers.SendingFacility, type: Direction.Ascending });
        expect(actual).toEqual([
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '617' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Headers.SendingFacility, type: Direction.Decending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '727' })
        ]);
    });
});

describe('when sorting documents by Date reported', () => {
    const documents = [
        {
            document: '1583',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'document-type-value',
            sendingFacility: 'sending-facility-value',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event-value'
        },
        {
            document: '727',
            receivedOn: new Date('2023-08-15T00:00:00Z'),
            type: 'document-type-other',
            sendingFacility: 'sending-facility-other',
            reportedOn: new Date('2023-12-15T23:04:00Z'),
            event: 'event-other'
        },
        {
            document: '617',
            receivedOn: new Date('2022-03-05T01:41:47Z'),
            type: 'document-type-other',
            sendingFacility: 'sending-facility-other',
            reportedOn: new Date('2021-07-15T00:00:00Z'),
            event: 'event-other'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Headers.DateReported, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Headers.DateReported, type: Direction.Decending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '617' })
        ]);
    });
});

describe('when sorting documents by Condition', () => {
    const documents = [
        {
            document: '1583',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event',
            condition: 'B'
        },
        {
            document: '727',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event'
        },
        {
            document: '617',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event',
            condition: 'A'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Headers.Condition, type: Direction.Ascending });
        expect(actual).toEqual([
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Headers.Condition, type: Direction.Decending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '727' })
        ]);
    });
});

describe('when sorting documents by Associated with', () => {
    const documents = [
        {
            document: '1583',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event',
            associatedWith: {
                id: 'associatedWith-a',
                local: 'B'
            }
        },
        {
            document: '727',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event'
        },
        {
            document: '617',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'event',
            associatedWith: {
                id: 'associatedWith-a',
                local: 'A'
            }
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Headers.AssociatedWith, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '1583' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Headers.AssociatedWith, type: Direction.Decending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '727' })
        ]);
    });
});

describe('when sorting documents by Event ID', () => {
    const documents = [
        {
            document: '1583',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: '56B'
        },
        {
            document: '727',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'C'
        },
        {
            document: '617',
            receivedOn: new Date('2021-10-07T15:01:10Z'),
            type: 'type',
            sendingFacility: 'sending-facility',
            reportedOn: new Date('2021-09-21T17:04:11Z'),
            event: 'A'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Headers.EventID, type: Direction.Ascending });
        expect(actual).toEqual([
            expect.objectContaining({ document: '1583' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '727' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Headers.EventID, type: Direction.Decending });

        expect(actual).toEqual([
            expect.objectContaining({ document: '727' }),
            expect.objectContaining({ document: '617' }),
            expect.objectContaining({ document: '1583' })
        ]);
    });
});
