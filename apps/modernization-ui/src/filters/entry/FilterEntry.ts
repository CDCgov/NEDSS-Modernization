import { ExactValue, PartialValue, DateRange } from 'filters';
import { DatePeriodOperator, DateRangeOperator, ExactValueOperator, PartialValueOperator } from 'filters/operators';

type BaseFilterEntry = {
    property: string;
};

type ExactValueEntry = BaseFilterEntry & {
    operator: ExactValueOperator;
} & ExactValue;

type PartialValueEntry = BaseFilterEntry & {
    operator: PartialValueOperator;
} & PartialValue;

type ValueEntry = PartialValueEntry | ExactValueEntry;

type DatePeriodFilterEntry = BaseFilterEntry & {
    operator: DatePeriodOperator;
};

type DateRangeFilterEntry = BaseFilterEntry & { operator: DateRangeOperator } & DateRange;

type DateFilterEntry = DatePeriodFilterEntry | DateRangeFilterEntry;

type FilterEntry = ValueEntry | DateFilterEntry;

export type {
    FilterEntry,
    ValueEntry,
    PartialValueEntry,
    ExactValueEntry,
    DateFilterEntry,
    DatePeriodFilterEntry,
    DateRangeFilterEntry
};
