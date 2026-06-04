import { ModalRef } from '@trussworks/react-uswds';
import { Shown } from 'conditional-render';
import { ConfirmationModal } from 'confirmation';
import { Button } from 'design-system/button';
import { Card } from 'design-system/card';
import { NoData } from 'design-system/data';
import { RepeatingBlock } from 'design-system/entry/multi-value';
import { ValueField } from 'design-system/field';
import { TextInputField } from 'design-system/input';
import { TextAreaField } from 'design-system/input/text';
import { SingleSelect } from 'design-system/select';
import { HasValueFunction, NamedColumn } from 'design-system/table/header/column';
import { AdminReportRequest, BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { Selectable } from 'options';
import { useReportDataSources, useReportFilters, useReportLibraries, useReportSections } from 'options/report';
import { useUserOptions } from 'options/users';
import { ReactComponentLike } from 'prop-types';
import { useId, useRef, useState } from 'react';
import { Controller, useWatch } from 'react-hook-form';
import { validateRequiredRule } from 'validation/entry';

interface FilterConfig {
    id?: number;
    filter: Selectable;
    selectType?: BasicFilterConfiguration.selectType;
    columnId?: number;
    isRequired: boolean;
}

const SIZING = 'medium';

interface GroupSelectable {
    value: ReportConfiguration.group;
    name: string;
}

const GROUP_OPTIONS: GroupSelectable[] = [
    { value: ReportConfiguration.group.PUBLIC, name: 'Public' },
    { value: ReportConfiguration.group.PRIVATE, name: 'Private' },
    { value: ReportConfiguration.group.TEMPLATE, name: 'Template' },
    { value: ReportConfiguration.group.REPORTING_FACILITY, name: 'Reporting Facility' },
];

export type ConfigForm = {
    dataSourceId: Selectable;
    reportTitle: string;
    description?: string;
    ownerId: Selectable;
    group: GroupSelectable;
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
        filterRequests: [],
    };
};

type FilterColumn = NamedColumn<FilterConfig, string> & HasValueFunction<FilterConfig, string>;

const ReportConfigurationContent = ({ config, isEditable }: { config?: ReportConfiguration; isEditable: boolean }) => {
    const [dataSourceSelected, setDataSourceSelected] = useState(!!config?.dataSource);
    const confirmDataSourceRef = useRef<ModalRef>(null);

    const dataSource = useWatch<ConfigForm, 'dataSourceId'>({
        name: 'dataSourceId',
    });

    return (
        <>
            <Card
                id="report-source"
                title="1. Report source"
                collapsible={false}
                footer={
                    <Shown when={isEditable && !dataSourceSelected}>
                        {/* didn't seem worth adding a styles module for this one thing */}
                        <div style={{ paddingLeft: '16rem' }}>
                            <Button
                                secondary={true}
                                disabled={!dataSource}
                                onClick={confirmDataSourceRef.current?.toggleModal}>
                                Confirm data source
                            </Button>
                        </div>
                    </Shown>
                }>
                <Row
                    // only editable on create
                    isEditable={isEditable}
                    disabled={dataSourceSelected}
                    fieldName="dataSourceId"
                    EditComponent={SingleSelect}
                    label="Data source"
                    defaultValue={config?.dataSource.id.toString()}
                    getOptions={() =>
                        useReportDataSources().map(({ value, name, label }) => ({
                            value,
                            name: `${name} (${label})`,
                            label,
                        }))
                    }
                />

                <ConfirmationModal
                    modal={confirmDataSourceRef}
                    title={`Confirm data source: ${dataSource?.name}`}
                    message={
                        <>
                            After you confirm, you cannot edit the data source again. If you want to use a different
                            data source for this report later, create a new report.
                        </>
                    }
                    confirmText="Confirm"
                    cancelText="Cancel"
                    onConfirm={() => {
                        setDataSourceSelected(true);
                        confirmDataSourceRef.current?.toggleModal();
                    }}
                    onCancel={() => {
                        confirmDataSourceRef.current?.toggleModal();
                    }}
                />
            </Card>
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
                        const options = libs
                            .filter(({ name }) => name !== 'nbs_custom')
                            .map(({ value, name, label }) => ({ value, name: `${name} (${label})`, label }));
                        if (!nbsCustom) return options;
                        const nbsCustomName = `${nbsCustom.name} (recommended default) (${nbsCustom.label})`;
                        return [{ value: nbsCustom.value, name: nbsCustomName, label: nbsCustom.value }, ...options];
                    }}
                    helperText="The query logic for the report"
                />
            </Card>
            <FilterRepeatingBlock config={config} isEditable={isEditable} dataSourceSelected={dataSourceSelected} />
        </>
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
            disabled={disabled}
            // ignoring the ref as it does not pass down well and isn't critical
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            render={({ field: { ref, ...fieldValues }, fieldState: { error } }) => (
                <EditComponent
                    id={id}
                    orientation="horizontal"
                    SIZING="medium"
                    required={required}
                    label={label}
                    helperText={helperText}
                    error={error?.message}
                    options={options}
                    maxLength={maxLength}
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

const FilterRepeatingBlock = ({
    config,
    isEditable,
    dataSourceSelected,
}: {
    config?: ReportConfiguration;
    isEditable: boolean;
    dataSourceSelected: boolean;
}) => {
    const filterOptions = useReportFilters();

    const filterColumns: FilterColumn[] = [
        { id: 'filter', name: 'Filter', value: (v) => v.filter.name },
        { id: 'type', name: 'Type', value: (v) => v.selectType },
        {
            id: 'column',
            name: 'Associated column',
            value: (v) => config?.columns.find(({ id }) => id === v.columnId)?.title,
        },
        { id: 'filter-required', name: 'Required as basic filter?', value: (v) => (v.isRequired ? 'Yes' : 'No') },
    ];

    const filterData: FilterConfig[] =
        config?.basicFilters.map((f) => ({
            id: f.reportFilterUid,
            filter: filterOptions.find(({ value }) => parseInt(value) === f.filterType.id)!,
            selectType: f.selectType,
            columnId: f.reportColumnUid,
            isRequired: f.isRequired,
        })) ?? [];

    if (config?.advancedFilter) {
        filterData.push({
            id: config.advancedFilter.reportFilterUid,
            filter: filterOptions.find(({ value }) => value === '7')!,
            isRequired: false,
        });
    }

    return (
        <RepeatingBlock<FilterConfig>
            id="filter-config"
            title="3. Available filters"
            itemName="filter"
            columns={filterColumns}
            sizing={SIZING}
            editable={isEditable && dataSourceSelected}
            data={filterData}
            disabled={!dataSourceSelected}
            viewRenderer={(entry: FilterConfig) =>
                filterColumns.map((fc) => (
                    <ValueField key={fc.id} label={fc.name} sizing={SIZING}>
                        {fc.value(entry)}
                    </ValueField>
                ))
            }
            formRenderer={() => <FilterConfigForm filterOptions={filterOptions} />}
        />
    );
};

const SELECTABLE_FILTER_IDS = ['1', '2', '3', '8', '9', '10', '16', '19', '20', '21'];
const COLUMN_REQUIRED_FILTER_IDS = [
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
];

const FilterConfigForm = ({ filterOptions }: { filterOptions: Selectable[] }) => {
    // Note the repeating block starts a new form, so this is a different form context than the
    // parent report config form

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
        </section>
    );
};

export { ReportConfigurationContent, formToRequest };
