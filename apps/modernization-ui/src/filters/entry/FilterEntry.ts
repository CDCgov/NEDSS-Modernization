import { MultiValue, SingleValue, DateRange } from 'filters';
import { DatePeriodOperator, DateRangeOperator, MultiValueOperator, SingleValueOperator } from 'filters/operators';

type BaseFilterEntry = {
    property: string;
};

type MultiValueEntry = BaseFilterEntry & {
    operator: MultiValueOperator;
} & MultiValue;

type SingleValueEntry = BaseFilterEntry & {
    operator: SingleValueOperator;
} & SingleValue;

type ValueEntry = SingleValueEntry | MultiValueEntry;

type DatePeriodFilterEntry = BaseFilterEntry & {
    operator: DatePeriodOperator;
};

type DateRangeFilterEntry = BaseFilterEntry & { operator: DateRangeOperator } & DateRange;

type DateFilterEntry = DatePeriodFilterEntry | DateRangeFilterEntry;

type FilterEntry = ValueEntry | DateFilterEntry;

export type {
    FilterEntry,
    ValueEntry,
    SingleValueEntry,
    MultiValueEntry,
    DateFilterEntry,
    DatePeriodFilterEntry,
    DateRangeFilterEntry
};
