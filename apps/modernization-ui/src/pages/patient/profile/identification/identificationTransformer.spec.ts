import { transform } from './identificationTransformer';

describe('transform function', () => {
    it('should return an empty array when input is null', () => {
        const result = transform(null);
        expect(result).toEqual([]);
    });

    it('should correctly transform patient identification data', () => {
        const mockInput = {
            identification: {
                content: [
                    {
                        patient: 10000001,
                        sequence: 1,
                        version: 7,
                        asOf: '2015-09-19T18:30:00.000Z',
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
                total: 3,
                number: 0,
                size: 10
            }
        };

        const result = transform(mockInput);
        const resultWithIsoDates = result.map((item) => ({
            ...item,
            asOf: item.asOf.toISOString()
        }));

        const expectedOutput = [
            {
                asOf: new Date('2015-09-19T23:30:00.000Z').toISOString(),
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
            }
        ];

        expect(resultWithIsoDates).toEqual(expectedOutput);
    });
});
