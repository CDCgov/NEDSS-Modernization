import { mapIf } from './mapIf';

describe('when mapping data with mapIf', () => {
    it('should return mapped items that satisfy the predicate', () => {
        const items = ['a', 'b', 'c'];

        const actual = mapIf(
            (item) => item.toUpperCase(),
            (item) => item === 'b',
            items
        );

        expect(actual).toEqual(expect.arrayContaining(['B']));
    });

    it('should return empty when no items satisfy the predicate', () => {
        const items = ['a', 'b', 'c'];

        const actual = mapIf(
            (item) => item.toUpperCase(),
            (item) => item === 'X',
            items
        );

        expect(actual).toHaveLength(0);
    });
});
