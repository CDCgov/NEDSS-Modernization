import {
    DatePeriodOperator,
    DateRangeOperator,
    isMultiValueProperty,
    isSingleValueProperty,
    MultiValueOperator,
    SingleValueOperator
} from './operators';
import { Property } from './properties';

type SelectableSinlgeValueOperator = { name: string; value: SingleValueOperator };
type SelectableMultiValueOperator = { name: string; value: MultiValueOperator };

type SelectableValueOperator = SelectableSinlgeValueOperator | SelectableMultiValueOperator;

const singleValueOperators: SelectableSinlgeValueOperator[] = [
    { name: 'starts with', value: 'STARTS_WITH' },
    { name: 'contains', value: 'CONTAINS' }
];

const multiValueOperators: SelectableMultiValueOperator[] = [
    { name: 'equals', value: 'EQUALS' },
    { name: 'not equal to', value: 'NOT_EQUAL_TO' }
];

const valueOperators: SelectableValueOperator[] = [...singleValueOperators, ...multiValueOperators];

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
    SelectableMultiValueOperator,
    SelectableValueOperator,
    SelectableDateOperator,
    SelectableDatePeriodOperator,
    SelectableDateRangeOperator,
    OperatorOption
};

const operators: (property?: Property) => OperatorOption[] = (property) => {
    if (property) {
        return property.type === 'value' ? valueOperators : dateOperators;
    }

    return [];
};

const withValue = (value: string) => (option: OperatorOption) => option.value === value;

const isSingleValueOption = (option?: SelectableValueOperator) => isSingleValueProperty(option?.value);

const isMultiValueOption = (option?: SelectableValueOperator) => isMultiValueProperty(option?.value);

export {
    valueOperators,
    singleValueOperators,
    multiValueOperators,
    dateOperators,
    withValue,
    operators,
    isMultiValueOption,
    isSingleValueOption
};
