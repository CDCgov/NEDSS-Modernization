type ExactValueOperator = 'EQUALS' | 'NOT_EQUAL_TO';
type PartialValueOperator = 'STARTS_WITH' | 'CONTAINS';
type ValueOperator = PartialValueOperator | ExactValueOperator;

type DatePeriodOperator = 'TODAY' | 'LAST_7_DAYS' | 'LAST_14_DAYS' | 'LAST_30_DAYS' | 'MORE_THAN_30_DAYS';
type DateRangeOperator = 'BETWEEN';
type DateOperator = DatePeriodOperator | DateRangeOperator;

export type {
    ValueOperator,
    DateOperator,
    DatePeriodOperator,
    DateRangeOperator,
    PartialValueOperator,
    ExactValueOperator
};

const isPartialValueProperty = (operator?: ValueOperator) =>
    (operator && (operator === 'STARTS_WITH' || operator === 'CONTAINS')) || false;

const isExactValueProperty = (operator?: ValueOperator) =>
    (operator && (operator === 'EQUALS' || operator === 'NOT_EQUAL_TO')) || false;

export { isPartialValueProperty, isExactValueProperty };
