import { Selectable, asSelectable } from 'options';

const STARTS_WITH_OPERATOR = asSelectable('startsWith', 'Starts with');
const CONTAINS_OPERATOR = asSelectable('contains', 'Contains');
const EQUAL_OPERATOR = asSelectable('equals', 'Equal');
const NOT_EQUAL_OPERATOR = asSelectable('not', 'Not equal');
const SOUNDS_LIKE_OPERATOR = asSelectable('soundsLike', 'Sounds like');

const textOperators: Selectable[] = [
    STARTS_WITH_OPERATOR,
    CONTAINS_OPERATOR,
    EQUAL_OPERATOR,
    NOT_EQUAL_OPERATOR,
    SOUNDS_LIKE_OPERATOR
];

/** A subset of operators: equals, not equals, contains */
const textAlphaOperators: Selectable[] = [EQUAL_OPERATOR, NOT_EQUAL_OPERATOR, CONTAINS_OPERATOR];

/** The default operator to be used. Currently: EQUAL */
const defaultTextOperator = EQUAL_OPERATOR;

export { textOperators, textAlphaOperators, defaultTextOperator };
