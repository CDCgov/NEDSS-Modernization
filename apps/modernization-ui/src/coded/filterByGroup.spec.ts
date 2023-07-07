import exp from 'constants';
import { GroupedCodedValue } from './CodedValue';
import { filterByGroup } from './filterByGroup';

describe('when there are no GroupedCodedValues', () => {
    const items: GroupedCodedValue[] = [];

    it('should reutrn no items', () => {
        const actual = filterByGroup(items, 'any');

        expect(actual).toHaveLength(0);
    });
});

describe('when there are GroupedCodedValues being filtered by group', () => {
    const items = [
        {
            name: 'one',
            value: '1',
            group: 'A'
        },
        {
            name: 'other',
            value: 'Other',
            group: 'B'
        },
        {
            name: 'two',
            value: '2',
            group: 'A'
        },
        {
            name: 'another',
            value: 'Another',
            group: 'B'
        },
        {
            name: 'anteater',
            value: '2201-1',
            group: 'ANI'
        }
    ];

    it('should filter by group', () => {
        const actual = filterByGroup(items, 'B');

        expect(actual).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    name: 'other',
                    value: 'Other',
                    group: 'B'
                }),
                expect.objectContaining({
                    name: 'another',
                    value: 'Another',
                    group: 'B'
                })
            ])
        );
    });

    it('should not return items for a group that is not present', () => {
        const actual = filterByGroup(items, 'NOT');

        expect(actual).toHaveLength(0);
    });

    it('should not return items for a group that is not present', () => {
        const actual = filterByGroup(items, null);

        expect(actual).toHaveLength(0);
    });
});
