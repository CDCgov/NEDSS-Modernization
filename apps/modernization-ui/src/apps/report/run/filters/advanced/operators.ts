import { Operator } from 'react-querybuilder';

export type NbsOperator = Operator & { nbsCd: string };

export const EQ_OPERATORS: NbsOperator[] = [
    {
        name: '=',
        nbsCd: 'EQ',
        label: 'Equals',
        arity: 'binary',
    },
    {
        name: '!=',
        nbsCd: 'NE',
        label: 'Not Equals',
        arity: 'binary',
    },
];

export const LIST_OPERATORS: NbsOperator[] = [
    {
        name: 'in',
        nbsCd: 'EQ',
        label: 'Equals',
        arity: 'binary',
    },
    {
        name: 'notIn',
        nbsCd: 'NE',
        label: 'Not Equals',
        arity: 'binary',
    },
];

export const NULL_OPERATORS: NbsOperator[] = [
    {
        name: 'null',
        nbsCd: 'IN',
        label: 'Is Null',
        arity: 'unary',
    },
    {
        name: 'notNull',
        nbsCd: 'NN',
        label: 'Is Not Null',
        arity: 'unary',
    },
];

export const STRING_OPERATORS: NbsOperator[] = [
    {
        name: 'contains',
        nbsCd: 'CO',
        label: 'Contains',
        arity: 'binary',
    },
    {
        name: 'beginswith',
        nbsCd: 'SW',
        label: 'Starts With',
        arity: 'binary',
    },
];

export const BETWEEN_OPERATOR: NbsOperator = {
    name: 'between',
    nbsCd: 'BW',
    label: 'Between',
    arity: 'ternary',
};

export const NUMERIC_OPERATORS: NbsOperator[] = [
    ...BETWEEN_OPERATOR,
    {
        name: '<',
        nbsCd: 'LT',
        label: 'Less Than',
        arity: 'binary',
    },
    {
        name: '>',
        nbsCd: 'GT',
        label: 'Greater Than',
        arity: 'binary',
    },

    {
        name: '<=',
        nbsCd: 'LE',
        label: 'Less Or Equal',
        arity: 'binary',
    },

    {
        name: '>=',
        nbsCd: 'GE',
        label: 'Greater Or Equal',
        arity: 'binary',
    },
];

export const ALL_OPERATORS = [
    ...EQ_OPERATORS,
    ...NULL_OPERATORS,
    ...LIST_OPERATORS,
    ...STRING_OPERATORS,
    ...NUMERIC_OPERATORS,
];

export const BINARY_OPERATORS = ALL_OPERATORS.filter(({ arity }) => arity === 'binary').map(({ name }) => name);

export const OPERATOR_MAP: Record<string, NbsOperator[]> = {
    STRING: [...EQ_OPERATORS, ...NULL_OPERATORS, ...STRING_OPERATORS],
    INTEGER: [...EQ_OPERATORS, ...NULL_OPERATORS, ...NUMERIC_OPERATORS],
    NUMBER: [...EQ_OPERATORS, ...NULL_OPERATORS, ...NUMERIC_OPERATORS],
    DATETIME: [...EQ_OPERATORS, ...NULL_OPERATORS, ...NUMERIC_OPERATORS],
    CODED: [...LIST_OPERATORS, ...NULL_OPERATORS],
};
