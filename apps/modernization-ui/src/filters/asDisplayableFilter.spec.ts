import { DatePeriodFilterEntry, DateRangeFilterEntry, ExactValueEntry, PartialValueEntry } from './entry/FilterEntry';
import { Property } from './properties';
import { asFilter } from './asDisplayableFilter';

Object.defineProperty(globalThis, 'crypto', {
    value: {
        randomUUID: () => '69b09298-08e5-43f5-a15d-a37810448b3d'
    }
});

describe('when a filter is submitted', () => {
    it('should create a single value filter from the entry', () => {
        const properties: Property[] = [{ value: 'single-value', name: 'Single Value', type: 'value' }];

        const entry: PartialValueEntry = {
            property: 'single-value',
            operator: 'STARTS_WITH',
            value: 'prefix-value'
        };

        const filter = asFilter(properties)(entry);

        expect(filter).toEqual(
            expect.objectContaining({
                id: '69b09298-08e5-43f5-a15d-a37810448b3d',
                operator: expect.objectContaining({ value: 'STARTS_WITH' }),
                value: 'prefix-value'
            })
        );
    });

    it('should create a multi value filter from the entry', () => {
        const properties: Property[] = [{ value: 'multi-value', name: 'Multi Value', type: 'value' }];

        const entry: ExactValueEntry = {
            property: 'multi-value',
            operator: 'EQUALS',
            values: ['value-one', 'value-two']
        };

        const filter = asFilter(properties)(entry);

        expect(filter).toEqual(
            expect.objectContaining({
                id: '69b09298-08e5-43f5-a15d-a37810448b3d',
                operator: expect.objectContaining({ value: 'EQUALS' }),
                values: expect.arrayContaining(['value-one', 'value-two'])
            })
        );
    });

    it('should create a date filter from the entry', () => {
        const properties: Property[] = [{ value: 'date', name: 'Date', type: 'date' }];

        const entry: DatePeriodFilterEntry = {
            property: 'date',
            operator: 'TODAY'
        };

        const filter = asFilter(properties)(entry);

        expect(filter).toEqual(
            expect.objectContaining({
                id: '69b09298-08e5-43f5-a15d-a37810448b3d',
                operator: expect.objectContaining({ value: 'TODAY' })
            })
        );
    });

    it('should create a date range filter from the entry', () => {
        const properties: Property[] = [{ value: 'date-range', name: 'Date Range', type: 'date' }];

        const entry: DateRangeFilterEntry = {
            property: 'date-range',
            operator: 'BETWEEN',
            after: '11/01/2023',
            before: '11/30/2023'
        };

        const filter = asFilter(properties)(entry);

        expect(filter).toEqual(
            expect.objectContaining({
                id: '69b09298-08e5-43f5-a15d-a37810448b3d',
                operator: expect.objectContaining({ value: 'BETWEEN' }),
                after: '11/01/2023',
                before: '11/30/2023'
            })
        );
    });
});
