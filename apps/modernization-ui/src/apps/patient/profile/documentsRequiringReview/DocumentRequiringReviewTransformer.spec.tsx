import { transform } from './DocumentRequiringReviewTransformer';
import { DocumentRequiringReview } from './DocumentsRequiringReview';

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
    it('should return new object of documents', () => {
        const result = {
            content: [
                {
                    dateReceived: '2023-10-07T00:00:00Z',
                    descriptions: [{ title: 'title', value: 'value' }],
                    facilityProviders: {
                        orderingProvider: { name: 'ordering provider' },
                        reportingFacility: { name: 'reporting facility' },
                        sendingFacility: { name: 'sending facility' }
                    },
                    id: '123',
                    isElectronic: true,
                    localId: '123',
                    type: 'type'
                }
            ],
            total: 1,
            number: 0
        };

        const actual = transform(result);

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    id: '123',
                    dateReceived: new Date('2023-10-07T00:00:00Z'),
                    facilityProviders: {
                        orderingProvider: { name: 'ordering provider' },
                        reportingFacility: { name: 'reporting facility' },
                        sendingFacility: { name: 'sending facility' }
                    },
                    descriptions: [{title: 'title', value: 'value'}],
                    isElectronic: true,
                    localId: '123',
                    type: 'type'
                })
            ])
        );
    });
});
