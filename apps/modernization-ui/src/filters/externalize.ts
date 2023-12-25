import type { Filter as APIFilter } from 'apps/page-builder/generated/models/Filter';
import { externalizeDate } from 'date';
import {
    DatePeriodFilter,
    DateRange,
    DateRangeFilter,
    Filter,
    ExactValue,
    ExactValueFilter,
    PartialValue,
    PartialValueFilter,
    Value
} from './filter';
import { DatePeriodOperator, ExactValueOperator, PartialValueOperator } from './operators';

type Base = {
    property: string;
};

type ExternalDatePeriodFilter = {
    operator: DatePeriodOperator;
    from?: Date | null;
} & Base;

type ExternalDateRangeFilter = Base & DateRange;

type ExternalPartialValueFilter = {
    operator: PartialValueOperator;
} & Base &
    PartialValue;

type ExternalExactValueFilter = {
    operator: ExactValueOperator;
} & Base &
    ExactValue;

const externalize = (displayables: Filter[]): APIFilter[] => displayables.map(asFilter);

const asFilter = (displayable: Filter): APIFilter => {
    if ('value' in displayable) {
        return asPartialValueFilter(displayable);
    } else if ('values' in displayable) {
        return asExactValueFilter(displayable);
    } else if ('after' in displayable && 'before' in displayable) {
        return asDateRangeFilter(displayable);
    }

    return asDatePeriodFilter(displayable);
};

const asPartialValue = (value: Value): string => (typeof value === 'string' ? value : value.value);

const asPartialValueFilter = (displayable: PartialValueFilter): ExternalPartialValueFilter => ({
    property: displayable.property.value,
    operator: displayable.operator.value,
    value: asPartialValue(displayable.value)
});

const asExactValue = (values: Value[]): string[] => values.map(asPartialValue);

const asExactValueFilter = (displayable: ExactValueFilter): ExternalExactValueFilter => ({
    property: displayable.property.value,
    operator: displayable.operator.value,
    values: asExactValue(displayable.values)
});

const asDatePeriodFilter = (displayable: DatePeriodFilter): ExternalDatePeriodFilter => ({
    property: displayable.property.value,
    operator: displayable.operator.value,
    from: null
});

const asDateRangeFilter = (displayable: DateRangeFilter): ExternalDateRangeFilter => {
    const range = asDateRange(displayable);

    return {
        property: displayable.property.value,
        ...range
    };
};

const asDateRange = (displayable: DateRangeFilter): DateRange => ({
    after: externalizeDate(displayable.after),
    before: externalizeDate(displayable.before)
});

export { externalize };
