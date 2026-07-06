import { AdvancedFilterConfiguration, ReportColumn, Rule, RuleGroup } from 'generated';
import { useController } from 'react-hook-form';
import QueryBuilder, {
    Field,
    formatQuery,
    isRuleType,
    joinWith,
    QueryValidator,
    RuleGroupType,
    splitBy,
    ValidationResult,
} from 'react-querybuilder';

import { ReportExecuteForm } from '../../ReportRunPage';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { QueryBuilderDnD } from '@react-querybuilder/dnd';
import { createPragmaticDndAdapter } from '@react-querybuilder/dnd/pragmatic-dnd';
import {
    draggable,
    dropTargetForElements,
    monitorForElements,
} from '@atlaskit/pragmatic-drag-and-drop/element/adapter';
import { combine } from '@atlaskit/pragmatic-drag-and-drop/combine';
import { KeyboardDnDProvider } from './useKeyboardDnd';
import { ShiftableDragHandle } from './ShiftableDragHandle';
import { ValueEditorSwitch } from './ValueEditorSwitch.tsx';
import { ValueSingleSelector } from './ValueSingleSelector.tsx';
import { RemoveButton } from '././RemoveButton.tsx';

import styles from './advanced-filter.module.scss';
import { validateRule } from './validator.ts';
import { AddButton } from './AddButton.tsx';
import { ALL_OPERATORS, LIST_OPERATORS, NULL_OPERATORS, OPERATOR_MAP } from './operators.ts';
import { Heading } from '../../../../../components/heading';
import { AlertMessage } from 'design-system/message/index.ts';

// ============= Constants ============= /

const EMPTY_QUERY: QbRuleGroup = {
    id: crypto.randomUUID(),
    combinator: RuleGroup.combinator.AND,
    rules: [{ id: crypto.randomUUID(), field: '~', operator: '~', value: '' }],
};

const INPUT_TYPE_MAP: Record<string, string> = {
    STRING: 'text',
    INTEGER: 'number',
    NUMBER: 'number',
    DATETIME: 'date',
    CODED: 'text',
};

// ============= Translation NBS <--> Query Builder ============= /

type NbsQuery = RuleGroup | Rule;
// to match `RuleType` more closely
// eslint-disable-next-line @typescript-eslint/no-explicit-any
type QbRule = Omit<Rule, 'columnId' | 'value'> & { field: string; value: any };
export type QbRuleGroup = RuleGroupType<QbRule>;
type QbQuery = QbRuleGroup | QbRule;

// map rules and remove any extraneous fields
function mapNbsRules(rule: NbsQuery, mapper: (r: Rule) => QbRule): QbQuery {
    if ('rules' in rule) {
        const { id, combinator, rules } = rule;
        return { id, combinator: combinator.toLowerCase(), rules: rules.map((r) => mapNbsRules(r, mapper)) };
    }

    return mapper(rule);
}

function mapQbRules(rule: QbQuery, mapper: (r: QbRule) => Rule): NbsQuery {
    if ('rules' in rule) {
        const { id, combinator, rules } = rule;
        // @ts-expect-error the rule group types overlap, but can't convince ts
        return { id, combinator: combinator.toUpperCase(), rules: rules.map((r) => mapQbRules(r, mapper)) };
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

const mapToQueryOp = (op: string, isCoded: boolean) => {
    // "EQ" and "NE" are translated to "in" and "notIn" if this is a coded value
    if (isCoded) {
        const listOp = LIST_OPERATORS.find(({ nbsCd }) => nbsCd === op);
        if (listOp) return listOp.name;
    }
    return ALL_OPERATORS.find(({ nbsCd }) => nbsCd === op)!.name;
};
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
            value: LIST_OPERATORS.find(({ name }) => name === operator)
                ? joinWith(value, '|')
                : NULL_OPERATORS.find(({ name }) => name === operator)
                  ? ''
                  : value.toString(),
        };
    }) as RuleGroup;
};

// typescript is tricky to appease here, hence the casts, but the code does work as intended
const advancedFilterConfigToQuery = (query: RuleGroup, columns: ReportColumn[]): QbRuleGroup => {
    return mapNbsRules(query, ({ id, operator, columnId, value }) => {
        const column = columns.find(({ id }) => columnId === id)!;
        const qbOperator = mapToQueryOp(operator, !!column.codeDescCd)!;
        return {
            id,
            operator: qbOperator,
            field: column.name!.toString(),
            value: LIST_OPERATORS.find(({ name }) => name === qbOperator) ? splitBy(value, '|') : value,
        };
    }) as QbRuleGroup;
};

export type ValueSetMetadata = { codeDescCd?: string; codesetNm?: string; columnUid: number };

const translateColumnToField = (c: ReportColumn): Field & ValueSetMetadata => {
    const sourceType = c.codeDescCd ? 'CODED' : c.sourceTypeCode;
    const valueEditorType = sourceType === 'CODED' ? 'multiselect' : 'text';
    return {
        id: c.id.toString(),
        name: c.name,
        label: c.title,
        operators: OPERATOR_MAP[sourceType],
        inputType: INPUT_TYPE_MAP[sourceType],
        valueEditorType,

        // shoving in more metadata for value set fetching
        codeDescCd: c.codeDescCd,
        codesetNm: c.codesetNm,
        columnUid: c.id,
    };
};

