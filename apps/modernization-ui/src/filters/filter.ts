import { Selectable } from 'options';

import { DateProperty, ValueProperty } from './properties';
import {
    SelectableDatePeriodOperator,
    SelectableDateRangeOperator,
    SelectableMultiValueOperator,
    SelectableSinlgeValueOperator
} from './selectables';

type MultiValue = { values: string[] };
type SingleValue = { value: string };
type DateRange = { after: string; before: string };

export type { MultiValue, SingleValue, DateRange };

type Identifiable = {
    id: string;
};

type Value = string | Selectable;

type SingleValueFilter = Identifiable & {
    property: ValueProperty;
    operator: SelectableSinlgeValueOperator;
    value: Value;
};

type MultiValueFilter = Identifiable & {
    property: ValueProperty;
    operator: SelectableMultiValueOperator;
    values: Value[];
};

type ValueFilter = SingleValueFilter | MultiValueFilter;

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
    SingleValueFilter,
    MultiValueFilter,
    ValueFilter,
    DatePeriodFilter,
    DateRangeFilter,
    DateFilter,
    Filter
};
