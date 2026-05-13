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

// ============= Constants ============= /

const EMPTY_QUERY: QbRuleGroup = {
    id: crypto.randomUUID(),
    combinator: RuleGroup.combinator.AND,
    rules: [{ id: crypto.randomUUID(), field: '~', operator: '~', value: '' }],
};

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

// ============= Translation NBS <--> Query Builder ============= /

type NbsQuery = RuleGroup | Rule;
type QbRule = Omit<Rule, 'columnId'> & { field: string };
export type QbRuleGroup = RuleGroupType<QbRule>;
type QbQuery = QbRuleGroup | QbRule;

// map rules and remove any extraneous fields
function mapNbsRules(rule: NbsQuery, mapper: (r: Rule) => QbRule): QbQuery {
    if ('rules' in rule) {
        const { id, combinator, rules } = rule;
        return { id, combinator, rules: rules.map((r) => mapNbsRules(r, mapper)) };
    }

    return mapper(rule);
}

function mapQbRules(rule: QbQuery, mapper: (r: QbRule) => Rule): NbsQuery {
    if ('rules' in rule) {
        const { id, combinator, rules } = rule;
        // @ts-expect-error the rule group types overlap, but can't convince ts
        return { id, combinator, rules: rules.map((r) => mapQbRules(r, mapper)) };
    }

    return mapper(rule);
}

// filter rules
function filterQbRules(rule: QbQuery, filterer: (r: QbRule) => boolean): QbQuery {
    if ('rules' in rule) {
        const { id, combinator, rules } = rule;
        return {
            id,
            combinator,
            // @ts-expect-error the rule group types overlap, but can't convince ts
            rules: rules.filter((r) => (isRuleType(r) ? filterer(r) : true)).map((r) => filterQbRules(r, filterer)),
        };
    }

    return rule;
}

const mapToQueryOp = (op: string) => ALL_OPERATORS.find(({ nbsCd }) => nbsCd === op)!.name;
const mapToNbsOp = (op: string) => ALL_OPERATORS.find(({ name }) => name === op)!.nbsCd;

// typescript is tricky to appease here, hence the casts, but the code does work as intended
const queryToAdvancedFilterRequest = (query: QbRuleGroup, columns: ReportColumn[]): RuleGroup | undefined => {
    const nonEmptyQuery = filterQbRules(query, (rule) => !!rule.field && rule.field !== '~') as QbRuleGroup;
    // no non-empty rules means there is functionally no filter
    if (nonEmptyQuery.rules.length === 0) return undefined;

    return mapQbRules(nonEmptyQuery, ({ id, operator, field, value }) => {
        return {
            id,
            operator: mapToNbsOp(operator)!,
            columnId: columns.find(({ name }) => field === name)!.id,
            value: value.toString(),
        };
    }) as RuleGroup;
};

// typescript is tricky to appease here, hence the casts, but the code does work as intended
const advancedFilterConfigToQuery = (query: RuleGroup, columns: ReportColumn[]): QbRuleGroup => {
    return mapNbsRules(query, ({ id, operator, columnId, value }) => {
        return {
            id,
            operator: mapToQueryOp(operator)!,
            field: columns.find(({ id }) => columnId === id)!.name!.toString(),
            value: value,
        };
    }) as QbRuleGroup;
};

// ============= Validation ============= /

const validateAdvancedFilter = (value?: QbRuleGroup) => {
    if (!value) return true;

    return (
        Object.values(validator(value))
            .filter((v) => !v.valid)
            .reduce((acc, cur) => `${acc}\n${cur.reasons[0]}`, '') || true
    );
};

// tighten the default type so it's just the object version
type ValidationResultMap = Record<string, ValidationResult>;

const validator: QueryValidator = (q) => {
    const result: ValidationResultMap = {};
    q.rules.forEach((r) => validateRule(r, result));
    return result;
};

const isDate = (val: string) => !!val.match(/\d{4}-\d{2}-\d{2}/);

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
                setInvalid(id, 'Both low and high values required');
            } else {
                const parts: string[] = rule.value.split(',');
                if (isDate(parts[0])) {
                    const [startDt, endDt] = parts.map((v) => new Date(v));
                    if (startDt > endDt) {
                        setInvalid(id, 'High value must be greater than or equal to low value');
                    }
                } else {
                    const [startInt, endInt] = parts.map((v) => parseInt(v));
                    if (startInt > endInt) setInvalid(id, 'High value must be greater than or equal to low value');
                }
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

// ============= Drag And Drop ============= /

const dndKitAdapter = createDndKitAdapter(DndKit);

// ============= Componentry ============= /

const AdvancedFilter = ({ filter, columns }: { filter: AdvancedFilterConfiguration; columns: ReportColumn[] }) => {
    const {
        field: { onChange, value },
        fieldState: { error },
    } = useController<ReportExecuteForm, 'advancedFilter'>({
        name: 'advancedFilter',
        defaultValue: filter.defaultValue ? advancedFilterConfigToQuery(filter.defaultValue, columns) : EMPTY_QUERY,
        rules: { validate: validateAdvancedFilter },
    });

    const fields: Field[] = columns.map((c) => ({
        id: c.id.toString(),
        name: c.name,
        label: c.title,
        operators: OPERATOR_MAP[c.sourceTypeCode ?? 'STRING'],
        inputType: INPUT_TYPE_MAP[c.sourceTypeCode ?? 'STRING'],
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
            <PreviewWhere query={value} />
        </div>
    );
};

const PreviewWhere = ({ query }: { query?: QbRuleGroup }) => {
    const fallbackExpression = 'No advanced filter selections made';

    return (
        <details>
            <summary>Preview Where Statement</summary>
            <p className="font-mono-md">
                {query
                    ? formatQuery(query, {
                          format: 'sql',
                          preset: 'mssql',
                          fallbackExpression,
                      })
                    : fallbackExpression}
            </p>
        </details>
    );
};

export { AdvancedFilter, queryToAdvancedFilterRequest };
