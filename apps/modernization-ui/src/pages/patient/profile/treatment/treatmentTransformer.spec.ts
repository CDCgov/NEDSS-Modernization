import { transform } from './treatmentTransformer';

describe('transform function', () => {
    it('should return an empty array when input is null', () => {
        const actual = transform(null);

        expect(actual).toHaveLength(0);
    });

    it('should correctly transform patient identification data', () => {
        const mockInput = {
            content: [
                {
                    treatment: '10055291',
                    createdOn: '2023-01-17T00:00:00Z',
                    provider: 'Supervisor StateUser',
                    treatedOn: '2023-01-17T05:00:00Z',
                    description: ' Amoxicillin, 50 mg/kg/day, x 14-28',
                    event: 'TRT10000000GA01',
                    associatedWith: {
                        id: '10055290',
                        local: 'CAS10001000GA01',
                        condition: 'Bacterial Vaginosis'
                    }
                }
            ],
            number: 0,
            total: 3
        };

        const result = transform(mockInput);

        expect(result).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    treatment: '10055291',
                    createdOn: new Date('2023-01-17T05:00:00Z'),
                    provider: 'Supervisor StateUser',
                    treatedOn: '2023-01-17T05:00:00Z',
                    description: ' Amoxicillin, 50 mg/kg/day, x 14-28',
                    event: 'TRT10000000GA01',
                    associatedWith: {
                        id: '10055290',
                        local: 'CAS10001000GA01',
                        condition: 'Bacterial Vaginosis'
                    }
                })
            ])
        );
    });
});
