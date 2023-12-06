import { Direction } from 'sorting';
import { Column } from './identification';
import { sort } from './identificationSorter';

describe('sort function', () => {
    const mockIdentifications = [
        {
            patient: 10000001,
            sequence: 1,
            version: 7,
            asOf: new Date('2015-09-19T13:00:00.000Z'),
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
    ];

    it('should sort by asOf date in ascending order', () => {
        const sorted = sort(mockIdentifications, { name: Column.AsOf, type: Direction.Ascending });
        expect(sorted).toEqual(mockIdentifications);
    });

    it('should sort by type in descending order', () => {
        const sorted = sort(mockIdentifications, { name: Column.Type, type: Direction.Descending });
        expect(sorted).toEqual(mockIdentifications);
    });

    it('should sort by value in descending order', () => {
        const sorted = sort(mockIdentifications, { name: Column.Value, type: Direction.Ascending });
        expect(sorted).toEqual(mockIdentifications);
    });
});
