import { AdvancedFilterConfiguration, ReportColumn, Rule, RuleGroup } from 'generated';
import { useController } from 'react-hook-form';
import QueryBuilder, {
    Field,
    formatQuery,
    isRuleGroupType,
    isRuleType,
    Operator,
    QueryValidator,
    RuleGroupType,
    RuleGroupTypeAny,
    RuleType,
    ValidationResult,
} from 'react-querybuilder';
import 'react-querybuilder/dist/query-builder.css';
import { ReportExecuteForm } from '../ReportRunPage';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { QueryBuilderDnD } from '@react-querybuilder/dnd';
import { createDndKitAdapter } from '@react-querybuilder/dnd/dnd-kit';
import * as DndKit from '@dnd-kit/core';

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
const BINARY_OPERATORS = ALL_OPERATORS.filter(({ arity }) => arity === 'binary').map(({ name }) => name);

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

// translate operator and remove any extraneous fields
function mapExprRules(rule: Expr, mapper: (r: Rule) => Rule): Expr {
    if ('rules' in rule) {
        const { id, combinator, rules } = rule;
        return { id, combinator, rules: rules.map((r) => mapExprRules(r, mapper)) };
    }

    return mapper(rule);
}

const mapToQueryOp = (op: string) => ALL_OPERATORS.find(({ nbsCd }) => nbsCd === op)!.name;
const mapToNbsOp = (op: string) => ALL_OPERATORS.find(({ name }) => name === op)!.nbsCd;

// typescript is tricky to appease here, hence the casts, but the code does work as intended
const queryToAdvancedFilterRequest = (query: RuleGroupType, columns: ReportColumn[]): RuleGroup => {
    return mapExprRules(query as RuleGroup, ({ id, operator, field, value }) => {
        return {
            id,
            operator: mapToNbsOp(operator)!,
            field: columns.find(({ columnName }) => field === columnName)!.id.toString(),
            value: value.toString(),
        };
    }) as RuleGroup;
};

// typescript is tricky to appease here, hence the casts, but the code does work as intended
const advancedFilterConfigToQuery = (query: RuleGroup, columns: ReportColumn[]): RuleGroupType => {
    return mapExprRules(query as RuleGroup, ({ id, operator, field, value }) => {
        return {
            id,
            operator: mapToQueryOp(operator)!,
            field: columns.find(({ id }) => field === id.toString())!.columnName!.toString(),
            value: value,
        };
    }) as RuleGroup;
};

const validateAdvancedFilter = (value?: RuleGroup) => {
    if (!value) return true;

    // todo: validation
    return Object.values(validator(value))
        .filter((v) => !v.valid)
        .reduce((acc, cur) => `${acc}\n${cur.reasons[0]}`, '');
};

// tighten the default type so it's just the object version
type ValidationResultMap = Record<string, ValidationResult>;

const validator: QueryValidator = (q) => {
    const result: ValidationResultMap = {};
    q.rules.forEach((r) => validateRule(r, result));
    return result;
};

const validateRule = (rule: RuleGroupTypeAny | RuleType | string, result: ValidationResultMap) => {
    const setInvalid = (id: string, reason: string) => {
        result[id].valid = false;
        result[id].reasons = [reason];
    };

    if (isRuleType(rule)) {
        const id = rule.id;
        if (!id) return; // no key for the map, shouldn't happen in practice
        // default valid
        result[id] = { valid: true };

        // empty rules are fine
        if (!rule.field || rule.field === '~') return;

        // check for exceptions
        if (!rule.operator || rule.operator === '~') {
            setInvalid(id, 'Must select an operator and value');
        } else if (rule.operator === 'between') {
            if (!rule.value || rule.value.includes('~')) {
                setInvalid(id, 'Both To and From Dates Required');
            }
        } else if (BINARY_OPERATORS.find((name) => name === rule.operator)) {
            // 0 is fine, but falsey
            if (!rule.value || rule.value === 0) {
                setInvalid(id, 'Value cannot be empty');
            }
        }
    } else if (isRuleGroupType(rule)) {
        rule.rules.forEach((r) => validateRule(r, result));
    }
};

const dndKitAdapter = createDndKitAdapter(DndKit);

const EMPTY_QUERY: RuleGroup = {
    id: crypto.randomUUID(),
    combinator: RuleGroup.combinator.AND,
    rules: [{ id: crypto.randomUUID(), field: '~', operator: '~', value: '' }],
};

const AdvancedFilter = ({ filter, columns }: { filter: AdvancedFilterConfiguration; columns: ReportColumn[] }) => {
    const {
        field: { onChange, value },
        fieldState: { error },
    } = useController<ReportExecuteForm, 'advancedFilter'>({
        name: 'advancedFilter',
        defaultValue: filter.defaultValue
            ? (advancedFilterConfigToQuery(filter.defaultValue, columns) as RuleGroup)
            : EMPTY_QUERY,
        rules: { validate: validateAdvancedFilter },
    });

    const fields: Field[] = columns.map((c) => ({
        id: c.id.toString(),
        name: c.columnName!,
        label: c.columnTitle!,
        operators: OPERATOR_MAP[c.columnSourceTypeCode ?? 'STRING'],
        inputType: INPUT_TYPE_MAP[c.columnSourceTypeCode ?? 'STRING'],
    }));

    return (
        <div>
            {error?.message && <AlertBanner type="error">{error.message}</AlertBanner>}
            <QueryBuilderDnD dnd={dndKitAdapter} updateWhileDragging={false}>
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
            </QueryBuilderDnD>
            <details>
                <summary>Preview Where Statement</summary>
                <p className="font-mono-md">
                    {value
                        ? formatQuery(value, {
                              format: 'sql',
                              preset: 'mssql',
                              fallbackExpression: 'No advanced filter selections made',
                          })
                        : 'No advanced filter selections made'}
                </p>
            </details>
        </div>
    );
};

export { AdvancedFilter, queryToAdvancedFilterRequest };