// ============= Validation ============= /

// Add another key 'label' to display in error msg
// Add another key 'type' to support validation
const addColNameAndTypeToRules = (columns: ReportColumn[], value: QbRuleGroup) => {
    const rules = value.rules;
    const updatedRules = rules.map((rule) => {
        if ('rules' in rule && Array.isArray(rule.rules)) {
            return addColNameAndTypeToRules(columns, rule as QbRuleGroup);
        }

        const matchedColumn = columns.find((col) => col.name === rule.field);

        return {
            ...rule,
            type: matchedColumn ? matchedColumn.sourceTypeCode : null,
            label: matchedColumn ? matchedColumn.title : rule.field,
        };
    });

    return {
        ...value,
        rules: updatedRules,
    };
};

const validateAdvancedFilter = (columns: ReportColumn[], value?: QbRuleGroup) => {
    if (!value) return true;
    const updatedRuleGroup = addColNameAndTypeToRules(columns, value);

    return (
        Object.values(validator(updatedRuleGroup))
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

// ============= Drag And Drop ============= /

const pragmaticDndAdapter = createPragmaticDndAdapter({
    draggable,
    dropTargetForElements,
    monitorForElements,
    combine,
});

const translationOverrides = {
    fields: { placeholderLabel: '- Select -' },
    operators: {
        placeholderLabel: '- Select -',
        title: 'Logic',
    },
    addGroup: { title: 'Add rule group' },
};

// ============= Componentry ============= /

const AdvancedFilter = ({ filter, columns }: { filter: AdvancedFilterConfiguration; columns: ReportColumn[] }) => {
    const {
        field: { onChange, value },
        fieldState: { error },
    } = useController<ReportExecuteForm, 'advancedFilter'>({
        name: 'advancedFilter',
        defaultValue: filter.defaultValue ? advancedFilterConfigToQuery(filter.defaultValue, columns) : EMPTY_QUERY,
        rules: { validate: (values) => validateAdvancedFilter(columns, values) },
    });

    const fields = columns.filter((c) => c.isFilterable).map(translateColumnToField);

    return (
        <div className={styles.layout}>
            {filter.exceptionMessage && (
                <AlertMessage type="warning" title="Saved filter has an error and can't be applied">
                    <p>
                        The saved filter contains an error that prevents it from loading. You can still run this report,
                        but the filter won't be applied to your results. To use this filter, rebuild it and save the
                        report. If you need help, share the following details with your administrator:
                    </p>
                    <p>
                        <strong>Error:</strong> <span className="font-mono-sm">{filter.exceptionMessage}</span>
                    </p>
                    <p>
                        <strong>Saved filter query:</strong> <span className="font-mono-sm">{filter.query}</span>
                    </p>
                </AlertMessage>
            )}
            {error?.message && (
                <AlertBanner type="error">
                    <Heading level={2}>Fix the following errors:</Heading>
                    <ul className={'margin-bottom-0'}>
                        {error.message
                            .split('.')
                            .map((str) => str.trim())
                            .filter(Boolean)
                            .map((msg, index) => (
                                <li key={`adv-filter-error-${index}`} className={'margin-bottom-1'}>
                                    {msg}.
                                </li>
                            ))}
                    </ul>
                </AlertBanner>
            )}
            <KeyboardDnDProvider>
                <QueryBuilderDnD dnd={pragmaticDndAdapter} updateWhileDragging={false}>
                    <QueryBuilder
                        fields={fields}
                        query={value}
                        validator={validator}
                        onQueryChange={onChange}
                        addRuleToNewGroups={true}
                        autoSelectField={false}
                        autoSelectOperator={false}
                        autoSelectValue={false}
                        translations={translationOverrides}
                        controlElements={{
                            dragHandle: ShiftableDragHandle,
                            valueEditor: ValueEditorSwitch,
                            valueSelector: ValueSingleSelector,
                            addGroupAction: AddButton,
                            addRuleAction: AddButton,
                            removeGroupAction: RemoveButton,
                            removeRuleAction: RemoveButton,
                        }}
                    />
                </QueryBuilderDnD>
            </KeyboardDnDProvider>
            <PreviewWhere query={value} />
        </div>
    );
};

const PreviewWhere = ({ query }: { query?: QbRuleGroup }) => {
    const fallbackExpression = 'No advanced filter selections made';

    return (
        <div className="padding-205 border-top border-base-lighter">
            <h3 className="margin-top-1 margin-bottom-2">Preview of WHERE Clause</h3>
            <div className="bg-base-lightest padding-105">
                <p className="font-mono-md">
                    {query
                        ? formatQuery(query, {
                              format: 'sql',
                              preset: 'mssql',
                              fallbackExpression,
                          })
                        : fallbackExpression}
                </p>
            </div>
        </div>
    );
};

export { AdvancedFilter, queryToAdvancedFilterRequest };
