import { DateFilter, DateRangeFilter, MultiValueFilter, SingleValueFilter } from 'filters';
import { externalize } from './externalize';

describe('when a filter is externalized', () => {
    it('should send a single value filter to the API', () => {
        const filter: SingleValueFilter = {
            id: 'single-value-identifier',
            property: { value: 'single-value', name: 'Single Value', type: 'value' },
            operator: { name: 'Starts with', value: 'STARTS_WITH' },
            value: 'prefix-value'
        };

        const actual = externalize([filter]);

        expect(actual).toEqual([
            expect.objectContaining({
                operator: 'STARTS_WITH',
                property: 'single-value',
                value: 'prefix-value'
            })
        ]);
    });

    it('should send a multi value filter to the API', () => {
        const filter: MultiValueFilter = {
            id: 'multi-value-identifier',
            property: { value: 'multi-value', name: 'Multi Value', type: 'value' },
            operator: { name: 'equals', value: 'EQUALS' },
            values: ['value-one', 'value-two']
        };

        const actual = externalize([filter]);

        expect(actual).toEqual([
            expect.objectContaining({
                operator: 'EQUALS',
                property: 'multi-value',
                values: ['value-one', 'value-two']
            })
        ]);
    });

    it('should send a date filter to the API', () => {
        const filter: DateFilter = {
            id: 'date-identifier',
            property: { value: 'date-period', name: 'Date Period', type: 'date' },
            operator: { name: 'today', value: 'TODAY' }
        };

        const actual = externalize([filter]);

        expect(actual).toEqual([
            expect.objectContaining({
                property: 'date-period',
                operator: 'TODAY'
            })
        ]);
    });

    it('should send a date range filter to the API', () => {
        const filter: DateRangeFilter = {
            id: 'date-range-identifier',
            property: { value: 'date-range', name: 'Date Range', type: 'date' },
            operator: { name: 'between', value: 'BETWEEN' },
            after: '12/01/2023',
            before: '12/29/2023'
        };

        const actual = externalize([filter]);

        expect(actual).toEqual([
            expect.objectContaining({
                property: 'date-range',
                after: '2023-12-01',
                before: '2023-12-29'
            })
        ]);
    });
});
