import { ModalRef } from '@trussworks/react-uswds';
import { Shown } from 'conditional-render';
import { ConfirmationModal } from 'confirmation';
import { Button } from 'design-system/button';
import { Card } from 'design-system/card';
import { NoData } from 'design-system/data';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { Field, ValueField } from 'design-system/field';
import { TextInputField } from 'design-system/input';
import { TextAreaField } from 'design-system/input/text';
import { SingleSelect } from 'design-system/select';
import { HasValueFunction, NamedColumn } from 'design-system/table/header/column';
import { Toggle } from 'design-system/toggle/Toggle';
import { AdminReportRequest, BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { LoadingIndicator } from 'libs/loading/indicator';
import { Selectable } from 'options';
import { useReportDataSources, useReportFilters, useReportLibraries, useReportSections } from 'options/report';
import { useReportDataSourceFilterableColumnOptions } from 'options/report/useReportDataSourceColumnOptions';
import { useUserOptions } from 'options/users';
import { ReactComponentLike } from 'prop-types';
import { useEffect, useId, useRef, useState } from 'react';
import { Controller, useWatch } from 'react-hook-form';
import { validateRequiredRule } from 'validation/entry';

interface EnumSelectable<T> {
    value: T;
    name: string;
}
const GROUP_OPTIONS: EnumSelectable<ReportConfiguration.group>[] = [
    { value: ReportConfiguration.group.PUBLIC, name: 'Public' },
    { value: ReportConfiguration.group.PRIVATE, name: 'Private' },
    { value: ReportConfiguration.group.TEMPLATE, name: 'Template' },
    { value: ReportConfiguration.group.REPORTING_FACILITY, name: 'Reporting Facility' },
];

const SELECT_OPTIONS: EnumSelectable<BasicFilterConfiguration.selectType>[] = [
    { value: BasicFilterConfiguration.selectType.SINGLE, name: 'Single' },
    { value: BasicFilterConfiguration.selectType.MULTI, name: 'Multi' },
];

const SIZING = 'medium';

export type ConfigForm = {
    dataSourceId: Selectable;
    reportTitle: string;
    description?: string;
    ownerId: Selectable;
    group: EnumSelectable<ReportConfiguration.group>;
    sectionCode: Selectable;
    libraryId: Selectable;
    filterRequests: FilterConfig[];
};

const formToRequest = (data: ConfigForm): AdminReportRequest => {
    return {
        ...data,
        dataSourceId: parseInt(data.dataSourceId.value),
        libraryId: parseInt(data.libraryId.value),
        ownerId: parseInt(data.ownerId.value),
        group: data.group.value,
        sectionCode: data.sectionCode.value,
        filterRequests: data.filterRequests.map((fc) => ({
            id: fc.id,
            filterCodeUid: parseInt(fc.filter.value),
            columnUid: fc.associatedColumn?.value ? parseInt(fc.associatedColumn.value) : undefined,
            isRequired: fc.isRequired,
        })),
    };
};

const ReportConfigurationContent = ({ config, isEditable }: { config?: ReportConfiguration; isEditable: boolean }) => {
    const [dataSource, setDataSource] = useState<string | Selectable | undefined>(config?.dataSource.id.toString());
    const dataSourceSelected = !!dataSource;

    return (
        <>
            {isEditable ? (
                <DataSourceEditCard isEditable={isEditable} config={config} setDataSource={setDataSource} />
            ) : (
                <DataSourceCard dataSource={config!.dataSource.id.toString()} />
            )}
            <Card id="metadata" title="2. Report configuration" collapsible={false} disabled={!dataSourceSelected}>
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="reportTitle"
                    EditComponent={TextInputField}
                    label="Name"
                    defaultValue={config?.title}
                    maxLength={100}
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="description"
                    EditComponent={TextAreaField}
                    label="Description"
                    defaultValue={config?.description}
                    required={false}
                    maxLength={300}
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="ownerId"
                    EditComponent={SingleSelect}
                    label="Owner"
                    defaultValue={config?.ownerUid.toString()}
                    getOptions={() => [{ value: '0', name: 'System' }, ...useUserOptions()]}
                    helperText="The user who can edit and delete this report."
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="group"
                    EditComponent={SingleSelect}
                    label="Group"
                    defaultValue={config?.group}
                    getOptions={() => GROUP_OPTIONS}
                    helperText="The level of visibility for the report. Templates are public."
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="sectionCode"
                    EditComponent={SingleSelect}
                    label="Section name"
                    defaultValue={config?.sectionCd}
                    getOptions={useReportSections}
                    helperText="The heading under which this report appears."
                />
                <Row
                    isEditable={isEditable}
                    disabled={!dataSourceSelected}
                    fieldName="libraryId"
                    EditComponent={SingleSelect}
                    label="Report execution library"
                    defaultValue={config?.library.id.toString()}
                    getOptions={() => {
                        const libs = useReportLibraries();
                        const nbsCustom = libs.find(({ name }) => name === 'nbs_custom');
                        const options = libs.filter(({ name }) => name !== 'nbs_custom');
                        if (!nbsCustom) return options;
                        const nbsCustomName = `${nbsCustom.name} (recommended default)`;
                        return [{ value: nbsCustom.value, name: nbsCustomName, label: nbsCustom.label }, ...options];
                    }}
                    helperText="The query logic for the report"
                />
            </Card>
            <FilterRepeatingBlock config={config} isEditable={isEditable} dataSource={dataSource} />
        </>
    );
};

const DataSourceEditCard = ({
    config,
    setDataSource,
}: {
    isEditable: boolean;
    config?: ReportConfiguration;
    setDataSource: (ds: Selectable) => void;
}) => {
    const [dataSourceSelected, setDataSourceSelected] = useState(!!config?.dataSource);
    const confirmDataSourceRef = useRef<ModalRef>(null);

    const dataSource = useWatch<ConfigForm, 'dataSourceId'>({ disabled: dataSourceSelected, name: 'dataSourceId' });

    return (
        <Card
            id="report-source"
            title="1. Report source"
            collapsible={false}
            footer={
                <Shown when={!dataSourceSelected}>
                    {/* didn't seem worth adding a styles module for this one thing */}
                    <div style={{ paddingLeft: '16rem' }}>
                        <Button
                            secondary={true}
                            disabled={!dataSource}
                            onClick={confirmDataSourceRef.current?.toggleModal}
                        >
                            Confirm data source
                        </Button>
                    </div>
                </Shown>
            }
        >
            <Row
                // only editable on create
                isEditable={true}
                disabled={dataSourceSelected}
                fieldName="dataSourceId"
                EditComponent={SingleSelect}
                label="Data source"
                defaultValue={config?.dataSource.id.toString()}
                getOptions={useReportDataSources}
            />

            <ConfirmationModal
                modal={confirmDataSourceRef}
                title={`Confirm data source: ${dataSource?.name}`}
                message={
                    <>
                        After you confirm, you cannot edit the data source again. If you want to use a different data
                        source for this report later, create a new report.
                    </>
                }
                confirmText="Confirm"
                cancelText="Cancel"
                onConfirm={() => {
                    setDataSourceSelected(true);
                    setDataSource(dataSource);
                    confirmDataSourceRef.current?.toggleModal();
                }}
                onCancel={() => {
                    confirmDataSourceRef.current?.toggleModal();
                }}
            />
        </Card>
    );
};

const DataSourceCard = ({ dataSource }: { dataSource: string }) => {
    return (
        <Card id="report-source" title="1. Report source" collapsible={false}>
            <Row
                // only editable on create
                isEditable={false}
                disabled={false}
                fieldName="dataSourceId"
                EditComponent={SingleSelect}
                label="Data source"
                defaultValue={dataSource}
                getOptions={useReportDataSources}
            />
        </Card>
    );
};

const Row = ({
    isEditable,
    disabled,
    defaultValue,
    fieldName,
    label,
    helperText,
    maxLength,
    required = true,
    getOptions,
    EditComponent,
}: {
    isEditable: boolean;
    disabled: boolean;
    defaultValue?: string;
    fieldName: string;
    label: string;
    helperText?: string;
    maxLength?: number;
    required?: boolean;
    EditComponent: ReactComponentLike;
    getOptions?: () => Selectable[];
}) => {
    const id = useId();
    const options = getOptions?.();

    const option = options ? options.find(({ value }) => value === defaultValue) : defaultValue;

    return isEditable ? (
        <Controller
            name={fieldName}
            rules={required ? validateRequiredRule(label) : undefined}
            defaultValue={defaultValue}
            // ignoring the ref as it does not pass down well and isn't critical
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            render={({ field: { ref, ...fieldValues }, fieldState: { error } }) => (
                <EditComponent
                    id={id}
                    orientation="horizontal"
                    sizing={SIZING}
                    required={required}
                    label={label}
                    helperText={helperText}
                    error={error?.message}
                    options={(options ?? []).map(addLabelToName)}
                    maxLength={maxLength}
                    disabled={disabled}
                    {...fieldValues}
                />
            )}
        />
    ) : (
        <ValueField label={label} helperText={helperText}>
            <Option option={option} />
        </ValueField>
    );
};

const addLabelToName = ({ value, name, label }: Selectable) => ({
    value,
    label,
    name: !label || label === name ? name : `${name} (${label})`,
});

const Option = ({ option }: { option?: Selectable | string }) => {
    if (!option) return <NoData />;
    if (typeof option === 'string') return option;
    if (option.name === option.label || !option.label) return option.name;
    return (
        <span>
            {option.name} <em className="text-base">({option.label})</em>
        </span>
    );
};

interface FilterConfig {
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

    return filterOptions.length === 0 ? (
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
            formRenderer={() => (
                <FilterConfigForm filterOptions={filterOptions ?? []} columnOptions={columnOptions ?? []} />
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
    filterOptions,
    columnOptions,
}: {
    filterOptions: Selectable[];
    columnOptions: Selectable[];
}) => {
    // Note the repeating block starts a new form, so this is a different form context than the
    // parent report config form

    const filterVal = useWatch<FilterConfig, 'filter'>({ name: 'filter' });

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
            {SELECTABLE_FILTER_IDS.has(filterVal?.value) && (
                <Controller
                    name="selectType"
                    rules={validateRequiredRule('Type')}
                    shouldUnregister={true}
                    // ignoring the ref as it does not pass down well and isn't critical
                    // eslint-disable-next-line @typescript-eslint/no-unused-vars
                    render={({ field: { ref, name, ...remaining }, fieldState: { error } }) => (
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
                    )}
                />
            )}
            {COLUMN_REQUIRED_FILTER_IDS.has(filterVal?.value) && (
                <>
                    <Controller
                        name="associatedColumn"
                        rules={validateRequiredRule('Associated column')}
                        shouldUnregister={true}
                        // ignoring the ref as it does not pass down well and isn't critical
                        // eslint-disable-next-line @typescript-eslint/no-unused-vars
                        render={({ field: { ref, name, ...remaining }, fieldState: { error } }) => (
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
                        )}
                    />
                    <Controller
                        name="isRequired"
                        defaultValue={false}
                        shouldUnregister={true}
                        // ignoring the ref as it does not pass down well and isn't critical
                        // eslint-disable-next-line @typescript-eslint/no-unused-vars
                        render={({ field: { ref, name, ...remaining }, fieldState: { error } }) => (
                            <Field
                                htmlFor={`filter-${name}`}
                                orientation="horizontal"
                                sizing={SIZING}
                                label="Required as basic filter?"
                                className="height-full"
                                error={error?.message}
                                required
                            >
                                <Toggle
                                    id={`filter-${name}`}
                                    aria-label="Required as basic filter"
                                    name={name}
                                    required
                                    {...remaining}
                                />
                            </Field>
                        )}
                    />
                </>
            )}
        </section>
    );
};

export { ReportConfigurationContent, formToRequest };
