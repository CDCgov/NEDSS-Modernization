import { transform } from './NameTransformer';

describe('transform function', () => {
    it('should return an empty array when input is null', () => {
        const actual = transform(null);
        expect(actual).toHaveLength(0);
    });

    it('should correctly transform patient name data', () => {
        const mockInput = {
            names: {
                content: [
                    {
                        patient: 10014282,
                        sequence: 20,
                        version: 7,
                        asOf: '2023-01-17T00:00:00Z',
                        prefix: {
                            id: 'BSHP',
                            description: 'Bishop'
                        },
                        use: {
                            id: 'AD',
                            description: 'Adopted Name'
                        },
                        first: 'Mike',
                        middle: 'John',
                        secondMiddle: 'David',
                        last: 'Smith',
                        secondLast: 'Sarah',
                        suffix: {
                            id: 'ESQ',
                            description: 'Esquire'
                        },
                        degree: {
                            id: 'PHD',
                            description: 'Doctor of Philosophy'
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
                    patient: 10014282,
                    sequence: 20,
                    version: 7,
                    asOf: new Date('2023-01-17T00:00:00Z'),
                    prefix: {
                        id: 'BSHP',
                        description: 'Bishop'
                    },
                    use: {
                        id: 'AD',
                        description: 'Adopted Name'
                    },
                    first: 'Mike',
                    middle: 'John',
                    secondMiddle: 'David',
                    last: 'Smith',
                    secondLast: 'Sarah',
                    suffix: {
                        id: 'ESQ',
                        description: 'Esquire'
                    },
                    degree: {
                        id: 'PHD',
                        description: 'Doctor of Philosophy'
                    }
                })
            ])
        );
    });
});
