import { Selectable } from 'options';

import { DateProperty, ValueProperty } from './properties';
import {
    SelectableDatePeriodOperator,
    SelectableDateRangeOperator,
    SelectableExactValueOperator,
    SelectableSinlgeValueOperator
} from './selectables';

type ExactValue = { values: string[] };
type PartialValue = { value: string };
type DateRange = { after: string; before: string };

export type { ExactValue, PartialValue, DateRange };

type Identifiable = {
    id: string;
};

type Value = string | Selectable;

type PartialValueFilter = Identifiable & {
    property: ValueProperty;
    operator: SelectableSinlgeValueOperator;
    value: Value;
};

type ExactValueFilter = Identifiable & {
    property: ValueProperty;
    operator: SelectableExactValueOperator;
    values: Value[];
};

type ValueFilter = PartialValueFilter | ExactValueFilter;

type DatePeriodFilter = Identifiable & {
    property: DateProperty;
    operator: SelectableDatePeriodOperator;
};

type DateRangeFilter = Identifiable & {
    property: DateProperty;
    operator: SelectableDateRangeOperator;
} & DateRange;

type DateFilter = DatePeriodFilter | DateRangeFilter;

type Filter = ValueFilter | DateFilter;

export type {
    Value,
    PartialValueFilter,
    ExactValueFilter,
    ValueFilter,
    DatePeriodFilter,
    DateRangeFilter,
    DateFilter,
    Filter
};
