import type { Filter as APIFilter } from 'apps/page-builder/generated/models/Filter';
import { externalizeDate } from 'date';
import {
    DatePeriodFilter,
    DateRange,
    DateRangeFilter,
    Filter,
    MultiValue,
    MultiValueFilter,
    SingleValue,
    SingleValueFilter,
    Value
} from './filter';
import { DatePeriodOperator, MultiValueOperator, SingleValueOperator } from './operators';

type Base = {
    property: string;
};

type ExternalDatePeriodFilter = {
    operator: DatePeriodOperator;
    from?: Date | null;
} & Base;

type ExternalDateRangeFilter = Base & DateRange;

type ExternalSingleValueFilter = {
    operator: SingleValueOperator;
} & Base &
    SingleValue;

type ExternalMultiValueFilter = {
    operator: MultiValueOperator;
} & Base &
    MultiValue;

const externalize = (displayables: Filter[]): APIFilter[] => displayables.map(asFilter);

const asFilter = (displayable: Filter): APIFilter => {
    if ('value' in displayable) {
        return asSingleValueFilter(displayable);
    } else if ('values' in displayable) {
        return asMultiValueFilter(displayable);
    } else if ('after' in displayable && 'before' in displayable) {
        return asDateRangeFilter(displayable);
    }

    return asDatePeriodFilter(displayable);
};

const asSingleValue = (value: Value): string => (typeof value === 'string' ? value : value.value);

const asSingleValueFilter = (displayable: SingleValueFilter): ExternalSingleValueFilter => ({
    property: displayable.property.value,
    operator: displayable.operator.value,
    value: asSingleValue(displayable.value)
});

const asMultiValue = (values: Value[]): string[] => values.map(asSingleValue);

const asMultiValueFilter = (displayable: MultiValueFilter): ExternalMultiValueFilter => ({
    property: displayable.property.value,
    operator: displayable.operator.value,
    values: asMultiValue(displayable.values)
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
