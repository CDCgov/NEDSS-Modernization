import { AdvancedFilterConfiguration, ReportColumn, Rule, RuleGroup } from 'generated';
import { useController } from 'react-hook-form';
import QueryBuilder, {
    Field,
    formatQuery,
    isRuleType,
    joinWith,
    QueryValidator,
    RuleGroupProps,
    RuleGroupType,
    splitBy,
    ValidationResult,
    RuleGroup as DefaultRuleGroup,
    Rule as DefaultRule,
    RuleProps,
    FullField,
} from 'react-querybuilder';

import { ReportExecuteForm } from '../../ReportRunPage';
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
import { validateRule } from './validator.ts';
import { AddButton } from './AddButton.tsx';
import { ALL_OPERATORS, LIST_OPERATORS, NULL_OPERATORS, OPERATOR_MAP } from './operators.ts';
import { ComponentType, ReactNode, useEffect, useState } from 'react';
import { ValidationErrorBanner } from 'design-system/errors/ValidationError.tsx';
import { AlertMessage } from 'design-system/message/index.ts';
import classNames from 'classnames';

import styles from './advanced-filter.module.scss';

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
type QbRule = Omit<Rule, 'columnId' | 'value'> & { field: string; value: any; label?: string; type?: string };
export type QbRuleGroup = RuleGroupType<QbRule>;
export type QbQuery = QbRuleGroup | QbRule;

export const isQbRuleType = (rule: QbQuery): rule is QbRule => isRuleType(rule) && 'field' in rule;
export const isQbRuleGroupType = (rule: QbQuery): rule is QbRuleGroup => !isQbRuleType(rule);

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
const addColNameAndTypeToRules = (columns: ReportColumn[], value: QbRuleGroup): QbRuleGroup => {
    const updatedRules: QbQuery[] = value.rules.map((rule) => {
        if ('rules' in rule && Array.isArray(rule.rules)) {
            return addColNameAndTypeToRules(columns, rule);
        }

        // cast to alternate type branch appease ts
        const r = rule as QbRule;

        const matchedColumn = columns.find((col) => col.name === r.field);

        return {
            ...r,
            type: matchedColumn ? matchedColumn.sourceTypeCode : undefined,
            label: matchedColumn ? matchedColumn.title : r.field,
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
    q.rules.forEach((r) => validateRule(r as QbQuery, result));
    return result;
};

const parseAdvancedFilterErrors = (message: string): string[] =>
    message
        .split('\n')
        .map((str) => str.trim())
        .filter(Boolean);

const formatAdvancedFilterErrors = (message: string): ReactNode =>
    parseAdvancedFilterErrors(message).map((msg, index) => <li key={`adv-filter-error-${index}`}>{msg}</li>);

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
    const [errorIds, setErrorIds] = useState<string[]>([]);

    const fields = columns.filter((c) => c.isFilterable).map(translateColumnToField);

    // only validate when the form validates
    useEffect(() => {
        if (value) {
            setErrorIds(
                Object.entries(validator(value))
                    .filter(([_k, v]) => !v.valid)
                    .map(([k, _v]) => k)
            );
        } else {
            setErrorIds([]);
        }
    }, [error]);

    return (
        <div className={classNames(styles.layout)}>
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
                <ValidationErrorBanner level={3}>
                    <ul>{formatAdvancedFilterErrors(error.message)}</ul>
                </ValidationErrorBanner>
            )}
            <KeyboardDnDProvider>
                <QueryBuilderDnD dnd={pragmaticDndAdapter} updateWhileDragging={false}>
                    <QueryBuilder
                        fields={fields}
                        query={value}
                        context={{ errorIds }}
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
                            // KLUDGE: This is using the literal default component, but ts is very unhappy
                            ruleGroup: RuleGroupWithErrors as unknown as ComponentType<
                                RuleGroupProps<ValueSetMetadata & FullField, string>
                            >,
                            rule: RuleWithErrors,
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
                <p className="font-mono-xs">
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

const RuleGroupWithErrors = (props: RuleGroupProps) => {
    return (
        <div className={classNames({ [styles.invalid]: props?.context?.errorIds?.includes(props.id) })}>
            <DefaultRuleGroup {...props} />
        </div>
    );
};

const RuleWithErrors = (props: RuleProps) => {
    return (
        <div className={classNames({ [styles.invalid]: props?.context?.errorIds?.includes(props.id) })}>
            <DefaultRule {...props} />
        </div>
    );
};

export { AdvancedFilter, queryToAdvancedFilterRequest, parseAdvancedFilterErrors };
