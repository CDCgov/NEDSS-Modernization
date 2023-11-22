type MultiValueOperator = 'EQUALS' | 'NOT_EQUAL_TO';
type SingleValueOperator = 'STARTS_WITH' | 'CONTAINS';
type ValueOperator = SingleValueOperator | MultiValueOperator;

type DatePeriodOperator = 'TODAY' | 'LAST_7_DAYS' | 'LAST_14_DAYS' | 'LAST_30_DAYS' | 'MORE_THAN_30_DAYS';
type DateRangeOperator = 'BETWEEN';
type DateOperator = DatePeriodOperator | DateRangeOperator;

export type {
    ValueOperator,
    DateOperator,
    DatePeriodOperator,
    DateRangeOperator,
    SingleValueOperator,
    MultiValueOperator
};

const isSingleValueProperty = (operator?: ValueOperator) =>
    (operator && (operator === 'STARTS_WITH' || operator === 'CONTAINS')) || false;

const isMultiValueProperty = (operator?: ValueOperator) =>
    (operator && (operator === 'EQUALS' || operator === 'NOT_EQUAL_TO')) || false;

export { isSingleValueProperty, isMultiValueProperty };
