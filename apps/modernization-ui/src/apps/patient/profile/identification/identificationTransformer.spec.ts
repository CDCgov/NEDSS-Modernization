import { transform } from './identificationTransformer';

describe('transform function', () => {
    it('should return an empty array when input is null', () => {
        const actual = transform(null);

        expect(actual).toHaveLength(0);
    });

    it('should correctly transform patient identification data', () => {
        const mockInput = {
            identification: {
                content: [
                    {
                        patient: 10000001,
                        sequence: 1,
                        version: 7,
                        asOf: '2023-01-17T00:00:00Z',
                        authority: {
                            id: 'CA',
                            description: 'CA'
                        },
                        value: '234',
                        type: {
                            id: 'AN',
                            description: 'Account number'
                        }
                    }
                ],
                total: 1,
                number: 0,
                size: 10
            }
        };

        const result = transform(mockInput);

        expect(result).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    asOf: new Date('2023-01-17T00:00:00Z'),
                    type: {
                        id: 'AN',
                        description: 'Account number'
                    },
                    authority: {
                        id: 'CA',
                        description: 'CA'
                    },
                    value: '234',
                    patient: 10000001,
                    sequence: 1,
                    version: 7
                })
            ])
        );
    });
});
