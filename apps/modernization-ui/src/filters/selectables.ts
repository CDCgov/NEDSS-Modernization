import {
    DatePeriodOperator,
    DateRangeOperator,
    isExactValueProperty,
    isPartialValueProperty,
    ExactValueOperator,
    PartialValueOperator
} from './operators';
import { Property, ValueProperty } from './properties';

type SelectableSinlgeValueOperator = { name: string; value: PartialValueOperator };
type SelectableExactValueOperator = { name: string; value: ExactValueOperator };

type SelectableValueOperator = SelectableSinlgeValueOperator | SelectableExactValueOperator;

const partialValueOperators: SelectableSinlgeValueOperator[] = [
    { name: 'starts with', value: 'STARTS_WITH' },
    { name: 'contains', value: 'CONTAINS' }
];

const exactValueOperators: SelectableExactValueOperator[] = [
    { name: 'equals', value: 'EQUALS' },
    { name: 'not equal to', value: 'NOT_EQUAL_TO' }
];

const valueOperators: SelectableValueOperator[] = [...partialValueOperators, ...exactValueOperators];

type SelectableDatePeriodOperator = { name: string; value: DatePeriodOperator };
type SelectableDateRangeOperator = { name: string; value: DateRangeOperator };
type SelectableDateOperator = SelectableDatePeriodOperator | SelectableDateRangeOperator;

const dateOperators: SelectableDateOperator[] = [
    { name: 'today', value: 'TODAY' },
    { name: 'between', value: 'BETWEEN' },
    { name: 'last 7 days', value: 'LAST_7_DAYS' },
    { name: 'last 14 days', value: 'LAST_14_DAYS' },
    { name: 'last 30 days', value: 'LAST_30_DAYS' },
    { name: 'more than 30 days', value: 'MORE_THAN_30_DAYS' }
];

type OperatorOption = SelectableValueOperator | SelectableDateOperator;

export type {
    SelectableSinlgeValueOperator,
    SelectableExactValueOperator,
    SelectableValueOperator,
    SelectableDateOperator,
    SelectableDatePeriodOperator,
    SelectableDateRangeOperator,
    OperatorOption
};

const operators: (property?: Property) => OperatorOption[] = (property) => {
    if (property) {
        return property.type === 'value' ? resolveValueOperators(property) : dateOperators;
    }
    return [];
};

const resolveValueOperators = (property: ValueProperty) => (property.exactOnly ? exactValueOperators : valueOperators);

const withValue = (value: string) => (option: OperatorOption) => option.value === value;

const isPartialValueOption = (option?: SelectableValueOperator) => isPartialValueProperty(option?.value);

const isExactValueOption = (option?: SelectableValueOperator) => isExactValueProperty(option?.value);

export {
    valueOperators,
    partialValueOperators as PartialValueOperators,
    exactValueOperators as ExactValueOperators,
    dateOperators,
    withValue,
    operators,
    isExactValueOption,
    isPartialValueOption
};
