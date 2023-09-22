import { PatientNameResults } from 'generated/graphql/schema';
import { sortingByDate } from './sortingByDate'; // Adjust the import path as needed

describe('sortingByDate function', () => {
    it('should sort PatientNameResults data by date in descending order', () => {
        const data: PatientNameResults['content'] = [
            {
                patient: 10000001,
                version: 82,
                asOf: '2023-07-28T00:00:00Z',
                sequence: 3,
                use: {
                    id: 'L',
                    description: 'Legal',
                    __typename: 'PatientNameUse'
                },
                prefix: null,
                first: 'Surma',
                middle: 'J',
                secondMiddle: null,
                last: 'Singh',
                secondLast: null,
                suffix: {
                    id: 'III',
                    description: 'III / The Third',
                    __typename: 'PatientNameSuffix'
                },
                degree: null,
                __typename: 'PatientName'
            },
            {
                patient: 10000001,
                version: 82,
                asOf: '2023-09-20T18:30:00Z',
                sequence: 5,
                use: {
                    id: 'M',
                    description: 'Maiden Name',
                    __typename: 'PatientNameUse'
                },
                prefix: {
                    id: 'CARD',
                    description: 'Cardinal',
                    __typename: 'PatientNamePrefix'
                },
                first: 'Test',
                middle: null,
                secondMiddle: null,
                last: null,
                secondLast: null,
                suffix: null,
                degree: null,
                __typename: 'PatientName'
            }
        ];

        const sortedData = sortingByDate(data);

        for (let i = 0; i < sortedData.length - 1; i++) {
            const dateA = new Date(sortedData[i].asOf).getTime();
            const dateB = new Date(sortedData[i + 1].asOf).getTime();
            expect(dateA).toBeGreaterThanOrEqual(dateB);
        }
    });
});
