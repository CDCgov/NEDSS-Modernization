import { Direction } from 'sorting';
import { sort } from './DocumentRequiringReviewSorter';
import { Columns, DocumentReview } from './ReviewDocuments';

describe('when sorting documents', () => {
    it('it should default sort on date received', () => {
        const documents: DocumentReview[] = [
            {
                dateReceived: new Date('2023-10-07T00:00:00Z'),
                type: 'type',
                id: '123',
                isElectronic: true,
                localId: '123',
                facilityProviders: {
                    orderingProvider: undefined,
                    reportingFacility: undefined,
                    sendingFacility: undefined
                },
                eventDate: undefined,
                descriptions: [{ title: undefined, value: undefined }]
            },
            {
                dateReceived: new Date('2021-10-07T00:00:00Z'),
                type: 'type',
                id: '234',
                facilityProviders: {
                    orderingProvider: undefined,
                    reportingFacility: undefined,
                    sendingFacility: undefined
                },
                eventDate: undefined,
                descriptions: [{ title: undefined, value: undefined }],
                isElectronic: true,
                localId: '234'
            },
            {
                dateReceived: new Date('2022-02-06T00:00:00Z'),
                type: 'type',
                id: '345',
                facilityProviders: {
                    orderingProvider: undefined,
                    reportingFacility: undefined,
                    sendingFacility: undefined
                },
                eventDate: undefined,
                descriptions: [{ title: undefined, value: undefined }],
                isElectronic: true,
                localId: '345'
            }
        ];

        const actual = sort(documents, {});

        expect(actual).toEqual([
            expect.objectContaining({ id: '123' }),
            expect.objectContaining({ id: '345' }),
            expect.objectContaining({ id: '234' })
        ]);
    });
});

describe('when sorting document require review with Date received', () => {
    const documents: DocumentReview[] = [
        {
            dateReceived: new Date('2023-10-07T00:00:00Z'),
            type: 'type',
            id: '123',
            isElectronic: true,
            localId: '123',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: undefined,
            descriptions: [{ title: undefined, value: undefined }]
        },
        {
            dateReceived: new Date('2021-10-07T00:00:00Z'),
            type: 'type',
            id: '234',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: undefined,
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '234'
        },
        {
            dateReceived: new Date('2022-02-06T00:00:00Z'),
            type: 'type',
            id: '345',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: undefined,
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '345'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Columns.DateReceived, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ id: '234' }),
            expect.objectContaining({ id: '345' }),
            expect.objectContaining({ id: '123' })
        ]);
    });
});

describe('sorting with type', () => {
    const documents: DocumentReview[] = [
        {
            dateReceived: new Date('2023-10-07T00:00:00Z'),
            type: 'A',
            id: '123',
            isElectronic: true,
            localId: '123',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: undefined,
            descriptions: [{ title: undefined, value: undefined }]
        },
        {
            dateReceived: new Date('2021-10-07T00:00:00Z'),
            type: 'C',
            id: '234',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: undefined,
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '234'
        },
        {
            dateReceived: new Date('2022-02-06T00:00:00Z'),
            type: 'B',
            id: '345',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: undefined,
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '345'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Columns.DocumentType, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ id: '123' }),
            expect.objectContaining({ id: '345' }),
            expect.objectContaining({ id: '234' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Columns.DocumentType, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ id: '234' }),
            expect.objectContaining({ id: '345' }),
            expect.objectContaining({ id: '123' })
        ]);
    });
});

describe('sorting with event date', () => {
    const documents: DocumentReview[] = [
        {
            dateReceived: new Date('2023-10-07T00:00:00Z'),
            type: 'A',
            id: '123',
            isElectronic: true,
            localId: '123',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: new Date('2023-10-07T00:00:00Z'),
            descriptions: [{ title: undefined, value: undefined }]
        },
        {
            dateReceived: new Date('2021-10-07T00:00:00Z'),
            type: 'C',
            id: '234',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: new Date('2021-10-07T00:00:00Z'),
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '234'
        },
        {
            dateReceived: new Date('2022-02-06T00:00:00Z'),
            type: 'B',
            id: '345',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: new Date('2022-02-06T00:00:00Z'),
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '345'
        }
    ];

    it('should sort ascending', () => {
        const actual = sort(documents, { name: Columns.EventDate, type: Direction.Ascending });

        expect(actual).toEqual([
            expect.objectContaining({ id: '234' }),
            expect.objectContaining({ id: '345' }),
            expect.objectContaining({ id: '123' })
        ]);
    });

    it('should sort descending', () => {
        const actual = sort(documents, { name: Columns.EventDate, type: Direction.Descending });

        expect(actual).toEqual([
            expect.objectContaining({ id: '123' }),
            expect.objectContaining({ id: '345' }),
            expect.objectContaining({ id: '234' })
        ]);
    });
});

describe('sort by event id', () => {
    const documents: DocumentReview[] = [
        {
            dateReceived: new Date('2023-10-07T00:00:00Z'),
            type: 'A',
            id: '123',
            isElectronic: true,
            localId: '123',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: new Date('2023-10-07T00:00:00Z'),
            descriptions: [{ title: undefined, value: undefined }]
        },
        {
            dateReceived: new Date('2021-10-07T00:00:00Z'),
            type: 'C',
            id: '234',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: new Date('2021-10-07T00:00:00Z'),
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '234'
        },
        {
            dateReceived: new Date('2022-02-06T00:00:00Z'),
            type: 'B',
            id: '345',
            facilityProviders: {
                orderingProvider: undefined,
                reportingFacility: undefined,
                sendingFacility: undefined
            },
            eventDate: new Date('2022-02-06T00:00:00Z'),
            descriptions: [{ title: undefined, value: undefined }],
            isElectronic: true,
            localId: '345'
        }
    ];

    it('should sort ascending', () => {
      const actual = sort(documents, { name: Columns.EventID, type: Direction.Ascending });

      expect(actual).toEqual([
          expect.objectContaining({ id: '123' }),
          expect.objectContaining({ id: '234' }),
          expect.objectContaining({ id: '345' })
      ]);
  });

  it('should sort descending', () => {
      const actual = sort(documents, { name: Columns.EventID, type: Direction.Descending });

      expect(actual).toEqual([
          expect.objectContaining({ id: '345' }),
          expect.objectContaining({ id: '234' }),
          expect.objectContaining({ id: '123' })
      ]);
  });
});
