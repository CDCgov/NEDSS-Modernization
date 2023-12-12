import { FilterEntry } from './entry/FilterEntry';
import { DateFilter, DateRangeFilter, Filter, ValueFilter } from './filter';
import { isMultiValueProperty, isSingleValueProperty } from './operators';
import { DateProperty, Property, ValueProperty } from './properties';
import {
    dateOperators,
    multiValueOperators,
    SelectableDateRangeOperator,
    singleValueOperators,
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
    if ('value' in entry && isSingleValueProperty(entry.operator)) {
        const operator = singleValueOperators.find(withValue(entry.operator));
        return (
            operator && {
                id: crypto.randomUUID(),
                property,
                operator,
                value: entry.value
            }
        );
    } else if ('values' in entry && isMultiValueProperty(entry.operator)) {
        const operator = multiValueOperators.find(withValue(entry.operator));
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

export { asFilter };

export type { Filter };
