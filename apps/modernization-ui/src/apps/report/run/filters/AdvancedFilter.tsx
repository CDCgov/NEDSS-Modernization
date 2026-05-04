/* eslint-disable no-redeclare */
import { AdvancedFilterConfiguration, ReportColumn, Rule, RuleGroup } from 'generated';
import { useController } from 'react-hook-form';
import QueryBuilder, {
    Field,
    formatQuery,
    Operator,
    QueryValidator,
    RuleGroupType,
    RuleType,
    ValidationMap,
} from 'react-querybuilder';
import 'react-querybuilder/dist/query-builder.css';
import { ReportExecuteForm } from '../ReportRunPage';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';

type NbsOperator = Operator & { nbsCd: string };

const BASE_OPERATORS: NbsOperator[] = [
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

const STRING_OPERATORS: NbsOperator[] = [
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

const NUMERIC_OPERATORS: NbsOperator[] = [
    {
        name: 'between',
        nbsCd: 'BW',
        label: 'Between',
        arity: 'ternary',
    },
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

const ALL_OPERATORS = [...BASE_OPERATORS, ...STRING_OPERATORS, ...NUMERIC_OPERATORS];

const OPERATOR_MAP: Record<string, Operator[]> = {
    STRING: [...BASE_OPERATORS, ...STRING_OPERATORS],
    INTEGER: [...BASE_OPERATORS, ...NUMERIC_OPERATORS],
    NUMBER: [...BASE_OPERATORS, ...NUMERIC_OPERATORS],
    DATETIME: [...BASE_OPERATORS, ...NUMERIC_OPERATORS],
};

const INPUT_TYPE_MAP: Record<string, string> = {
    STRING: 'text',
    INTEGER: 'number',
    NUMBER: 'number',
    DATETIME: 'date',
};

type Expr = RuleGroup | Rule;

const mapToQueryOp = (op: string) => ALL_OPERATORS.find(({ nbsCd }) => nbsCd === op)!.name;
const mapToNbsOp = (op: string) => ALL_OPERATORS.find(({ name }) => name === op)!.nbsCd;

// translate operator and remove any extraneous fields
function translateOperators(rule: Expr, mapper: (op: string) => string): Expr {
    if ('rules' in rule) {
        const { combinator, rules } = rule;
        return { combinator, rules: rules.map((r) => translateOperators(r, mapper)) };
    }

    const { operator, field, value } = rule;
    return { field, value: value.toString(), operator: mapper(operator) };
}

// typescript is tricky to appease here, hence the casts, but the code does work as intended
const queryToAdvancedFilterRequest = (query: RuleGroupType): RuleGroup =>
    translateOperators(query as RuleGroup, mapToNbsOp) as RuleGroup;

const validateAdvancedFilter = (value?: RuleGroup) => {
    if (!value) return true;

    // todo: validation
    return Object.values(validator(value))
        .filter((v) => !v.valid)
        .reduce((acc, cur) => `${acc}\n${cur.reasons[0]}`, '');
};

const validator: QueryValidator = (q) => {
    const result: ValidationMap = {};
    q.rules.forEach((r) => validateRule(r, result));
    return result;
};

const validateRule = (rule: RuleGroupType | RuleType, result: ValidationMap) => {
    if ('operator' in rule) {
        // default valid
        result[rule.id] = { valid: true };

        // check for exceptions
        if (rule.operator === 'between' && (!rule.value || rule.value.includes('~'))) {
            result[rule.id].valid = false;
            result[rule.id].reasons = ['Both To and From Dates Required'];
        }
    } else if ('rules' in rule) {
        rule.rules.forEach((r) => validateRule(r, result));
    }
};

const EMPTY_QUERY: RuleGroup = { combinator: RuleGroup.combinator.AND, rules: [] };

const AdvancedFilter = ({ filter, columns }: { filter: AdvancedFilterConfiguration; columns: ReportColumn[] }) => {
    const {
        field: { onChange, value },
        fieldState: { error },
    } = useController<ReportExecuteForm, 'advancedFilter'>({
        name: 'advancedFilter',
        defaultValue: filter.defaultValue
            ? (translateOperators(filter.defaultValue, mapToQueryOp) as RuleGroup)
            : EMPTY_QUERY,
        rules: { validate: validateAdvancedFilter },
    });

    const fields: Field[] = columns.map((c) => ({
        name: c.id.toString(),
        label: c.columnTitle!,
        operators: OPERATOR_MAP[c.columnSourceTypeCode ?? 'STRING'],
        inputType: INPUT_TYPE_MAP[c.columnSourceTypeCode ?? 'STRING'],
    }));

    return (
        <div>
            {error?.message && <AlertBanner type="error">{error.message}</AlertBanner>}
            <QueryBuilder
                fields={fields}
                query={value}
                validator={validator}
                onQueryChange={onChange}
                addRuleToNewGroups={true}
                autoSelectField={false}
                autoSelectOperator={false}
                autoSelectValue={false}
            />
            <details>
                <summary>Preview Where Statement</summary>
                {value ? (
                    <p className="font-mono-md">{formatQuery(value, 'sql')}</p>
                ) : (
                    'No advanced filter selections made'
                )}
            </details>
        </div>
    );
};

export { AdvancedFilter, queryToAdvancedFilterRequest };
