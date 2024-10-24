import { Selectable, asSelectable, findByValue } from 'options';

const STARTS_WITH_OPERATOR = asSelectable('S', 'Starts with');
const CONTAINS_OPERATOR = asSelectable('C', 'Contains');
const EQUAL_OPERATOR = asSelectable('E', 'Equal');
const NOT_EQUAL_OPERATOR = asSelectable('N', 'Not equal');
const SOUNDS_LIKE_OPERATOR = asSelectable('L', 'Sounds like');

const operators: Selectable[] = [
    STARTS_WITH_OPERATOR,
    CONTAINS_OPERATOR,
    EQUAL_OPERATOR,
    NOT_EQUAL_OPERATOR,
    SOUNDS_LIKE_OPERATOR
];

/** A subset of operators: equals, not equals, contains */
const basicOperators: Selectable[] = [EQUAL_OPERATOR, NOT_EQUAL_OPERATOR, CONTAINS_OPERATOR];

/** The default operator to be used. Currently: EQUAL */
const defaultOperator = EQUAL_OPERATOR;

const asSelectableOperator = (value: string | null | undefined) =>
    (value && findByValue(operators, EQUAL_OPERATOR)(value)) || EQUAL_OPERATOR;

export { operators, basicOperators, defaultOperator, asSelectableOperator };
