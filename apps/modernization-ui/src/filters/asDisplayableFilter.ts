import { FilterEntry } from './entry/FilterEntry';
import { DateFilter, DateRangeFilter, Filter, ValueFilter } from './filter';
import { isExactValueProperty, isPartialValueProperty } from './operators';
import { DateProperty, Property, ValueProperty } from './properties';
import {
    dateOperators,
    ExactValueOperators,
    SelectableDateRangeOperator,
    PartialValueOperators,
    withValue
} from './selectables';

const asFilter =
    (properties: Property[]) =>
    (entry: FilterEntry): Filter | undefined => {
        const property = properties.find((item) => item.value === entry.property);

        if (property?.type === 'value') {
            return asValue(property, entry);
        } else if (property?.type === 'date') {
            return asDisplayableDate(property, entry);
        }

        return undefined;
    };

const asValue = (property: ValueProperty, entry: FilterEntry): ValueFilter | undefined => {
    if ('value' in entry && isPartialValueProperty(entry.operator)) {
        const operator = PartialValueOperators.find(withValue(entry.operator));
        return (
            operator && {
                id: crypto.randomUUID(),
                property,
                operator,
                value: entry.value
            }
        );
    } else if ('values' in entry && isExactValueProperty(entry.operator)) {
        const operator = ExactValueOperators.find(withValue(entry.operator));
        return (
            operator && {
                id: crypto.randomUUID(),
                property,
                operator,
                values: entry.values
            }
        );
    }

    return undefined;
};

const asDisplayableDate = (property: DateProperty, entry: FilterEntry): DateFilter | undefined => {
    const operator = dateOperators.find(withValue(entry.operator));

    if (!operator) {
        return undefined;
    } else if (operator?.value === 'BETWEEN') {
        return asDateRange(property, operator, entry);
    }

    return {
        id: crypto.randomUUID(),
        property,
        operator
    };
};

const asDateRange = (
    property: DateProperty,
    operator: SelectableDateRangeOperator,
    entry: FilterEntry
): DateRangeFilter | undefined => {
    if ('after' in entry && 'before' in entry) {
        return {
            id: crypto.randomUUID(),
            property,
            operator,
            after: entry.after,
            before: entry.before
        };
    }

    return undefined;
};

const asString = (filter: Filter): string => {
    let value = '';
    if ('value' in filter) {
        value = typeof filter.value === 'string' ? filter.value : filter.value.name;
    } else if ('values' in filter) {
        value = filter.values.map((v) => (typeof v === 'string' ? v : v.name)).join(' OR ');
    } else if ('after' in filter && 'before' in filter) {
        value = `${filter.after} and ${filter.before}`;
    } else {
        throw new Error('Failed to convert filter to string');
    }
    return `${filter.property.name} ${filter.operator.name} ${value}`;
};

export { asFilter, asString };

export type { Filter };
