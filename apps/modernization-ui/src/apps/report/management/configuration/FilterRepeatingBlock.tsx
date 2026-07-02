import { RepeatingBlock } from 'design-system/entry/multi-value';
import { ValueField } from 'design-system/field';
import { HasValueFunction, NamedColumn } from 'design-system/table/header/column';
import { LoadingIndicator } from 'libs/loading/indicator';
import { useReportFilters } from 'options/report';
import { useReportDataSourceFilterableColumnOptions } from 'options/report/useReportDataSourceColumnOptions';
import { useEffect } from 'react';
import { BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { Selectable } from 'options';
import { addLabelToName, EnumSelectable } from '../../utils';
import { SIZING } from '../../constants';
import { Controller, useWatch } from 'react-hook-form';
import { validateRequiredRule } from 'validation/entry';
import { SingleSelect } from 'design-system/select';
import { Shown } from 'conditional-render';
import { ToggleField } from 'design-system/toggle/ToggleField';

const SELECT_OPTIONS: EnumSelectable<BasicFilterConfiguration.selectType>[] = [
    { value: BasicFilterConfiguration.selectType.SINGLE, name: 'Single' },
    { value: BasicFilterConfiguration.selectType.MULTI, name: 'Multi' },
];

export interface FilterConfig {
    id?: number;
    filter: Selectable;
    selectType?: EnumSelectable<BasicFilterConfiguration.selectType>;
    associatedColumn?: Selectable;
    isRequired: boolean;
}

const EMPTY_FILTER_CONFIG: Partial<FilterConfig> = {
    id: undefined,
    filter: undefined,
    selectType: undefined,
    associatedColumn: undefined,
    isRequired: false,
};

type FilterColumn = NamedColumn<FilterConfig, string> & HasValueFunction<FilterConfig, string>;

const filterColumns: FilterColumn[] = [
    { id: 'filter', name: 'Filter', value: (v) => v.filter.name },
    { id: 'type', name: 'Type', value: (v) => v.selectType?.name },
    {
        id: 'column',
        name: 'Associated column',
        value: (v) => v.associatedColumn?.name,
    },
    { id: 'filter-required', name: 'Required as basic filter?', value: (v) => (v.isRequired ? 'Yes' : 'No') },
];

const FilterRepeatingBlock = ({
    config,
    isEditable,
    dataSource,
}: {
    config?: ReportConfiguration;
    isEditable: boolean;
    dataSource?: Selectable | string;
}) => {
    const dataSourceSelected = !!dataSource;
    const filterOptions = useReportFilters();
    const rawColumnOptions = useColumnOptions(dataSource);
    const columnOptions = (rawColumnOptions ?? []).map(addLabelToName);

    const defaultFilterData: FilterConfig[] =
        config?.basicFilters.map((f) => ({
            id: f.reportFilterUid,
            filter: filterOptions.find(({ value }) => parseInt(value) === f.filterType.id)!,
            selectType: SELECT_OPTIONS.find(({ value }) => value == f.selectType),
            associatedColumn: columnOptions.find(({ value }) => value === f.reportColumnUid?.toString()),
            isRequired: f.isRequired,
        })) ?? [];

    if (config?.advancedFilter) {
        defaultFilterData.push({
            id: config.advancedFilter.reportFilterUid,
            filter: filterOptions.find(({ value }) => value === '7')!,
            isRequired: false,
        });
    }

    return filterOptions.length === 0 || (!!dataSource && columnOptions.length === 0) ? (
        <LoadingIndicator />
    ) : isEditable ? (
        <Controller
            name="filterRequests"
            defaultValue={defaultFilterData}
            render={({ field: { onChange, value } }) => (
                <FilterRepeatingBlockImpl
                    onChange={onChange}
                    isEditable={isEditable}
                    dataSourceSelected={dataSourceSelected}
                    filterOptions={filterOptions}
                    columnOptions={columnOptions}
                    value={value}
                />
            )}
        />
    ) : (
        <FilterRepeatingBlockImpl
            isEditable={isEditable}
            dataSourceSelected={dataSourceSelected}
            value={defaultFilterData}
        />
    );
};

const useColumnOptions = (dataSource?: Selectable | string) => {
    const { load, options } = useReportDataSourceFilterableColumnOptions();

    useEffect(() => {
        load(typeof dataSource === 'string' ? dataSource : dataSource?.value);
    }, [dataSource]);

    return options;
};

const FilterRepeatingBlockImpl = ({
    isEditable,
    dataSourceSelected,
    value,
    filterOptions,
    columnOptions,
    onChange,
}: {
    isEditable: boolean;
    dataSourceSelected: boolean;
    value: FilterConfig[];
    filterOptions?: Selectable[];
    columnOptions?: Selectable[];
    onChange?: (v: FilterConfig[]) => void;
}) => {
    return (
        <RepeatingBlock<FilterConfig>
            id="filter-config"
            title="3. Available filters"
            itemName="filter"
            columns={filterColumns}
            sizing={SIZING}
            defaultValues={EMPTY_FILTER_CONFIG}
            editable={isEditable && dataSourceSelected}
            data={value}
            disabled={!dataSourceSelected}
            onChange={onChange}
            viewRenderer={(entry: FilterConfig) =>
                filterColumns.map((fc) => (
                    <ValueField key={fc.id} label={fc.name} sizing={SIZING}>
                        {fc.value(entry)}
                    </ValueField>
                ))
            }
            formRenderer={(entry) => (
                <FilterConfigForm
                    entry={entry}
                    filterOptions={filterOptions ?? []}
                    columnOptions={columnOptions ?? []}
                />
            )}
        />
    );
};

// Matches NBS 6 logic
const SELECTABLE_FILTER_IDS = new Set(['1', '2', '3', '8', '9', '10', '16', '19', '20', '21']);
const COLUMN_REQUIRED_FILTER_IDS = new Set([
    '1',
    '2',
    '3',
    '5',
    '6',
    '8',
    '9',
    '10',
    '12',
    '13',
    '14',
    '15',
    '16',
    '17',
    '18',
    '19',
    '20',
    '21',
]);

const FilterConfigForm = ({
    entry,
    filterOptions,
    columnOptions,
}: {
    entry?: FilterConfig;
    filterOptions: Selectable[];
    columnOptions: Selectable[];
}) => {
    // Note the repeating block starts a new form, so this is a different form context than the
    // parent report config form

    const filterVal = useWatch<FilterConfig, 'filter'>({ name: 'filter' });
    const needsSelectType = SELECTABLE_FILTER_IDS.has(filterVal?.value);
    const needsColumnAndRequired = COLUMN_REQUIRED_FILTER_IDS.has(filterVal?.value);

    return (
        <section>
            <Controller
                name="filter"
                rules={validateRequiredRule('Filter')}
                // ignoring the ref as it does not pass down well and isn't critical
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
                render={({ field: { ref, name, ...remaining }, fieldState: { error } }) => (
                    <SingleSelect
                        id={`filter-${name}`}
                        label={'Filter'}
                        disabled={!!entry?.id} // can't change filter type on edit
                        name={name}
                        options={filterOptions}
                        orientation="horizontal"
                        error={error?.message}
                        required
                        sizing={SIZING}
                        {...remaining}
                    />
                )}
            />
            <Controller
                name="selectType"
                rules={needsSelectType ? validateRequiredRule('Type') : undefined}
                // ignoring the ref as it does not pass down well and isn't critical
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
                render={({ field: { ref, name, ...remaining }, fieldState: { error } }) => (
                    <Shown when={needsSelectType}>
                        <SingleSelect
                            id={`filter-${name}`}
                            label={'Type'}
                            name={name}
                            options={SELECT_OPTIONS}
                            orientation="horizontal"
                            error={error?.message}
                            required
                            sizing={SIZING}
                            {...remaining}
                        />
                    </Shown>
                )}
            />
            <Controller
                name="associatedColumn"
                rules={needsColumnAndRequired ? validateRequiredRule('Associated column') : undefined}
                // ignoring the ref as it does not pass down well and isn't critical
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
                render={({ field: { ref, name, ...remaining }, fieldState: { error } }) => (
                    <Shown when={needsColumnAndRequired}>
                        <SingleSelect
                            id={`filter-${name}`}
                            label="Associated column"
                            name={name}
                            options={columnOptions}
                            orientation="horizontal"
                            error={error?.message}
                            required
                            sizing={SIZING}
                            {...remaining}
                        />
                    </Shown>
                )}
            />
            <Controller
                name="isRequired"
                // ignoring the ref as it does not pass down well and isn't critical
                // eslint-disable-next-line @typescript-eslint/no-unused-vars
                render={({ field: { ref, name, ...remaining }, fieldState: { error } }) => (
                    <Shown when={needsColumnAndRequired}>
                        <ToggleField
                            id={`filter-${name}`}
                            orientation="horizontal"
                            sizing={SIZING}
                            label="Required as basic filter?"
                            className="height-full"
                            error={error?.message}
                            {...remaining}
                        />
                    </Shown>
                )}
            />
        </section>
    );
};

export { FilterRepeatingBlock };
